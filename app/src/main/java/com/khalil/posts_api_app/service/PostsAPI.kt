package com.khalil.posts_api_app.service

import com.khalil.posts_api_app.model.PostsList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://jsonplaceholder.typicode.com/"
interface PostsAPI {
    @GET("posts")
    suspend fun getPosts() : List<PostsList>
}

object PostsNetwork{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val serviceApi = retrofit.create(PostsAPI::class.java)

}