package com.app.esports

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val id: Int, var username: String, var email: String, var password: String) :
    Parcelable {
    override fun toString() = username
}
