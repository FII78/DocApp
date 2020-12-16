package com.example.docapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import androidx.fragment.app.Fragment
import com.example.docapp.fragments.AskQFragment
import com.example.docapp.fragments.HomeFragment
import com.example.docapp.fragments.ProfileFragment
import com.example.lastone.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    private val Home = HomeFragment()
    private val Askq = AskQFragment()
    private  val Profile = ProfileFragment()
    var db= FirebaseFirestore.getInstance()
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