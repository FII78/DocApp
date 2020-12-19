package com.example.docapp.doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.docapp.R
import com.example.docapp.fragments.SearchFragment
import com.example.docapp.fragments.doctors.DocHomeFragment
import com.example.docapp.fragments.doctors.DocQnAFragment
import com.example.docapp.fragments.doctors.DoctorProfileFragment
import com.example.docapp.fragments.doctors.health_feedFragment
import com.example.docapp.fragments.patients.DocListFragment
import kotlinx.android.synthetic.main.activity_doctor.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btmnav

class DoctorActivity : AppCompatActivity() {
    private val Home = DocHomeFragment()
    private val QnA = DocQnAFragment()
    private val Profile = DoctorProfileFragment()
    private val Feed = health_feedFragment()
    private val docList = DocListFragment()
    private val search = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        replaceFragments(Home)
        btmNavDoc.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> replaceFragments(Home)
                R.id.miQues -> replaceFragments(QnA)
                R.id.miProfile -> replaceFragments(Profile)
                R.id.miFeed -> replaceFragments(Feed)
                R.id.minotif -> replaceFragments(search)
            }
            true
        }
    }
    private fun replaceFragments(fragment: Fragment) {
        if(fragment != null ){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_doc,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}