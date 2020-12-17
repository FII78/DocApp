package com.example.docapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.adapters.DocAdapter
import com.example.docapp.models.User
import com.example.docapp.models.health_feed
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class DocListFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView
    lateinit var postAdapter: DocAdapter


    private var db=FirebaseFirestore.getInstance()
    val  query=db.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentView: View =
                inflater.inflate(R.layout.doc_list_home, container, false)

        postRecyclerView = fragmentView.findViewById(R.id.recycleViewdoc)
        postRecyclerView.layoutManager = LinearLayoutManager(activity)
        postRecyclerView.setHasFixedSize(true)
        postRecyclerView.adapter = postAdapter
        val queries=query.orderBy("",Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<User> = FirestoreRecyclerOptions.Builder<User>().setQuery(queries,User::class.java).build()
        postAdapter = DocAdapter(options)
        return fragmentView

    }


    override fun onStart() {
        super.onStart()

        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }
}