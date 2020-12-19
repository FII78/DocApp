package com.example.docapp.doctors

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.docapp.LoginActivity
import com.example.docapp.R
import com.example.docapp.models.User
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_doctors_signup.*

class doctorsSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rootNode:FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_signup)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        rootNode= FirebaseDatabase.getInstance()
        reference=rootNode.getReference("users")

        login_btn_docS.setOnClickListener{
            callLoginSignUp()
        }

        findViewById<Button>(R.id.doc_reg_btn).setOnClickListener {


                var specialization = findViewById<TextInputLayout>(R.id.Specialization).editText!!.text.toString()
                var hoursperweek = findViewById<TextInputLayout>(R.id.slider_hour).editText!!.text.toString()

                var experience = findViewById<TextInputLayout>(R.id.slider_Loe).editText!!.text.toString()
                var sex=""
//            lateinit var sex:String
//
//            var rg = findViewById<RadioGroup>(R.id.sex)
//            rg.setOnCheckedChangeListener { group, checkedId ->
//
//                when (checkedId) {
//                    R.id.male ->
//                        sex = "male"
//                    R.id.female ->
//                        sex = "female"
//
//                }
//            }
                signup(specialization, hoursperweek, experience, sex)


        }}
        private fun signup(specialization: String, hoursperweek: String, experience: String, sex: String) {

            val intent = intent
            val extras = intent.extras
            val username = extras!!.getString("EXTRA_USERNAME").toString()
            val userid = extras.getString("EXTRA_USER_ID").toString()
            val fullname = extras.getString("EXTRA_FULL_NAME").toString()
            val email = extras.getString("EXTRA_EMAIL").toString()
            val role = extras.getString("EXTRA_ROLE").toString()
           //firebase

            val userF = User(
                userid,
                fullname,
                username,
                email,
                role,
                sex,
                experience,
                specialization,
                hoursperweek

            )
            reference.child(userid).setValue(userF)
            //
            val user = User(
                    userid,
                    fullname,
                    username,
                    email,
                    role,
                    sex,
                    experience,
                    specialization,
                    hoursperweek
            )
            firestore.collection("users")
                    .document(user.id)
                    .set(user, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this@doctorsSignupActivity, "Registration successful! Please Login!", Toast.LENGTH_LONG).show()
                        callLoginSignUp()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this@doctorsSignupActivity, "Registration failed", Toast.LENGTH_LONG).show()
                    }
        }

        private fun callLoginSignUp() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



