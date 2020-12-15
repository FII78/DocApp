package com.example.lastone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
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
//                val intent = Intent(context, DetailPertanyaan::class.java).apply {
//                    putExtra("NUMBER", position)
//                    putExtra("CODE", itemKode.text)
//                    putExtra("CATEGORY", itemKategori.text)
//                    putExtra("CONTENT", itemIsi.text)
//                }
            // context.startActivity(intent)

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
    }

}