package com.app.esports

import java.io.Serializable

data class Game (var title:String, var desc:String, var imageId:Int, var rating:Int)  :
    Serializable