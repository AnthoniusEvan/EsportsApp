package com.app.esports.ui.teams

import java.io.Serializable

data class Player (val id:Int, val name:String, val role:String, val team_id:Int)  :
    Serializable