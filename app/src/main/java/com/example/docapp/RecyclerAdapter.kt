package com.example.lastone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.fragments.QuestionFragment
import com.example.docapp.models.Questions

class RecyclerAdapter (options: FirestoreRecyclerOptions<Questions>)
    :FirestoreRecyclerAdapter<Questions, RecyclerAdapter.QuestionHolder>(options) {
    inner class QuestionHolder(itemView: View ) : RecyclerView.ViewHolder(itemView){

        var itemDesc: TextView
        var createdAtc: TextView

        init {
            itemDesc = itemView.findViewById(R.id.descC)
            createdAtc = itemView.findViewById(R.id.asked_at)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
            }
        }}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.QuestionHolder {
        // TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardrecl, parent, false)
        return  QuestionHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionHolder, position: Int, model: Questions) {
        // TODO("Not yet implemented")
        holder.itemDesc.text=model.description
        holder.createdAtc.text= model.asked_at
        holder.itemView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val qFrag = QuestionFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,qFrag).addToBackStack(null).commit()
            }
        })
    }

}