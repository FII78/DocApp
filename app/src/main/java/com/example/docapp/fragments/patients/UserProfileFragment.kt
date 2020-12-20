package com.example.docapp.fragments.patients

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.docapp.LoginActivity
import com.example.docapp.R
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View= inflater.inflate(R.layout.fragment_profile, container, false)
        getUSerDetails()
        view.btnOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            startActivity(Intent(activity,LoginActivity::class.java))
        }
//        view.editProfile.setOnClickListener{
//                openEditFragment()
//        }
//        view.editMob.setOnClickListener{
//            openMobFragment()
//        }
        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
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
                userFullName.text=user.fullName.capitalize()
                userEmail.text= user.email
                userBloodT.text=user.BloodType
                userAge.text = user.age
                userHeight.text = user.height
                userWeight.text = user.weight
                userPName.text = "Username: ${ user.userName.capitalize() }"
                userSex.text = user.sex
                userMob.text = user.mobile


            }}
    private  fun updateProfile(){

    }
    private fun editMob(){
        dbs.collection("users")
            .document(getCurrentUserId())
            .update("mobile",userMob)
    }


    private fun openEditFragment(){
        val fragment = EditProfileFragment()
        var  fragmentM = (activity as FragmentActivity).supportFragmentManager
        val transaction = fragmentM.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun openMobFragment(){
        val fragment = EditMobFragment()
        var  fragmentM = (activity as FragmentActivity).supportFragmentManager
        val transaction = fragmentM.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}