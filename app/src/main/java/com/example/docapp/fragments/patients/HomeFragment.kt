package com.example.docapp.fragments.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.docapp.DocListFragment
import com.example.docapp.R
import com.example.docapp.UserInfo
<<<<<<< HEAD:app/src/main/java/com/example/docapp/fragments/patients/HomeFragment.kt
//import com.example.docapp.health_feedFragment
=======
import com.example.docapp.health_feedFragment
>>>>>>> 30442ede5454578165ccb0cb35b9853ee9d7aef9:app/src/main/java/com/example/docapp/fragments/HomeFragment.kt
import com.example.docapp.models.User
//import com.example.docapp.patients.health_feedFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    var dbs= FirebaseFirestore.getInstance()
    val userInfo = UserInfo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
       // getUSerDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.askCard.setOnClickListener{
            Log.d("ask", "Selected")
            useNmeLbl.text="445"
            val fraG = AskQFragment()
            replaceFragments(fraG)
        }
        view.feedsCard.setOnClickListener{
            Log.d("feeds", "Selected")
<<<<<<< HEAD:app/src/main/java/com/example/docapp/fragments/patients/HomeFragment.kt
            useNmeLbl.text="445"
            val fraG = health_feedFragment()
=======
            useNmmeLbl.text="445"
            val fraG = DocListFragment()
>>>>>>> 30442ede5454578165ccb0cb35b9853ee9d7aef9:app/src/main/java/com/example/docapp/fragments/HomeFragment.kt
            replaceFragments(fraG)
        }
        view.consultsCard.setOnClickListener{
            Log.d("consult", "Selected")
            useNmeLbl.text="445"
            val fraG = AskQFragment()
            replaceFragments(fraG)
        }

        view.msgCard.setOnClickListener{
            Log.d("messages", "Selected")
            useNmeLbl.text="445"
            val fraG = AskQFragment()
            replaceFragments(fraG)
        }
        getUSerDetails()
        return  view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
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
            transaction.replace(R.id.fragment_container,fragment)
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
                useNmeLbl.text="Hello  ${user.fullName} welcome"
                ///if activity = feed and role = doc->
                ///else normal news feed

            }}

    override fun onResume() {
        super.onResume()
        //getUSerDetails()
    }

    override fun onStart() {
        super.onStart()
        getUSerDetails()
    }
}