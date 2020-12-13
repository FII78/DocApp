
package com.example.docapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.FeedAdapter.OnItemClickListener
import com.example.docapp.models.health_feed
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class feedViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)
class FeedActivity : AppCompatActivity(){
    var db=FirebaseFirestore.getInstance()
    lateinit var adapter:FeedAdapter
    val  query=db.collection("health_feeds")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_feed)

        val buttonAdd:FloatingActionButton=findViewById(R.id.floating_action_button)
        buttonAdd.setOnClickListener{
            ///start new intent
            val intent = Intent(this@FeedActivity, New_Feed_Doctor_Activity::class.java)
            startActivity(intent)

        }
        setupRecylerView()
        adapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(position: Int) {
//                val note = documentSnapshot.toObject(Note::class.java)
//                val id = documentSnapshot.id
//                val path = documentSnapshot.reference.path
                Toast.makeText(this@FeedActivity, "Position:clicked", Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun setupRecylerView() {
         val queryies=query.orderBy("date_posted",Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<health_feed> = FirestoreRecyclerOptions.Builder<health_feed>().setQuery(queryies,health_feed::class.java).build()
        adapter= FeedAdapter(options)
        val recyclerview=findViewById<RecyclerView>(R.id.recycleView)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
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

//    override fun onItemCLick(documentSnapshot: DocumentSnapshot, position: Int) {
//        val feed = documentSnapshot.toObject(health_feed::class.java)
//        val id = documentSnapshot.id
//        val path = documentSnapshot.reference.path
//        Toast.makeText(
//            this@FeedActivity,
//            "Position: $position ID: $id", Toast.LENGTH_SHORT
//        ).show()
//    }
}