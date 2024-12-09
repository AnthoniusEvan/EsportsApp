package com.app.esports

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val id: Int, var first_name: String, var last_name: String, var username: String, val password: String, val role:String="") :
    Parcelable {
    override fun toString() = username
}
