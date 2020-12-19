package com.example.docapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.R
import com.example.docapp.adapters.DocAdapter2
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList

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
    private var mUsers: List<User>? = null
    private var recyclerView: RecyclerView? = null
    private var searchET: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView=view.findViewById(R.id.searchList)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager=LinearLayoutManager(context)
        mUsers=ArrayList()
        searchET=view.findViewById(R.id.searchUserET)

        retrieveAllUsers()

        searchET!!.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                searchForUsers(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
             searchForUsers(cs.toString().toLowerCase(Locale.ROOT))
            }


        })



                  return  view
    }


    private fun retrieveAllUsers(){
    val  firebaseUserId=FirebaseAuth.getInstance().currentUser!!.uid
    val reference= FirebaseDatabase.getInstance().reference.child("users")
            .orderByChild("role")
    reference.addValueEventListener(object :ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onDataChange(po: DataSnapshot) {
            (mUsers as ArrayList<User>).clear()
           if(searchET!!.text.toString()=="")
           {
               for(snapshot in po.children)
               {
                   val user:User?=snapshot.getValue(User::class.java)

                   if (!(user!!.id).equals(firebaseUserId)){
                       (mUsers as ArrayList<User>).add(user)
                   }

               }
               docAdapter= DocAdapter2(context!!,mUsers!!)
               recyclerView!!.adapter=docAdapter
           }


        }
    })
}
    private fun searchForUsers(str:String){
        val  firebaseUserId=FirebaseAuth.getInstance().currentUser!!.uid
//        val queryusers= FirebaseDatabase.getInstance().reference.child("c")
//                .orderByChild("userName")
//                .startAt(str)
//                .endAt(str+"\uf8ff")
        var queryusers=FirebaseDatabase.getInstance().reference.child("users")
                .orderByChild("fullName")
                .startAt(str)
                .endAt(str+"\uf8ff")
         queryusers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(po: DataSnapshot) {
                (mUsers as ArrayList<User>).clear()
                for (snapshot in po.children) {
                    val user: User? = snapshot.getValue(User::class.java)

                    if (!(user!!.id).equals(firebaseUserId)) {
                        (mUsers as ArrayList<User>).add(user)
                    }

                }
                docAdapter = DocAdapter2(context!!, mUsers!!)
                recyclerView!!.adapter = docAdapter
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