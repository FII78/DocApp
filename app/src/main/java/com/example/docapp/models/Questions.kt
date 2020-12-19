package com.example.docapp.models

import com.google.firebase.firestore.DocumentId

open class Questions (
        @DocumentId
        val documentId: String="",
        val description : String ="",
        val asked_at : String = "",
        val tag : String="",
        var answers: ArrayList<AnsList> = arrayListOf<AnsList>()
       // val answers : Array<String>

)