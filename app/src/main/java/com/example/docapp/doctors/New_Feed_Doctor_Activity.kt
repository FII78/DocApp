package com.example.docapp.doctors

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.docapp.R
import com.example.docapp.fragments.patients.dbs
import com.example.docapp.models.User
import com.example.docapp.models.health_feed
import com.example.docapp.patients.UserActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new__feed__doctor_.*
import kotlinx.android.synthetic.main.fragment_ask_q.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class New_Feed_Doctor_Activity : AppCompatActivity() {
    lateinit var title:String
    lateinit var description:String
    lateinit var subject:String
    lateinit var feedBy : String
    var dbs= FirebaseFirestore.getInstance()
    val  query=dbs.collection("questions")
    val users = dbs.collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__feed__doctor_)
        findViewById<ImageView>(R.id.img_bck_new).setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.post_btn).setOnClickListener {
            title = findViewById<TextInputLayout>(R.id.title_new_feed).editText!!.text.toString().trim()
            subject = findViewById<TextInputLayout>(R.id.subject_input).editText!!.text.toString().trim()
            description = findViewById<TextInputLayout>(R.id.edit_description).editText!!.text.toString().trim()
            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()

            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val date_posted = current.format(formatter)

            if(TextUtils.isEmpty(title)){
                title_new_feed.error = "Please insert title for your feed!!"
            }
            else if (title.length < 5) {
                title_new_feed.error = "Title  must contain more than 5 character !!"
            }else if(TextUtils.isEmpty(subject)){
            subject_input.error = "Please insert subject for your feed !!"
            }
            else if (subject.length < 3) {
                title_new_feed.error = "Subject must contain more than 3 character !!"
            }
            else if(TextUtils.isEmpty(description)){
                edit_description.error = "Description for a feed is required!!"
            }
            else if (description.length <10 ) {
                edit_description.error = "Description  must contain more than 10 character !!"
            }
            else {
                val model = health_feed(title,date_posted,description,subject,feedBy)
                val feedRef=FirebaseFirestore.getInstance().collection("health_feeds")
                feedRef
                    .add(model)
                    .addOnSuccessListener { documentReference ->
                        Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                        //title_new_feed.text.clear()
                       // getTagUser.text.clear()
                        Toast.makeText(this, "You have successfully published", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, DoctorActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)
                        Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
                    }
            }

            }

            getUSerDetails()

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
                feedBy=user.userName.capitalize()
                ///if activity = feed and role = doc->
                ///else normal news feed

            }}




}