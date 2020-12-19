@file:Suppress("DEPRECATION")

package com.example.docapp

import android.os.Bundle
import android.widget.TableLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.docapp.fragments.ChatsFragment
import com.example.docapp.fragments.SearchFragment
import com.example.docapp.fragments.SettingFragment
import com.example.docapp.fragments.patient_health_feedFragment
import com.example.docapp.fragments.patients.AskQFragment
import com.example.docapp.fragments.patients.HomeFragment
import com.example.docapp.fragments.patients.ProfileFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.btmnav
import kotlinx.android.synthetic.main.activity_main.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(findViewById(R.id.toolbar_chat))

        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar_chat)
        setSupportActionBar(toolbar)
        supportActionBar!!.title=""

        val tabLayout: TabLayout =findViewById(R.id.tab_layout_chat)
        val viewPager: ViewPager =findViewById(R.id.view_pager_chat)
        val  viewPagerAdapter=viewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatsFragment(),"Chats")
        viewPagerAdapter.addFragment(SearchFragment(),"Search")
        viewPagerAdapter.addFragment(SettingFragment(),"Setting")
        viewPager.adapter=viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)


    }
    internal class viewPagerAdapter(fragmentManager:FragmentManager):
            FragmentPagerAdapter(fragmentManager)
    {
        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return  fragments[position]
        }

        override fun getCount(): Int {
           return fragments.size
        }
        fun addFragment(fragment:Fragment,title:String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }





    }
}