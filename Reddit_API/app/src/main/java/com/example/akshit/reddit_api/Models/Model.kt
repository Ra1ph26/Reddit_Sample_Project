package com.example.akshit.reddit_api.Models

object Model {
    data class RedditPosts(val data:Data)
    data class Data(val children:List<Post>,val after:String,val before :String)
    data class Post(val subreddit :String,val title :String,val author:String,val points:Int,val numcomments:Int,
                    val permalink:String,val url:String,val domain:String,val id:String)
}