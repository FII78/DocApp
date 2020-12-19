package com.example.docapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class IntroActivity : AppCompatActivity() {
var firebaseUser:FirebaseUser?=null
    lateinit var logo:ImageView
   // lateinit var appName:ImageView
    lateinit var splashImg:ImageView
    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        this.logo =findViewById(R.id.logo)

        splashImg=findViewById(R.id.splashImg)

        topAnim=AnimationUtils.loadAnimation(this,
            R.anim.top_animation
        )
        bottomAnim=AnimationUtils.loadAnimation(this,
            R.anim.bottom_animation
        )

        logo.animation = topAnim
    //    appName.animation = topAnim
        splashImg.animation = bottomAnim

        //  animate()
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)



    }

//    override fun onStart() {
//        super.onStart()
//        firebaseUser=FirebaseAuth.getInstance().currentUser
//        if(firebaseUser!=null)
//        {
//            val  intent=Intent(this@IntroActivity,IntroActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

//    private fun animate(){
//        splashImg.animate().translationY(-1600F).setDuration(1000).startDelay = 4000
//        logo.animate().translationY(1400F).setDuration(1000).startDelay = 4000
//        appName.animate().translationY(1400F).setDuration(1000).startDelay = 4000
//    }





}