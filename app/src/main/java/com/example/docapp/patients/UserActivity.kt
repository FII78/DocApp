package com.example.docapp.patients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import androidx.fragment.app.Fragment
import com.example.docapp.R
import com.example.docapp.fragments.doctors.DoctorProfileFragment
import com.example.docapp.fragments.patients.AskQFragment
import com.example.docapp.fragments.patients.HomeFragment
import com.example.docapp.fragments.patients.ProfileFragment
import com.example.docapp.fragments.patients.health_feedFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
class UserActivity : AppCompatActivity() {

    private val Home = HomeFragment()
    private val Askq = AskQFragment()
    private  val Profile = ProfileFragment()
    private val Feed = health_feedFragment()
    var db= FirebaseFirestore.getInstance()
    val docP = DoctorProfileFragment()
    lateinit var adapter: RecyclerAdapter
    val  query=db.collection("questions")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)


        replaceFragments(Home)
        btmnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> replaceFragments(Home)
                R.id.miProfile -> replaceFragments(Profile)
                R.id.miQues -> replaceFragments(Askq)
                R.id.miFeed -> replaceFragments(Feed)
                R.id.minotif -> replaceFragments(docP)
            }
            true
        }
    }
    private fun replaceFragments(fragment: Fragment) {
        if(fragment != null ){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}

/*

//////////////////////////////////////////

*/