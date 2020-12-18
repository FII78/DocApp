package com.example.docapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.docapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_message_chat.*
import java.util.*
import kotlin.collections.HashMap

class MessageChatActivity : AppCompatActivity() {
    var userIdVistit:String=""
    var firebaseUser: FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)

        //TODO search fragement
        intent=intent
        userIdVistit=intent.getStringExtra("visit_id")
        firebaseUser=FirebaseAuth.getInstance().currentUser

        val reference=FirebaseDatabase.getInstance().reference.child("Users").child(userIdVistit)
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user: User? =snapshot.getValue(User::class.java)
                username_msg.text=user!!.userName
                //TODO get profie picture
                //Picasso.get().load(user.getProfile()).into(profile_image_msg))
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
        }
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
                    chatsListReference.child("id").setValue(firebaseUser!!.uid)

                    val reference=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
//TODO implement the push notfication

                }
            }
    }
}