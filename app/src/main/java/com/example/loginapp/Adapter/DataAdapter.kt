package com.example.loginapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.Model.Images
import com.example.loginapp.R
import com.squareup.picasso.Picasso

import java.util.*

open class DataAdapter(private val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val mRegion: MutableList<Images?>?
    var v2: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> viewHolder = getViewHolder(parent, inflater)
            LOADING -> {
                v2 = inflater.inflate(R.layout.item_loading, parent, false)
                viewHolder = LoadingVH(v2)
            }
            else -> {
                val v3 = inflater.inflate(R.layout.no_results, parent, false)
                viewHolder = NoResultVH(v3)
            }
        }
        return viewHolder
    }

    private fun getViewHolder(parent: ViewGroup?, inflater: LayoutInflater?): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val v1 = inflater?.inflate(R.layout.item, parent, false)
        viewHolder = v1?.let { ImagesViewHolder(it) }!!
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = mRegion?.get(position)
        when (getItemViewType(position)) {
            ITEM -> {
                val imagesViewHolder = holder as ImagesViewHolder?
                if (result != null) {
                    imagesViewHolder?.id?.text = "Id-: "+result.getId()
                }
                if (result != null) {
                    imagesViewHolder?.title?.text = "Title-: " + result.getTitle()
                }
                Picasso.get().load(result?.getUrl()).into(imagesViewHolder?.img)
            }
        }
    }

    override fun getItemCount(): Int {
            return mRegion!!.size
    }

    private fun add(g: Images?) {
        mRegion?.add(g)
        if (mRegion != null) {
            notifyItemInserted(mRegion.size - 1)
        }
    }

    fun addAll(listRegion: MutableList<Images?>?) {
        if (listRegion != null) {
            for (result in listRegion) {
                add(result)
            }
        }
    }

    class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView? = itemView.findViewById<ImageView?>(R.id.image)
        var id: TextView? = itemView.findViewById<TextView?>(R.id.id)
        var title: TextView? = itemView.findViewById<TextView?>(R.id.title)
    }

    inner class LoadingVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        init {
            v2?.visibility = View.GONE
        }
    }

    protected class NoResultVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    init {
        mRegion = ArrayList()
    }
}