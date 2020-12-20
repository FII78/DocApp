package com.example.docapp.fragments.doctors

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.docapp.LoginActivity
import com.example.docapp.R
import com.example.docapp.fragments.patients.EditMobFragment
import com.example.docapp.fragments.patients.EditProfileFragment
import com.example.docapp.fragments.patients.dbs
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_doctor_profile.*
import kotlinx.android.synthetic.main.fragment_doctor_profile.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DoctorProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val view:View =inflater.inflate(R.layout.fragment_doctor_profile, container, false)
        view.docbtnOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            startActivity(Intent(activity, LoginActivity::class.java))
        }
//        view.editMobDoc.setOnClickListener{
//            openMobFragment()
//        }
        getUSerDetails()
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DoctorProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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
                type.text = user.specialization
                docMob.text = user.mobile
                docEmail.text = user.email
                docExp.text = "${user.experience} + years"
                docAvlHVal.text = user.hoursAvailablePerWeek




            }}
    private fun openMobFragment(){
        val fragment = EditProfileFragment()
        var  fragmentM = (activity as FragmentActivity).supportFragmentManager
        val transaction = fragmentM.beginTransaction()
        transaction.replace(R.id.fragment_container_doc,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}