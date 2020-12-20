package com.example.docapp.fragments.doctors

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.docapp.R
import com.example.docapp.adapters.QnAdapterDoc
import com.example.docapp.fragments.patients.QuestionFragment
import com.example.docapp.models.AnsList
import com.example.docapp.models.Questions
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_answer_q.*
import kotlinx.android.synthetic.main.fragment_answer_q.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var argQuestions: Questions

class AnswerQFragment : Fragment(),QnAdapterDoc.Callback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var ansdBy:String = ""
    var dbs= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        getUSerDetails()
       // respBtn.setOnClickListener{
           // update()
       // }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view:View = inflater.inflate(R.layout.fragment_answer_q, container, false)
       // Toast.makeText(activity,"${argQuestions.documentId}",Toast.LENGTH_LONG).show()
        view.respBtn.setOnClickListener{
            update()
        }
        view.canclBtn.setOnClickListener{
            var fragment = DocQnAFragment()
            var  fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_doc,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    companion object {
        fun newInstance(question: Questions) =
            AnswerQFragment().apply {
                argQuestions = question

                }
    }
    // update data
    private fun update(){
       // respBtn.setOnClickListener{
            //Toast.makeText(activity,"clicked", Toast.LENGTH_LONG).show()

            var id : String = argQuestions.documentId
            val answer : String =answerQ.toString().trim()

            if(TextUtils.isEmpty(answer)){
                answerQ.error = "Please insert your response !!"
            }else{
                val ansData = AnsList(answer,ansdBy)
                val list : ArrayList<AnsList> = arrayListOf();
                list.add(ansData)
                //serilize to map
                val secMap = list.associateBy({it.answer},{it.ansdBy})
               // val data : Map<String,AnsList> = hashMapOf()
                //data.
                dbs.collection("questions")
                      .document(id)
                       .update("answers",FieldValue.arrayUnion(secMap))
                        //.set({ "answers":list },{SetOptions()})
                        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!")
                        Toast.makeText(activity,"Answered", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
            }
        //}


    }

    override fun onCardClicked(question: Questions) {
        TODO("Not yet implemented")
    }

    override fun onClicked(question: Questions) {
        TODO("Not yet implemented")
    }
//
//    override fun onBtnClicked(question: Questions) {
//        TODO("Not yet implemented")
//    }

    fun getCurrentUserId():String{
        val currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }

    private fun getUSerDetails () {
        dbs.collection("users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
                val  user=document.toObject(User::class.java)!!
                ansdBy=user.userName.capitalize()
                ///if activity = feed and role = doc->
                ///else normal news feed

            }}
}