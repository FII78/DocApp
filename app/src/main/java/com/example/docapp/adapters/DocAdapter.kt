package com.example.docapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.adapters.FeedAdapter
import com.example.docapp.R
import com.example.docapp.adapters.DocAdapter.DocHolder
import com.example.docapp.models.User
import com.example.docapp.models.health_feed
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class DocAdapter (options: FirestoreRecyclerOptions<User>) : FirestoreRecyclerAdapter<User, DocHolder>(options){

        inner class DocHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        {
            var title: TextView = itemView.findViewById(R.id.Doc_name)
            var hours_avail: TextView = itemView.findViewById(R.id.hours_avail)
            var experience: TextView = itemView.findViewById(R.id.experience)

            var specialization: TextView = itemView.findViewById(R.id.specialization)

            var image:ImageView=itemView.findViewById(R.id.imageView_profile)




        }


//    internal interface OnActionListener {
//        fun startActivity(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener
//    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.doc_list_card, parent, false)
            return DocHolder(view)
        }

        override fun onBindViewHolder(holder: DocHolder, position: Int, model: User) {
            if(model.role=="doctor") {
                holder.title.text = model.fullName
                holder.experience.text= model.experience
                holder.hours_avail.text = model.hoursAvailablePerWeek
                holder.specialization.text = model.specialization
                var sex = model.sex
                if (sex=="female")
                {

                }
                else
                {

                }
            }


//        holder.itemView.setOnClickListener {
//            var feedId=snapshots.getSnapshot(position).id
//            Toast.makeText(holder.itemView.context, feedId, Toast.LENGTH_SHORT).show()
//            val i = Intent(context, LoginActivity::class.java)
//            val extras = Bundle()
//            extras.putString("EXTRA_FEED_ID",feedId )
//            context.startActivity(i)
//        }

        }

    }

