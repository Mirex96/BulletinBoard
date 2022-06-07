package com.example.bulletinboard.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bulletinboard.R
import java.util.ArrayList

class RcViewDialogSpinner : RecyclerView.Adapter<RcViewDialogSpinner.SpViewHolder>() {
    val mainList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
        return SpViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        holder.setData(mainList[position])
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    inner class SpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSpItem: TextView = itemView.findViewById(R.id.tvSpItem)
        fun setData(text: String) {
            tvSpItem.text = text
        }
    }


    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()

    }
}