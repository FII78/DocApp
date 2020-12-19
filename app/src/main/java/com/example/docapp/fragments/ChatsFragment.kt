package com.example.docapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.adapters.ChatsListAdapter
import com.example.docapp.adapters.DocAdapter2
import com.example.docapp.models.User
import com.example.docapp.models.chatList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatsFragment : Fragment() {
    private var userAdapter: ChatsListAdapter? = null
    private var mUsers: List<User>? = null
    lateinit var recyclerView: RecyclerView
    private var usersChatList: List<chatList>? = null
    private var firebaseUser:FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_chats, container, false)
        recyclerView=view.findViewById(R.id.recycler_view_chat_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(context)
        firebaseUser=FirebaseAuth.getInstance().currentUser
        usersChatList=ArrayList()
        val ref=FirebaseDatabase.getInstance().reference.child("ChatList").child(firebaseUser!!.uid)
        ref!!.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                (usersChatList as ArrayList).clear()
                for (snapshot in snapshot.children)
                {
                    val chatList=snapshot.getValue(chatList::class.java)
                    (usersChatList as ArrayList).add(chatList!!)
                }
                retrieveChatList()

            }

        })
        return view
    }

private fun retrieveChatList(){
    mUsers=ArrayList()
    val ref=FirebaseDatabase.getInstance().reference.child("users")
    ref!!.addValueEventListener(object :ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            (mUsers as ArrayList).clear()
            for(snapshot in snapshot.children) {
                val user: User? = snapshot.getValue(User::class.java)
                for (eachChatList in usersChatList!!) {
                    if (user!!.id.equals(eachChatList.id))
                    {
                        (mUsers as ArrayList<User>).add(user!!)
                    }

                }
            }
            userAdapter=ChatsListAdapter(context!!,(mUsers as ArrayList<User>))
            recyclerView.adapter=userAdapter
            }
            })
}


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}