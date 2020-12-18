package com.example.docapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.docapp.R
import com.example.docapp.adapters.DocAdapter2
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var docAdapter: DocAdapter2? = null
    private var mUserss: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
private fun retrieveAllUsers(){
    val  firebaseUser=FirebaseAuth.getInstance().currentUser!!.uid
    val reference= FirebaseDatabase.getInstance().reference.child("Users")
    reference.addValueEventListener(object :ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            (mUserss as ArrayList<User>).clear()
            for(snapshot in snapshot.children)
            {
                val user:User?=snapshot.getValue(User::class.java)
                if (!(user!!.id).equals(firebaseUser)){
                    (mUserss as ArrayList<User>).add(user)
                }
            }
            docAdapter= DocAdapter2(context!!,mUserss!!)
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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}