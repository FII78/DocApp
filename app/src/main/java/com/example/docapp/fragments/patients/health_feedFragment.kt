package com.example.docapp.fragments.patients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.models.health_feed
import com.example.docapp.patients.FeedAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class health_feedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var postRecyclerView: RecyclerView
    lateinit var postAdapter: FeedAdapter

    lateinit var firebaseDatabase: FirebaseFirestore
  //  var databaseReference: DataBaseRef? = null
  lateinit var postList: List<health_feed>
    // Access a Cloud Firestore instance from your Activity
    var db=FirebaseFirestore.getInstance()
    val  query=db.collection("health_feeds")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentView: View =
            inflater.inflate(R.layout.feed_home, container, false)
        val queries=query.orderBy("date_posted",Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<health_feed> = FirestoreRecyclerOptions.Builder<health_feed>().setQuery(queries,health_feed::class.java).build()

        postAdapter = FeedAdapter(options)
        postRecyclerView = fragmentView.findViewById(R.id.recycleView)
        postRecyclerView.layoutManager = LinearLayoutManager(activity)
        postRecyclerView.setHasFixedSize(true)
        postRecyclerView.adapter = postAdapter

        return fragmentView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment health_feedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                health_feedFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
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