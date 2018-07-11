package com.example.akshit.reddit_api.API

import com.example.akshit.reddit_api.Models.Data
import com.example.akshit.reddit_api.Models.Model
import com.example.akshit.reddit_api.Models.Post
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

interface Posts_API{
    val str :String
    @GET("{subreddit}/.json")
    fun loadChanges(@Path("subreddit") sub :String,
                    @Query("after") after:String,
                    @Query("before") before:String,
                    @Query("count")count :Int) : Model.RedditPosts
    companion object {
        fun create(): Posts_API {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://reddit.com/r/")
                    .build()

            return retrofit.create(Posts_API::class.java)
        }
    }
}
