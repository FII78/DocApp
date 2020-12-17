package com.example.docapp.fragments.patients

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
import com.example.docapp.models.Questions
import com.example.docapp.models.User
import com.example.docapp.patients.RecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.cardrecl.*
import kotlinx.android.synthetic.main.fragment_ask_q.*
import kotlinx.android.synthetic.main.fragment_ask_q.view.*
import kotlinx.android.synthetic.main.fragment_doc_qn_a.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"
    val db = Firebase.firestore
    lateinit var data : FirebaseFirestore
    var dbs= FirebaseFirestore.getInstance()
    lateinit var adapter: RecyclerAdapter
    val  query=dbs.collection("questions")
    val users = dbs.collection("users")

class AskQFragment  : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        data = FirebaseFirestore.getInstance()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ask_q, container, false)
        view.asqBtn.setOnClickListener{
            Log.d("btnSetup", "Selected")
            //pplsHelped.text="445"
            senData()
        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AskQFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        setupRecylerView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun senData() {
        //Toast.makeText(getActivity(),"Registration Succes",Toast.LENGTH_LONG).show()
        var desc = descQ.text.toString().trim()
        var tag = getTagUser.text.toString().trim()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val askedAt = current.format(formatter)

        if (TextUtils.isEmpty(desc)) {
            descQ.error = "Please insert description for your question"
        } else if (desc.length < 10) {
            descQ.error = "Please insert more than 10 characters"
        } else if (TextUtils.isEmpty(tag)) {
            getTagUser.error = "Insert tag for your question"
        } else if (tag.length < 3) {
            getTagUser.error = "Insert more than 3 characters"
        } else {
            var model = Questions(desc, askedAt, tag)
            dbs.collection("questions")
                .add(model)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    descQ.text.clear()
                    getTagUser.text.clear()
                    Toast.makeText(activity, "Question submitted", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                    Toast.makeText(activity, "$e", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun setupRecylerView() {
        val queryies=query.orderBy("asked_at", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Questions> = FirestoreRecyclerOptions.Builder<Questions>().setQuery(queryies,Questions::class.java).build()

        adapter= RecyclerAdapter(options)
        //val recyclerview=findViewById<RecyclerView>(R.id.recycleView)
        val recyclerview = recycleView
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = adapter
    }
    fun getCurrentUserId():String{
        val currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }

    private fun getUSerDetails () {
       // var user:User
        dbs.collection("users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
              val user=document.toObject(User::class.java)!!
                if(user.role == "doctor"){
                    Toast.makeText(activity,"Question submited",Toast.LENGTH_LONG).show()
                        //btnAnswer.toggleVisibility()
                }
            }

    }
    fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
        }
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