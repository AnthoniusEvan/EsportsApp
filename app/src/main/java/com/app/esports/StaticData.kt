package com.app.esports

import com.app.esports.ui.schedule.Schedule

object StaticData {
    var schedules: Array<Schedule> = arrayOf(
        Schedule("20 SEP", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_1),
        Schedule("25 SEP", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_2),
        Schedule("9 OCT", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_3),
        Schedule("16 OCT", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_4),
        Schedule("25 SEP", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_5)
    )
    var isLiked:Boolean = false
    var numOfLikes:Int = 0
}