@file:Suppress("DEPRECATION")

package com.example.docapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docapp.adapters.ChatsAdapter
import com.example.docapp.models.User
import com.example.docapp.models.chat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_message_chat.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MessageChatActivity : AppCompatActivity() {
    var userIdVistit:String=""
    var firebaseUser: FirebaseUser?=null
var chatsAdapter:ChatsAdapter?=null
    var mChatList:List<chat>?=null
lateinit var recycler_view_msg:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)
        var name =  findViewById<TextView>(R.id.username_msg)
        name.setText("init")

        intent=intent
        userIdVistit=intent.getStringExtra("visit_id")
        Toast.makeText(this ,userIdVistit.toString(),
                Toast.LENGTH_LONG).show()
        firebaseUser=FirebaseAuth.getInstance().currentUser

        recycler_view_msg=findViewById(R.id.recycler_view_msg)
        recycler_view_msg.setHasFixedSize(true)
        var linearLayoutManager=LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd=true
        recycler_view_msg.layoutManager=linearLayoutManager
        val reference=FirebaseDatabase.getInstance().reference.child("User").child(userIdVistit)
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user: User? = snapshot.getValue(User::class.java)
                name.text= user?.fullName ?: "fii"
                //TODO get profie picture
                //Picasso.get().load(user.getProfile()).into(profile_image_msg))

                retrieveMessages(firebaseUser!!.uid,userIdVistit)

            }
        })



        send_image_file_btn.setOnClickListener{
            val message=text_message.text.toString()
            if(message=="")
            {
            //TODO toast a message
            }
            else{
                sendMessageUser(firebaseUser!!.uid,userIdVistit,message)
            }
            text_message.setText("")
        }

//        attact_image_file_btn.setOnClickListener{
//          val intent=Intent()
//            intent.action=Intent.ACTION_GET_CONTENT
//            intent.type="image/*"
//            startActivityForResult(Intent.createChooser(intent,"PickImage"),438)
//
//        }







    }

    private fun retrieveMessages(senderId: String, recieverId: String?) {
mChatList=ArrayList()
        val reference=FirebaseDatabase.getInstance().reference.child("Chats")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                (mChatList as ArrayList<chat>).clear()
                for (snapshot in snapshot.children){
                    val chat=snapshot.getValue(chat::class.java)
                    if(chat!!.reciever.equals(senderId)&&chat.sender.equals(recieverId) || chat.reciever.equals(recieverId)&&chat.sender.equals(senderId))
                    {
                        (mChatList as ArrayList<chat>).add(chat)

                    }
                    chatsAdapter= ChatsAdapter(this@MessageChatActivity,(mChatList as ArrayList<chat>))
                    recycler_view_msg.adapter=chatsAdapter
                }
            }

        })
    }

    private fun sendMessageUser(senderId:String,recieverId:String?,message:String){
        val reference=FirebaseDatabase.getInstance().reference
        val messageKey=reference.push().key

        val messageHashMap=HashMap<String,Any?>()
        messageHashMap["sender"]=senderId
        messageHashMap["message"]=message
        messageHashMap["reciever"]=recieverId
        messageHashMap["isseen"]=false
        messageHashMap["url"]=""
        messageHashMap["messageId"]=messageKey
        reference.child("Chats")
            .child(messageKey!!)
            .setValue(messageHashMap)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    val chatsListReference=FirebaseDatabase.getInstance()
                        .reference
                        .child("ChatList")
                            .child(firebaseUser!!.uid)
                            .child(userIdVistit)
                    chatsListReference.child("id").setValue(firebaseUser!!.uid)

                    chatsListReference.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                      if(!snapshot.exists())
                      {
                          chatsListReference.child("id").setValue(userIdVistit)
                      }
                            val chatsListrecieverReference=FirebaseDatabase.getInstance()
                                    .reference
                                    .child("ChatList")
                                    .child(userIdVistit)
                                    .child(firebaseUser!!.uid)
                            chatsListrecieverReference.child("id").setValue(firebaseUser!!.uid)

                        }
                    })


                    val reference=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                //TODO implement the push notfication

                }
            }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==438&&resultCode==Activity.RESULT_OK&&data!!.data!=null)
//        {
//            val loadingBar= ProgressDialog(applicationContext)
//            loadingBar.setMessage("Please Wait")
//            loadingBar.show()
//
//
//        }
//    }
}