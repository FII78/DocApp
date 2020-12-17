package com.example.docapp.fragments.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.docapp.R
import com.example.docapp.models.Questions
import com.example.docapp.adapters.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_question.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment(), RecyclerAdapter.Callback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dbs = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View= inflater.inflate(R.layout.fragment_question, container, false)
        getDetails()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getDetails() {
        dbs.collection("questions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e("TAG", "${document.id} => ${document.data}")
                    Toast.makeText(activity, "${document.data}", Toast.LENGTH_LONG).show()
                    quesDescription.text = document.getString("description")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "get failed with ", exception)
            }
    }

    override fun onCardClicked(question: Questions) {
        TODO("Not yet implemented")
    }
}