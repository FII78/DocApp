package com.example.docapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.MessageChatActivity
import com.example.docapp.R
import com.example.docapp.adapters.DocAdapter2.ViewHolder
import com.example.docapp.models.User
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView

class DocAdapter2(
        mContext :Context,
    mUsers:List<User>
) : RecyclerView.Adapter<DocAdapter2.ViewHolder?>() {
    private val mContext:Context = mContext
    private val  mUsers:List<User> = mUsers

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

       var name: TextView = itemView.findViewById(R.id.full_name_search)


//       var hours_avail: TextView = itemView.findViewById(R.id.hours_avail)
//       var experience: TextView = itemView.findViewById(R.id.experience)
//       var specialization: TextView = itemView.findViewById(R.id.specialization)

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(mContext).inflate(R.layout.user_search_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user:User=mUsers[position]

        if(user!!.role=="doctor")
        {

            holder.name.text = "Dr. ${user!!.fullName}"
        }
        else{

        }

//            holder.experience.text = user.experience
//            holder.hours_avail.text = user.hoursAvailablePerWeek
//            holder.specialization.text = user.specialization

    holder.itemView.setOnClickListener {
        val options = arrayOf<CharSequence>("Consult Now", "Visit Profile")
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setTitle("What do u want to do?")
        builder.setItems(options) {
            dialog, position ->
            if (position == 0) {
                val intent = Intent(mContext, MessageChatActivity::class.java)
                intent.putExtra("visit_id", user.id)
                mContext.startActivity(intent)
            }
            if (position == 1) {

            }
        }
        builder.show()
    }



    }


}