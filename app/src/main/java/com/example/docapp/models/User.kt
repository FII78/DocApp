package com.example.docapp.models

open class User(
    val id: String="",
    val fullName: String="",
    val userName: String="",
    val email: String="",
    val role:String="",

    val sex: String="",
    //doctor-specific
    val experience:String="0",
    val specialization:String="",
    val hoursAvailablePerWeek:String="1",
    ///////////////////////
    val mobile: String="",
    val BloodType: String="",
    val age : String = "",
    val height : String ="",
    val weight : String = ""
    //patient-specific


)