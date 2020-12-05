package com.jeleniep.publicationManager.ui.publicationsList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.jeleniep.publicationManager.R
import kotlinx.android.synthetic.main.publication_list_item.view.*

class PublicationAdapter(
    list: List<PublicationListItem>,
    private val listener: OnItemClickListener
) :
    Adapter<PublicationAdapter.PublicationViewHolder>() {

    var publicationList: List<PublicationListItem> = list
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class PublicationViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        val publicationName: TextView = itemView.publication_name
        val authors: TextView = itemView.publication_authors

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.publication_list_item, parent, false)
        return PublicationViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val currentItem = publicationList[position]
        holder.publicationName.text = currentItem.name;
        holder.authors.text = currentItem.authors;
    }

    override fun getItemCount() = publicationList.size;

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}