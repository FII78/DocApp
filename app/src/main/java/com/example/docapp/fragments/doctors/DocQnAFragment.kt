package com.example.docapp.fragments.doctors

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docapp.R
import com.example.docapp.adapters.QnAdapterDoc
import com.example.docapp.models.Questions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_doc_qn_a.*
import kotlinx.android.synthetic.main.fragment_doc_qn_a.descQdoc
import kotlinx.android.synthetic.main.fragment_doc_qn_a.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DocQnAFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DocQnAFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var data : FirebaseFirestore
    var dbs= FirebaseFirestore.getInstance()
    lateinit var adapter: QnAdapterDoc
    val  query=dbs.collection("questions")
    val users = dbs.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view:View=inflater.inflate(R.layout.fragment_doc_qn_a, container, false)
        view.asqBtndoc.setOnClickListener{
            Log.d("btnSetup", "Selected")
            //pplsHelped.text="445"
            senData()
        }
//        view.btnAnswerDoc.setOnClickListener{
//            val dialog = AnswerDialog()
//            var  fragmentManager = (activity as FragmentActivity).supportFragmentManager
//            dialog.show(fragmentManager,"dialog")
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecylerView()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DocQnAFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun setupRecylerView() {
        val queryies= query.orderBy("asked_at", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Questions> = FirestoreRecyclerOptions.Builder<Questions>().setQuery(queryies,
            Questions::class.java).build()

        adapter = QnAdapterDoc(options)
        //val recyclerview=findViewById<RecyclerView>(R.id.recycleView)
        val recyclerview = recycleDocView
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = adapter
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun senData() {
        //Toast.makeText(getActivity(),"Registration Succes",Toast.LENGTH_LONG).show()
        var desc = descQdoc.text.toString().trim()
        var tag = getTag.text.toString().trim()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val askedAt = current.format(formatter)

        if (TextUtils.isEmpty(desc)) {
            descQdoc.error = "Please insert description for your question"
        } else if (desc.length < 10) {
            descQdoc.error = "Please insert more than 10 characters"
        } else if (TextUtils.isEmpty(tag)) {
            getTag.error = "Insert tag for your question"
        } else if (tag.length < 3) {
            getTag.error = "Insert more than 3 characters"
        } else {
            var model = Questions(desc, askedAt, tag)
            dbs.collection("questions")
                .add(model)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    descQdoc.text.clear()
                    getTag.text.clear()
                    Toast.makeText(activity, "Question submitted", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                    Toast.makeText(activity, "$e", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun answer(){

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}