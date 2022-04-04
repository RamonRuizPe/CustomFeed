package com.example.proyectoparcial2

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class Entry(){
    var title: String = ""
    var description: String = ""
    var link: String = ""
    var author: String = ""
    var pubDate: String = ""
    override fun toString(): String {
        return """
            title= $title
            description= $description
            link= $link
            author = $author
            pubDate = $pubDate
        """.trimIndent()
    }
}

class feed : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_feed)
        val downloadData = DataDownload(this, recyclerView)
        downloadData.execute("https://www.awn.com/anime/rss.xml")
        Log.d(TAG, "onCreate finish")
    }

    companion object {
        private class DataDownload(context: Context, recyclerView: RecyclerView) : AsyncTask<String, Void, String>(){
            private val TAG = "Download"

            var localContext: Context by Delegates.notNull()
            var localRecyclerView: RecyclerView by Delegates.notNull()

            init{
                localContext = context
                localRecyclerView = recyclerView
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground")//.d significa debbug
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()){
                    Log.e(TAG, "doInBackground: failed")//Estos logs si los puede visualizar el usuario
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String{
                try{
                    return URL(urlPath).readText()
                }catch (e: Exception){
                    val errorMesage: String = when(e){
                        is MalformedURLException -> "downloadXML: Invalid url: ${e.message}"
                        is IOException -> "downloadXML: Error reading data: ${e.message}"
                        else -> "downloadXML: Unknown error ${e.message}"
                    }
                    Log.e(TAG, errorMesage)
                }
                return ""
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute")
                val parsedApplication = ParseApp()
                parsedApplication.parse(result)

                val adapter: AppAd = AppAd(localContext, parsedApplication.apps)
                localRecyclerView.adapter = adapter
                localRecyclerView.layoutManager = LinearLayoutManager(localContext)
            }


        }
    }

    fun backToMenu(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}