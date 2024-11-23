package com.app.esports

import java.io.Serializable

data class Game (val id:Int, val name:String, val description:String, val image_url:String)  :
    Serializable