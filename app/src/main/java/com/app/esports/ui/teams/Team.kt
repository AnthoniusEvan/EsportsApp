package com.app.esports.ui.teams

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class Team (val id:Int, val name:String, val game_id:Int)  :
    Parcelable