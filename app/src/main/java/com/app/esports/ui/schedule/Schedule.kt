package com.app.esports.ui.schedule

import java.io.Serializable

data class Schedule (val id: Int, var date:String, var title:String, var team_id:Int, var image_url:String, var team_name:String)  :
    Serializable