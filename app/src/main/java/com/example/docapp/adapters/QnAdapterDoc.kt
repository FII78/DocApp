package com.example.docapp.adapters
//import com.example.docapp.fragments.QuestionFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.doctors.AnswerDialog
import com.example.docapp.fragments.doctors.DocQnAFragment
import com.example.docapp.fragments.patients.QuestionFragment
import com.example.docapp.models.Questions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.cardqnadoc.view.*

class QnAdapterDoc(options: FirestoreRecyclerOptions<Questions>)
    :FirestoreRecyclerAdapter<Questions, QnAdapterDoc.QuestionHolder>(options) {
    lateinit var mCallback : Callback

    inner class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemDesc: TextView
        var createdAtc: TextView
        var tag : TextView
        var numAns : TextView
        lateinit var ansBtn : Button

        init {
            itemDesc = itemView.findViewById(R.id.descCdoc)
            createdAtc = itemView.findViewById(R.id.asked_atDoc)
            ansBtn = itemView.findViewById(R.id.btnAnswerDoc)
            tag = itemView.findViewById(R.id.tagDoc)
            numAns = itemView.findViewById(R.id.numAns)
            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
            }
        }}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        // TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardqnadoc, parent, false)
        return  QuestionHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionHolder, position: Int, model: Questions) {
        // TODO("Not yet implemented")
        holder.itemDesc.text=model.description
        holder.createdAtc.text= model.asked_at
        holder.tag.text = model.tag.capitalize()
        holder.numAns.text = "${ model.answers.size } answers"

            holder.itemView.btnAnswerDoc.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    // setCallback()
                    mCallback.onCardClicked(model)
                }
            })
//
//        holder.itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                val activity = v!!.context as AppCompatActivity
//                val qFrag = QuestionFragment()
//                activity.supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_doc, qFrag).addToBackStack(null).commit()
//            }
//        })
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // setCallback()
                mCallback.onCardClicked(model)
            }
        })
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    interface Callback{
        fun onCardClicked(question: Questions)
        fun onBtnClicked(question: Questions)
    }

}