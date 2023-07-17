package com.vandrushko.data.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("contacts") var contacts: ArrayList<Contact> = arrayListOf()

)
