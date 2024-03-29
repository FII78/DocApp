package com.example.docapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.docapp.doctors.doctorsSignupActivity
import com.example.docapp.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*


class signupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rootNode:FirebaseDatabase
    private lateinit var reference:DatabaseReference
    private lateinit var isDoctor: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firestore=FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        rootNode= FirebaseDatabase.getInstance()
        reference=rootNode.getReference("users")

        role.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.patient){
                isDoctor ="patient"
            }
            else if(checkedId == R.id.Doctor){
                isDoctor ="doctor"
            }
        }

        findViewById<Button>(R.id.next_btn).setOnClickListener {
            val email = findViewById<TextInputLayout>(R.id.Email).editText!!.text.toString()
            val password = findViewById<TextInputLayout>(R.id.password).editText!!.text.toString()
            signup(email,password)
        }
        login_btnReg.setOnClickListener{
            callLoginSignUp()
        }
//        login.setOnClickListener(){
//            callLoginFromSignUp()
//        }
    }


//    private fun callLoginFromSignUp() {
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
//    }
private fun signup(email:String,password:String) {
    val fullName = fullNameReg.text.toString().trim()
    val userName = userNameReg.text.toString().trim()
    val Email = emailReg.text.toString().trim()
    val Password = passReg.text.toString().trim()

        if(TextUtils.isEmpty(fullName)){
            fullNameReg.error = "Name is required !!"
        }else if (fullName.length < 3){
            fullNameReg.error = "Name must contain more than 3 characters !!"
        }else if (TextUtils.isEmpty(userName)){
            userNameReg.error = "Username is required !!"
        }else if (userName.length < 3){
            userNameReg.error = "Username must contain more than 3 characters !!"
        }else if(TextUtils.isEmpty(Email)){
            emailReg.error = "Email is required !!"
        }else if(TextUtils.isEmpty(Password)){
            passReg.error = "Name is required !!"
        }else if(Password.length < 8){
            passReg.error = "Password must contain more than 8 characters !!"
        }
        else{

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){

                    val firebaseUser: FirebaseUser =task.result!!.user!!
                   // val isDoctor =findViewById<TextInputLayout>(R.id.purpose).editText!!.text.toString()
                    //    .toLowerCase(Locale.ROOT)
                   // val isDoctor = "doctor"
                    val fullname=findViewById<TextInputLayout>(R.id.fullname).editText!!.text.toString()
                    val username=findViewById<TextInputLayout>(R.id.username).editText!!.text.toString()
//                    var rg = findViewById<RadioGroup>(R.id.role);
//
//                    rg.setOnCheckedChangeListener { group, checkedId ->
//                            when(checkedId){
//                                R.id.patient->
//                                    isDoctor="patient"
//                                // do operations specific to this selection
//                                R.id.Doctor->
//                                    isDoctor="doctor"
//                                // do operations specific to this selection
//
//                            }
//                        }


                    if(isDoctor=="doctor")
                    {
                        val intent = Intent(this, doctorsSignupActivity::class.java)
                        val extras = Bundle()
                        extras.putString("EXTRA_USER_ID", firebaseUser.uid)
                        extras.putString("EXTRA_FULL_NAME", fullname)
                        extras.putString("EXTRA_USERNAME", username)
                        extras.putString("EXTRA_EMAIL", email)
                        extras.putString("EXTRA_ROLE", isDoctor)
                        intent.putExtras(extras)
                        startActivity(intent)

                    }
                    else
                    {
                        //firebase

                        val userF = User(
                            firebaseUser.uid,
                            fullname,
                            username,
                            email,
                            isDoctor
                        )
                        reference.child(firebaseUser.uid).setValue(userF)
                        //firestore
                        val user = User(
                        firebaseUser.uid,
                        fullname,
                        username,
                        email,
                        isDoctor
                    )
                    firestore.collection("users")
                        .document(user.id)
                        .set(user, SetOptions.merge())
                        .addOnSuccessListener {
                            Toast.makeText(this@signupActivity,"Registration successful, Please Login!", Toast.LENGTH_LONG).show()
                            callLoginSignUp()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this@signupActivity,"Registration failed", Toast.LENGTH_LONG).show()
                        }
                    }


                }
                else{
                  //  Toast.makeText(this@signupActivity, "Email $email", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@signupActivity,"Error Message"+task.exception!!.message.toString(),
                        Toast.LENGTH_LONG).show()
                }
            }

    }



}

    private fun callLoginSignUp() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
