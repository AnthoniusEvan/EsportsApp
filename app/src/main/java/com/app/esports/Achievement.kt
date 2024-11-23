package com.app.esports

import java.io.Serializable

data class Achievement (val id:Int, val achievement:String, val game_id:Int, val year:String)  :
    Serializable