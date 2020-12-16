package com.example.docapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docapp.R
import com.example.docapp.models.Questions
import com.example.lastone.RecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_ask_q.*
import kotlinx.android.synthetic.main.fragment_ask_q.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
// Access a Cloud Firestore instance from your Activity
val db = Firebase.firestore
lateinit var data : FirebaseFirestore
var dbs= FirebaseFirestore.getInstance()
lateinit var adapter: RecyclerAdapter
val  query=dbs.collection("questions")
/**
 * A simple [Fragment] subclass.
 * Use the [AskQFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AskQFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
            pplsHelped.text="445"
            senData()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AskQFragment.
         */
        // TODO: Rename and change types and number of parameters
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
    private fun senData(){
        // asqBtn.setOnClickListener{
        // Toast.makeText(getActivity(),"Registration Succes",Toast.LENGTH_LONG).show()
        var desc = descQ.text.toString().trim()
//            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                LocalDateTime.now()
//            } else {
//                TODO("VERSION.SDK_INT < O")
//            }
//
//            val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//            } else {
//                TODO("VERSION.SDK_INT < O")
//            }
//            val askedAt = current.format(formatter)
        val current = LocalDateTime.now()
        val formatter= DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val askedAt = current.format(formatter)

        if(desc.isNotEmpty()){
            var model= Questions(desc,askedAt)
            data.collection("questions")
                .add(model)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    desc =""
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
        // }

    }
    private fun setupRecylerView() {
        val queryies=query.orderBy("asked_at", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<Questions> = FirestoreRecyclerOptions.Builder<Questions>().setQuery(queryies,Questions::class.java).build()
        //val options: FirestoreRecyclerOptions<health_feed> = FirestoreRecyclerOptions.Builder<health_feed>().setQuery(queryies,health_feed::class.java).build()

        adapter= RecyclerAdapter(options)
        //val recyclerview=findViewById<RecyclerView>(R.id.recycleView)
        val recyclerview = recycleView
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = adapter
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