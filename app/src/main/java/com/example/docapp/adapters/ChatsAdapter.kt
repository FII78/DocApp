package com.example.docapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.models.chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class ChatsAdapter(
        mContext:Context,
        mChatList:List<chat>
):RecyclerView.Adapter<ChatsAdapter.ViewHolder>()
{
    private var mContext:Context
    private var mChatList:List<chat>
    var firebaseUser:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
    init {
        this.mChatList=mChatList
        this.mContext=mContext
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var profile_image:CircleImageView?=itemView.findViewById(R.id.profile_image)
        var show_text_message: TextView?=itemView.findViewById(R.id.show_text_message)
        var text_seen:TextView?=itemView.findViewById(R.id.text_seen)

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
return if(position==1)
{
    val view:View= LayoutInflater.from(mContext).inflate(R.layout.message_item_right,parent,false)
    ViewHolder(view)

}
        else{
    val view:View= LayoutInflater.from(mContext).inflate(R.layout.message_item_left,parent,false)
    ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val chat:chat=mChatList[position]


            holder.show_text_message!!.text=chat.message

        if(position==mChatList.size-1){
            holder.text_seen!!.text="seen"

        }
        else{
            holder.text_seen!!.visibility=View.GONE
        }


          }

    override fun getItemViewType(position: Int): Int {

        return if(mChatList[position].sender.equals(firebaseUser!!.uid)) 1
        else 0
    }
}