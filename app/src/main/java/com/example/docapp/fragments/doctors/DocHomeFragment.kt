package com.example.docapp.fragments.doctors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.docapp.ChatActivity
import com.example.docapp.MessageChatActivity
import com.example.docapp.R
import com.example.docapp.doctors.AnswerDialog
import com.example.docapp.fragments.ChatsFragment
import com.example.docapp.fragments.SearchFragment
import com.example.docapp.fragments.doctors.health_feedFragment
import com.example.docapp.fragments.patients.AskQFragment
import com.example.docapp.fragments.patients.DocListFragment

import com.example.docapp.models.User
import com.example.docapp.patients.UserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.cardqnadoc.view.*
import kotlinx.android.synthetic.main.fragment_doct_home.*
import kotlinx.android.synthetic.main.fragment_doct_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DocHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    var dbs= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view : View =inflater.inflate(R.layout.fragment_doct_home, container, false)
        view.askCardDoc.setOnClickListener{
            val fraG = DocQnAFragment()
            replaceFragments(fraG)
        }
        view.feedsCardDoc.setOnClickListener{
            val fraG = health_feedFragment()
            replaceFragments(fraG)
        }
        view.consultsCardDoc.setOnClickListener{
            val fraG = SearchFragment()
            replaceFragments(fraG)
        }
        view.msgCardDoc.setOnClickListener{
//            val fraG = ChatsFragment()
//            replaceFragments(fraG)
            val intent= Intent(activity, ChatActivity::class.java)
            startActivity(intent)
        }

        getUSerDetails()
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DocHomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun replaceFragments(fragment: Fragment) {
        if(fragment != null ){
            var  fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_doc,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
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
        dbs.collection("users")
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    val  user=document.toObject(User::class.java)!!
                    useNmeLblDoc.text="Hello, ${user.userName.capitalize()} "
                    ///if activity = feed and role = doc->
                    ///else normal news feed

                }}
}