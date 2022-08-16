package com.khalil.posts_api_app.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khalil.posts_api_app.databinding.ItemPostsApiBinding
import com.khalil.posts_api_app.model.PostsList

class PostsAdabter : RecyclerView.Adapter<PostsAdabter.PostsViewHolder>(){

    inner class PostsViewHolder(val binding: ItemPostsApiBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<PostsList>(){
        override fun areItemsTheSame(oldItem: PostsList, newItem: PostsList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostsList, newItem: PostsList): Boolean {
            return oldItem == newItem
        }
    }

    private val differ  = AsyncListDiffer(this ,diffCallBack)
    var posts : List<PostsList>
        get() = differ.currentList
        set(value) { differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(ItemPostsApiBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.binding.apply {
            val singlePost =  posts[position]
            txTitle.text = singlePost.title
            txBody.text = singlePost.body

        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}