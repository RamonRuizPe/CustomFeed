package com.example.proyectoparcial2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAd (context: Context, Entry: ArrayList<Entry>): RecyclerView.Adapter<AppAd.ViewHolder>(){
    private var localContext: Context? = null
    private var localEntry: ArrayList<Entry>? = null

    init {
        localContext = context
        localEntry = Entry
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAd.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(localContext)
        val view: View = layoutInflater.inflate(R.layout.feed_row, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFeedEntry: Entry = localEntry!![position]
        holder.textTitle.text = currentFeedEntry.title
        holder.textLink.text = "Read it full at: ${currentFeedEntry.link}"
        holder.textDescription.text = currentFeedEntry.description.take(260).plus("...")
        holder.textAuthor.text = "Written by: ${currentFeedEntry.author}"
    }

    override fun getItemCount(): Int {
        return localEntry?.size?:0
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val textTitle: TextView = v.findViewById(R.id.tvTitle)
        val textDescription: TextView = v.findViewById(R.id.tvDescr)
        val textLink: TextView = v.findViewById(R.id.tvLink)
        val textAuthor: TextView = v.findViewById(R.id.tvAuthor)
    }

}