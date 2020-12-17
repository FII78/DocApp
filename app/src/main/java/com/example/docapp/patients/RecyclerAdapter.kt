package com.example.docapp.patients

//import com.example.docapp.fragments.QuestionFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.fragments.patients.QuestionFragment
import com.example.docapp.models.Questions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class RecyclerAdapter(options: FirestoreRecyclerOptions<Questions>)
    :FirestoreRecyclerAdapter<Questions, RecyclerAdapter.QuestionHolder>(options) {

    lateinit var mCallback : Callback
    inner class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemDesc: TextView
        var createdAtc: TextView
        var tag : TextView
        init {
            itemDesc = itemView.findViewById(R.id.descC)
            createdAtc = itemView.findViewById(R.id.asked_at)
            tag= itemView.findViewById(R.id.getTagUserVal)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                Log.e("taag","$position")
            }
        }}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        // TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardrecl, parent, false)
        return  QuestionHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionHolder, position: Int, model: Questions) {
        // TODO("Not yet implemented")

        holder.itemDesc.text=model.description
        holder.createdAtc.text= model.asked_at
        holder.tag.text = model.tag
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val activity = v!!.context as AppCompatActivity
                val qFrag = QuestionFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, qFrag).addToBackStack(null).commit()
            }
        })
    }

    fun setCallback(callback: Callback) {
         mCallback = callback
    }

    interface Callback{
      fun onCardClicked(question: Questions)
   }
}