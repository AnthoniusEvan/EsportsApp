package com.app.esports.ui.schedule

import java.io.Serializable

data class Schedule (var date:String, var title:String, var team:String, var imageId:Int)  :
    Serializable