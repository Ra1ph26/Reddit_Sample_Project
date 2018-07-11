package com.example.akshit.reddit_api.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akshit.reddit_api.Models.Post
import com.example.akshit.reddit_api.R
import kotlinx.android.synthetic.main.list_posts.view.*

class PostsRA(private val jsonlist: ArrayList<Post>, val OnClick: (post:Post) ->Unit) : RecyclerView.Adapter<PostsRA.jsonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): jsonViewHolder =
            jsonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_posts, parent, false))


    override fun getItemCount() = jsonlist.size

    override fun onBindViewHolder(holder: jsonViewHolder, position: Int)  {

        holder.bind(jsonlist[position],OnClick)

    }


    class jsonViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {



        fun bind(post: Post, onClick: (post: Post) -> Unit) {
            val authrep= post.author +" posted this and got "+post.numcomments.toString()+" replies"
            itemView?.tvScore?.text = post.points.toString()
            itemView?.tvTitle?.text = post.title
            itemView?.tvAuthRep?.text = authrep
            itemView.setOnClickListener(){
                onClick(post)
            }
        }

    }
}