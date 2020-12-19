package com.example.docapp.fragments.patients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.docapp.R
import com.example.docapp.fragments.doctors.DoctorProfileFragment
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_edit_mob.*
import kotlinx.android.synthetic.main.fragment_edit_mob.view.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dbs= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

       val view : View= inflater.inflate(R.layout.fragment_edit_profile, container, false)
        setValues()
        view.editMobBtnDoc.setOnClickListener{
            update()
        }
        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
   private fun getCurrentUserId():String{
        val currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }
    private fun setValues () {
        dbs.collection("users")
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
                val  user=document.toObject(User::class.java)!!

                        mobValFieldDoc.setText(user.mobile, TextView.BufferType.EDITABLE);

            }}
    private fun update(){
        val value = mobValField.text.toString().trim()
        dbs.collection("users")
            .document(getCurrentUserId())
            .update("mobile",value)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!")
                Toast.makeText(activity,"Updated", Toast.LENGTH_LONG).show()

                val fragment = DoctorProfileFragment()
                var  fragmentM = (activity as FragmentActivity).supportFragmentManager
                val transaction = fragmentM.beginTransaction()
                transaction.replace(R.id.fragment_container_doc,fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

    }
}
