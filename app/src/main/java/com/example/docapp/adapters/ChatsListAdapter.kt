package com.example.docapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.MessageChatActivity
import com.example.docapp.R
import com.example.docapp.models.User

class ChatsListAdapter (
        mContext : Context,
        mUsers:List<User>
) : RecyclerView.Adapter<ChatsListAdapter.ViewHolder?>(){


        private val mContext: Context = mContext
        private val  mUsers:List<User> = mUsers

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

            var name: TextView = itemView.findViewById(R.id.full_name_search_U)


//       var hours_avail: TextView = itemView.findViewById(R.id.hours_avail)
//       var experience: TextView = itemView.findViewById(R.id.experience)
//       var specialization: TextView = itemView.findViewById(R.id.specialization)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_search_layout,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mUsers.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val user: User =mUsers[position]


            if(user.role=="doctor")
            {
                val nameh="Dr ${user.fullName}"
                holder.name.text = nameh

            }
            else {
                holder.name.text = user.fullName


                }


//            holder.experience.text = user.experience
//            holder.hours_avail.text = user.hoursAvailablePerWeek
//            holder.specialization.text = user.specialization

            holder.itemView.setOnClickListener {
                        val intent = Intent(mContext, MessageChatActivity::class.java)
                        intent.putExtra("visit_id", user.id)
                        mContext.startActivity(intent)
                }

            }



        }



