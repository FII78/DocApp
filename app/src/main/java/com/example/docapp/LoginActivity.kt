package com.example.docapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.docapp.doctors.DoctorActivity
import com.example.docapp.fragments.patients.dbs
//import com.example.docapp.fragments.dbs
import com.example.docapp.models.User
import com.example.docapp.patients.UserActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var login:Button
    private lateinit var username:String
    private lateinit var password:String
    private lateinit var signup:Button
   // private var userLog = userLog.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)
        firestore= FirebaseFirestore.getInstance()
        this.auth = FirebaseAuth.getInstance()
        login=findViewById(R.id.login_btn)
        signup=findViewById(R.id.signup_btn_login)
        signup.setOnClickListener {
            callSignUpFromSignUp()
        }

        login.setOnClickListener{

            username= findViewById<TextInputLayout>(R.id.username).editText!!.text.toString()
            password= findViewById<TextInputLayout>(R.id.password).editText!!.text.toString()

            if(username.trim().isNotEmpty() || password.trim().isNotEmpty())
            {

                signInUser(username.trim(),password.trim())

            }
            else
            {
                Toast.makeText(this, "Username or Password can't be null", Toast.LENGTH_SHORT).show()
            }



        }

    }
    private fun signInUser(email:String, password:String)
    {
        val Email = userLog.text.toString().trim()
        val Password = passLog.text.toString().trim()

        if(TextUtils.isEmpty(Email)){
            userLog.error = "Email is required !!"
        }else if (TextUtils.isEmpty(Password)){
            passLog.error = "Password is required !!"
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            redirectUSer()}
                         else {
                            Toast.makeText(this, "Error Message" + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()

                    }}
        }
    }
    private fun redirectUSer () {
        // var user:User
        dbs.collection("users")
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    val user=document.toObject(User::class.java)!!
                    if(user.role == "doctor"){
                        val intent=Intent(this, DoctorActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent=Intent(this, UserActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

    }

    private fun callSignUpFromSignUp() {
        val intent = Intent(this, signupActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun getCurrentUserId():String{
        val currentUser=FirebaseAuth.getInstance().currentUser
         var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }


    //placeholder
    fun getUserDetailsfromlogin() {
        val currentUser=FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
    }

    fun getUserDetails(activity:Activity) {

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