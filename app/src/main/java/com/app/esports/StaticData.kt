package com.app.esports

import com.app.esports.ui.schedule.Schedule

object StaticData {
    var schedules: Array<Schedule> = arrayOf(
        Schedule("20 SEP", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_1),
        Schedule("25 SEP", "Elimination Round - CS:GO", "Nova Strike", R.drawable.poster_schedule_2),
        Schedule("9 OCT", "Semifinal - Mobile Legends", "Phantom Core", R.drawable.poster_schedule_3),
        Schedule("16 OCT", "National Qualifier - PUBG", "Solar Surge", R.drawable.poster_schedule_4),
        Schedule("25 SEP", "International Qualifier - LOL", "Shadow Wraiths", R.drawable.poster_schedule_5)
    )
    var isLiked:Boolean = false
    var numOfLikes:Int = 0

    var achievements = mapOf(
        "2018" to arrayOf("Won Spring Invitational Championship",
            "Secured 2nd place at Summer Regional Tournament",
            "Reached semifinals of the Global Elite Cup",
            "Achieved top 4 finish in the Winter League",
            "Qualified for International Masters Series"),
        "2019" to arrayOf("Claimed victory at the Spring Regional Championship",
            "Earned 3rd place at the International Masters Series",
            "Won Summer Showdown Invitational",
            "Reached quarterfinals of the Fall Pro League",
            "Runner-up at the Winter Clash"),
        "2020" to arrayOf("Won the Spring Elite Series",
            "Secured 2nd place at the Global Invitational Cup",
            "Reached semifinals in Summer Regional Tournament",
            "Claimed 1st place at the Fall Esports Festival",
            "Qualified for the Winter Global Series Finals"),
        "2021" to arrayOf("Won Spring Pro Series Championship",
            "Reached 2nd place in the Summer International Open",
            "Semifinalists at the Global Masters Tournament",
            "Champions of Fall Invitational Showdown",
            "Top 4 finish in the Winter Pro Circuit"),
        "2022" to arrayOf("Victorious at Spring Championship Finals",
            "Secured 3rd place at Summer Regional Cup",
            "Reached semifinals at the Global Esports Masters",
            "Won Fall International Tournament",
            "Qualified for Winter Elite Series Playoffs",
            "Finished as runner-up in the Esports Winter Invitational"),
        "2023" to arrayOf("Won Spring Esports Pro Tournament",
            "Secured 2nd place at Summer Global Championship",
            "Champions of the Fall Regional Invitational",
            "Top 4 finish at the International Masters Finals",
            "Qualified for Winter Showdown"),
        "2024" to arrayOf("Champions of the Spring Elite Invitational",
            "Secured 2nd place in Summer Esports Open",
            "Won Fall Regional Championship",
            "Reached semifinals of the Global Masters Series",
            "Qualified for Winter International Cup Finals")
    )
}