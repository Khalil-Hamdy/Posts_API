package com.khalil.posts_api_app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.khalil.posts_api_app.databinding.ActivityMainBinding
import com.khalil.posts_api_app.model.PostsList
import com.khalil.posts_api_app.service.PostsNetwork
import com.khalil.posts_api_app.ui.utils.PostsAdabter
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var postsAdabter: PostsAdabter
    private lateinit var binding: ActivityMainBinding
    private lateinit var postsList: List<PostsList>
    private var valid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        lifecycleScope.launchWhenCreated{
            binding.progressBarPostsApi.isVisible = true // Show Progress Bar

            try {
               postsList= PostsNetwork.serviceApi.getPosts()// Getting List of posts Data
                valid=true
            } catch (e: IOException) {
                Log.d("TodoListApiActivity", "You may have no internet connection")
                binding.progressBarPostsApi.isVisible = false // Disable Progress Bar
                valid=false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d("TodoListApiActivity", "Unexpected Response")
                binding.progressBarPostsApi.isVisible = false // Disable Progress Bar
                valid=false
                return@launchWhenCreated
            }
            if (valid) {
                postsAdabter.posts = postsList!!
            } else {
                Log.d("TodoListApiActivity", "Response is not successful")
            }
            binding.progressBarPostsApi.isVisible = false // Disable Progress Bar
        }

    }

    private fun setupRecyclerView() = binding.rvPostsApi.apply {
        postsAdabter = PostsAdabter()
        adapter = postsAdabter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}

