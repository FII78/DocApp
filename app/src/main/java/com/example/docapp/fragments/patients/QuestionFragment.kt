package com.example.docapp.fragments.patients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.docapp.R
import com.example.docapp.adapters.RecyclerAdapter
import com.example.docapp.models.AnsList
import com.example.docapp.models.Questions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment() : Fragment(), RecyclerAdapter.Callback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  var p:Int ? = null
    var dbs = FirebaseFirestore.getInstance()
    private lateinit var argQuestions: Questions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
           // p = it.getInt(num)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View= inflater.inflate(R.layout.fragment_question, container, false)
//        Log.e("kk", "aregs ${argQuestions.answers[0]} ")
//        for (i in argQuestions.answers){
//            Log.e("kk","i ${i.answer}")
//        }
///        Toast.makeText(activity, "${argQuestions.answers[0]}", Toast.LENGTH_LONG).show()
        //getDetails()
       // getLord()
        getAnswers()
        view.quesTag.text = argQuestions.tag
        view.queAskedAt.text = argQuestions.asked_at
        view.queDescriptions.text = argQuestions.description
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(question: Questions) =
            QuestionFragment().apply {
                argQuestions = question
                //arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                 //  arguments=question
               // }
             //   arguments = question
            }
    }
    private fun getAnswers(){
       val keyList = ArrayList(argQuestions.answers)
        val valueList = ArrayList(argQuestions.answers)
        val Ans: List<AnsList> = argQuestions.answers.toList()
        Log.e("Ans","${Ans}")

        for (i in argQuestions.answers){
            Log.e("kk","i ${i.answer}")
        }
    }

    private fun getDetails() {
        dbs.collection("questions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e("TAG", "${document.id} => ${document.data}")
                    Toast.makeText(activity, "${document.data}", Toast.LENGTH_LONG).show()
                   // quesDescription.text = document.getString("description")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "get failed with ", exception)
            }
    }

    private fun getLord(){
        quesTag.text = argQuestions.description
    }

    override fun onCardClicked(question: Questions) {
        TODO("Not yet implemented")
    }
}