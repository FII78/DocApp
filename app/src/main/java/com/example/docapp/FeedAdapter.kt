package com.example.docapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.models.health_feed
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot


class FeedAdapter(options: FirestoreRecyclerOptions<health_feed>
) : FirestoreRecyclerAdapter<health_feed, FeedAdapter.FeedHolder>(options) {
  private var listener: OnItemClickListener? =null


      inner class FeedHolder(itemView: View ) : RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.title)
        var supporting_stc: TextView = itemView.findViewById(R.id.supp_text)
          var description: TextView = itemView.findViewById(R.id.description_text_display)

        //var image:ImageView
        var date_posted: TextView = itemView.findViewById(R.id.date_posted)

        init {
            // image=itemView.findViewById(R.id.img_feed)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener!!.onItemClick( position)
                }
            }
            //snapshots.getSnapshot(position),
        }

//          override fun onClick(v: View?) {
//              TODO("Not yet implemented")
//          }


      }


    interface OnItemClickListener {


        fun onItemClick( position: Int)
        //documentSnapshot: DocumentSnapshot,
    }
//
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.FeedHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_feed_card, parent, false)
        return  FeedHolder(view)
    }

    override fun onBindViewHolder(holder: FeedAdapter.FeedHolder, position: Int, model: health_feed) {
        holder.title.text = model.title
        holder.supporting_stc.text = model.supporting_stc
        holder.date_posted.text = model.date_posted
        holder.description.text = model.description
        // holder.image.setImageURI(model.image)
    }

}
