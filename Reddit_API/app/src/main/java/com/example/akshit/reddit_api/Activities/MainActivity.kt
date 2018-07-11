package com.example.akshit.reddit_api.Activities

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import com.example.akshit.reddit_api.API.Posts_API
import com.example.akshit.reddit_api.Adapters.PostsRA
import com.example.akshit.reddit_api.Models.Data
import com.example.akshit.reddit_api.Models.Model
import com.example.akshit.reddit_api.Models.Post
import com.example.akshit.reddit_api.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    val posts_API by lazy {
        Posts_API.create()
    }
    var after = "" as String
    var before = "" as String
    var count=26 as Int
    var js=ArrayList<Post>()
    lateinit var p_api:Model.RedditPosts
    lateinit var sub :String

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTV("all")
        rvPosts.addItemDecoration(DividerItemDecoration(rvPosts.context, DividerItemDecoration.VERTICAL))
        sub="all"
        btnSub.setOnClickListener(){
            DownloadTask().cancel(true)
            sub=etSub.text.toString()
            after=""
            before=""
            updateTV(sub)
        }
        btnNext.setOnClickListener(){
            DownloadTask().cancel(true)
            before=""
            count+=26
            updateTV(sub)
        }
        btnPrev.setOnClickListener(){
            DownloadTask().cancel(true)
            after=""
            count-=26
            updateTV(sub)
        }
        val adapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTypes.adapter=adapter
    }

    private fun updateTV(str:String) {

        DownloadTask().execute("https://reddit.com/r/"+str+"/.json?after="+after+"&before="+before+"&count="+count)

    }
    fun beginSearch(subreddit: String) {

        p_api =posts_API.loadChanges(subreddit, after,before,count)
       // js=parseJson(p_api)
      //  val jsonAdapter1= PostsRA(js,OnClick)
      //  rvPosts.layoutManager= LinearLayoutManager(parent)
       // rvPosts.adapter=jsonAdapter1

    }
    inner class DownloadTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var url = URL("http://www.google.com")
            var result =""
            try {
                url = URL(params[0])
            }
            catch (e : MalformedURLException){
                e.printStackTrace()
            }
            val connection : HttpURLConnection
            try {
                connection = url.openConnection() as HttpURLConnection
                val isr = InputStreamReader(connection.inputStream)
                val br = BufferedReader(isr)
                val sb=StringBuilder()
                var buffer:String? = ""
                while (buffer != null){
                    sb.append(buffer)
                    buffer=br.readLine()

                }
                result=sb.toString()
                Log.d("TAG",result)

            }
            catch (e: IOException){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            js = parseJson(result!!)
            val jsonAdapter1= PostsRA(js,OnClick)
            rvPosts.layoutManager= LinearLayoutManager(parent)
            rvPosts.adapter=jsonAdapter1

//            tv1.text=result
        }
    }

    val OnClick ={
        post:Post ->
        val intent : Intent
      //  intent = Intent(this,Comments_Activity::class.java)
      //  intent.putExtra("user",post.id.toString())
      //  startActivity(intent)

    }

    fun parseJson(s :String):ArrayList<Post> {
        val json1 = ArrayList<Post>()
        Log.d("JSON", s)
        val json2=JSONObject(s)
        val json3=json2.getJSONObject("data")
        after=json3.getString("after")
        before=json3.getString("before")

        try {
            val children=json3.getJSONArray("children")
            val t=children.length() -1
            for(i in 0..t){
                var temp = children.getJSONObject(i)
                var temp1=temp.getJSONObject("data")
                var json4= Post(temp1.getString("subreddit")
                        ,temp1.getString("title")
                        ,temp1.getString("author")
                        ,temp1.getInt("score")
                        ,temp1.getInt("num_comments")
                        ,temp1.getString("permalink")
                        ,temp1.getString("url")
                        ,temp1.getString("domain")
                        ,temp1.getString("id") )
                json1.add(json4)
            }

        }
        catch (e: JSONException){
            e.printStackTrace()
        }


        return json1
    }

}

