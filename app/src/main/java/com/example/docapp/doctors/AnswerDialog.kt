package com.example.docapp.doctors

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.docapp.R
import com.example.docapp.fragments.patients.dbs
import com.example.docapp.models.Questions
import kotlinx.android.synthetic.main.fragment_ask_q.descQ
import kotlinx.android.synthetic.main.fragment_dialog.*
import kotlinx.android.synthetic.main.fragment_dialog.view.*
import kotlinx.android.synthetic.main.fragment_doc_qn_a.*

class AnswerDialog : DialogFragment(){

    lateinit var model : Questions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return super.onCreateView(inflater, container, savedInstanceState)
        var view:View = inflater.inflate(R.layout.fragment_dialog,container,false)
        view.canclBtn.setOnClickListener{
            dismiss()
        }
        view.respBtn.setOnClickListener{

        }
        return  view
    }
    private fun senData() {
        //Toast.makeText(getActivity(),"Registration Succes",Toast.LENGTH_LONG).show()
        var answer = answers.text.toString().trim()

        if (TextUtils.isEmpty(answer)) {
            descQ.error = "Please insert your answer"
        } else if (answer.length < 2) {
            descQ.error = "Please insert more than 2 characters"

        } else {
           var model = Questions("","",answer)

            dbs.collection("questions")
                .add(model)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    descQ.text.clear()
                    getTag.text.clear()
                    Toast.makeText(activity, "Question submitted", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                    Toast.makeText(activity, "$e", Toast.LENGTH_LONG).show()
                }
        }
    }
}