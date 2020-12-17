package com.example.docapp.doctors
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
import com.example.docapp.fragments.doctors.DocQnAFragment
import com.example.docapp.models.Questions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class QnAdapterDoc(options: FirestoreRecyclerOptions<Questions>)
    :FirestoreRecyclerAdapter<Questions, QnAdapterDoc.QuestionHolder>(options) {
    inner class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemDesc: TextView
        var createdAtc: TextView
        lateinit var ansBtn : Button

        init {
            itemDesc = itemView.findViewById(R.id.descCdoc)
            createdAtc = itemView.findViewById(R.id.asked_atDoc)
            ansBtn = itemView.findViewById(R.id.btnAnswerDoc)

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
        holder.ansBtn.setOnClickListener{

            Log.e("tag", "error")
            val fragmentManager: FragmentManager =
                (it.getContext() as FragmentActivity).supportFragmentManager
            //val ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()

            val dialog = AnswerDialog()
          //  var  fragmentManager = (getActivity() as FragmentActivity).supportFragmentManager
           // var frag = supportFragmentManager
        dialog.show(fragmentManager,"dialog")
        }
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val qFrag = DocQnAFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_doc, qFrag).addToBackStack(null).commit()
            }
        })
    }

}