package com.example.docapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.adapters.FeedAdapter
import com.example.docapp.models.health_feed
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.feed_home_patient.*


class patient_health_feedFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView
    lateinit var postAdapter: FeedAdapter
    

    private var db=FirebaseFirestore.getInstance()
    val  query=db.collection("health_feeds")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentView: View =
            inflater.inflate(R.layout.feed_home_patient, container, false)


        return fragmentView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private  fun setRecyclerView(){
        val queries=query.orderBy("date_posted",Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<health_feed> = FirestoreRecyclerOptions.Builder<health_feed>().setQuery(queries,health_feed::class.java).build()

        postRecyclerView = recycleView_patient_feed
        postRecyclerView.layoutManager = LinearLayoutManager(activity)
        postRecyclerView.setHasFixedSize(true)
        postAdapter = FeedAdapter(options)
        postRecyclerView.adapter = postAdapter


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