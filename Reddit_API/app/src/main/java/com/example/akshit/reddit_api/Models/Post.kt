package com.example.akshit.reddit_api.Models

data class Post(val subreddit :String,val title :String,val author:String,val points:Int,val numcomments:Int,
                val permalink:String,val url:String,val domain:String,val id:String)