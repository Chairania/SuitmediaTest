package com.suitmedia.assignment.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.suitmedia.assignment.R
import com.suitmedia.assignment.adapter.UserAdapter
import com.suitmedia.assignment.data.RetrofitClient
import com.suitmedia.assignment.model.User
import kotlinx.coroutines.launch

class ThirdScreen : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        window.statusBarColor = Color.WHITE
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        swipeRefresh = findViewById(R.id.swipeRefresh)
        val recyclerView = findViewById<RecyclerView>(R.id.rvUsers)

        adapter = UserAdapter(mutableListOf()) { user ->
            val resultIntent = Intent().apply {
                putExtra("selected_user_name", "${user.first_name} ${user.last_name}")
                putExtra("selected_user_avatar", user.avatar) // URL gambar
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        val backButton = findViewById<ImageButton>(R.id.ivBackThird)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            currentPage = 1
            fetchUsers(reset = true)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoading && currentPage < totalPages) {
                    currentPage++
                    fetchUsers()
                }
            }
        })

        fetchUsers()
    }

    private fun fetchUsers(reset: Boolean = false) {
        isLoading = true
        swipeRefresh.isRefreshing = true

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getUsers(currentPage, 5)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        totalPages = it.total_pages
                        val users: List<User> = it.data
                        if (reset) adapter.setUsers(users)
                        else adapter.addUsers(users)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@ThirdScreen, "API Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    android.util.Log.e("API_ERROR", "Code: ${response.code()}, Body: $errorBody")
                }
            } catch (e: Exception) {
                Toast.makeText(this@ThirdScreen, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
                android.util.Log.e("EXCEPTION", "Error: ", e)
            } finally {
                swipeRefresh.isRefreshing = false
                isLoading = false
            }
        }
    }

}
