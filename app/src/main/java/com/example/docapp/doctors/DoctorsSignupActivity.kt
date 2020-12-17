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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class doctorsSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_signup)

        firestore= FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.doc_reg_btn).setOnClickListener {
            val hoursperweek=findViewById<Slider>(R.id.slider_hour).value.toString()
//            val slider: Slider = view.findViewById(android.R.id.slider)
//            slider.addOnSliderTouchListener(touchListener)
            val experience=findViewById<Slider>(R.id.slider_Loe).toString()
            val specialization = findViewById<TextInputLayout>(R.id.Specialization).editText!!.text.toString()
            var sex =""
            val rg = findViewById<RadioGroup>(R.id.sex)

            rg.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    R.id.male ->
                        sex="male"
                    // do operations specific to this selection
                    R.id.female ->
                        sex="female"
                    // do operations specific to this selection

                }
            }
            signup(specialization,hoursperweek,experience,sex)
        }
    }
    private fun signup(specialization:String,hoursperweek:String,experience:String,sex:String) {

                        val intent = intent
                        val extras = intent.extras
                        val username = extras!!.getString("EXTRA_USERNAME").toString()
                        val userid = extras.getString("EXTRA_USER_ID").toString()
                        val fullname = extras.getString("EXTRA_FULL_NAME").toString()
                        val email = extras.getString("EXTRA_EMAIL").toString()
                        val role = extras.getString("EXTRA_ROLE").toString()

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
                                    Toast.makeText(this@doctorsSignupActivity,"Registration successful! Please Login!", Toast.LENGTH_LONG).show()
                                    callLoginSignUp()

                                }
                                .addOnFailureListener{
                                    Toast.makeText(this@doctorsSignupActivity,"Registration failed", Toast.LENGTH_LONG).show()
                                }
                        }

                    private fun callLoginSignUp() {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    }



