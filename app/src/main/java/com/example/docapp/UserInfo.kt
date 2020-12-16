package com.example.docapp

import android.app.Activity
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserInfo {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    fun getCurrentUserId():String{
        val currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }


    //placeholder

    fun getUserDetailsfromlogin() {
        val currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
    }

    fun getUserDetails(activity: Activity) {

        firestore.collection("users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
                val  user=document.toObject(User::class.java)!!
                ///if activity = feed and role = doc->
                ///else normal news feed

                when(activity){
                    is LoginActivity->{
                        activity.getUserDetailsfromlogin()
                    }
                }


            }
    }
}