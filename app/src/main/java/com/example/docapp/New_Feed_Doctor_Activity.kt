package com.example.docapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.docapp.models.health_feed
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class New_Feed_Doctor_Activity : AppCompatActivity() {
    lateinit var title:String
    lateinit var description:String
    lateinit var subject:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__feed__doctor_)
        findViewById<ImageView>(R.id.img_bck_new).setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.post_btn).setOnClickListener {
            title = findViewById<TextInputLayout>(R.id.title_new_feed).editText!!.text.toString()
            subject = findViewById<TextInputLayout>(R.id.subject).editText!!.text.toString()
            description = findViewById<TextInputLayout>(R.id.edit_description).editText!!.text.toString()
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


            if(title.trim().isEmpty()||description.trim().isEmpty()||subject.trim().isEmpty())
            {

            }
            val feedRef=FirebaseFirestore.getInstance().collection("health_feeds")
            feedRef.add(health_feed(title,date_posted,description,subject))
            Toast.makeText(this, "You have successfully published, Doc", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)

        }





    }
}