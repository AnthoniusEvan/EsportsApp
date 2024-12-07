package com.app.esports.ui.proposal

import java.io.Serializable

data class proposal (val id: Int, var game_id:String, val game_name: String, var user_id:String, var status:String, var description: String, var created_at: String, var name: String  )  :
    Serializable