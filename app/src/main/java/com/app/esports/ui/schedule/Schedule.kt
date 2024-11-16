package com.app.esports.ui.schedule

import java.io.Serializable

data class Schedule (val id: Int, var date:String, var title:String, var team:String, var image_url:Int,)  :
    Serializable