package com.app.esports

import com.app.esports.ui.schedule.Schedule
import com.app.esports.ui.teams.Player

object StaticData {
//    var schedules: Array<Schedule> = arrayOf(
//        Schedule(1,"20 SEP 2024", "Regional Qualifier - Valorant", "Void Runners", R.drawable.poster_schedule_1),
//        Schedule(2,"25 SEP 2024", "Elimination Round - CS:GO", "Nova Strike", R.drawable.poster_schedule_2),
//        Schedule(3,"9 OCT 2024", "Semifinal - Mobile Legends", "Phantom Core", R.drawable.poster_schedule_3),
//        Schedule(4,"16 OCT 2024", "National Qualifier - PUBG", "Solar Surge", R.drawable.poster_schedule_4),
//        Schedule(5,"25 SEP 2024", "International Qualifier - LOL", "Shadow Wraiths", R.drawable.poster_schedule_5)
//    )

//    var games: Array<Game> = arrayOf(
//        Game(1,"Valorant", "Valorant is an online multiplayer computer game, produced by Riot Games. It is a first-person shooter game, consisting of two teams of five, where one team attacks and the other defends.", R.drawable.valorant),
//        Game(2,"Mobile Legends", "Mobile Legends: Bang Bang is a fast-paced multiplayer online battle arena (MOBA) game where players team up in 5v5 matches to destroy the enemy base while defending their own. Featuring a wide range of heroes with unique abilities, Mobile Legends focuses on strategic teamwork, quick reflexes, and tactical play in a competitive environment.", R.drawable.mobile_legends),
//        Game(3,"CS:GO", "Counter-Strike: Global Offensive (CS:GO) is a tactical first-person shooter that pits teams of terrorists against counter-terrorists in objective-based game modes. With intense firefights, precision gunplay, and team strategy, CS:GO has become one of the most iconic esports titles, known for its high skill ceiling and competitive nature.", R.drawable.csgo),
//        Game(4,"PUBG","Player Unknown’s Battlegrounds (PUBG) is a battle royale game where up to 100 players parachute onto an island and scavenge for weapons and supplies to survive. The goal is to be the last person or team standing as the play area shrinks, forcing encounters and strategic combat. Known for its intense, large-scale firefights, PUBG revolutionized the battle royale genre.", R.drawable.pubg),
//    )

    var achievements = mapOf(
        "Valorant" to mapOf(
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
        ),
        "Mobile Legends" to mapOf(
            "2018" to arrayOf("Champions of the Spring MPL (Mobile Legends Professional League)",
                "Secured 2nd place at the Summer Invitational Cup",
                "Top 4 finish in the Global Championship Series",
                "Qualified for the Winter International Tournament",
                "Reached semifinals in the Mobile Legends World Cup",),
            "2019" to arrayOf("Winners of the Spring Regional MPL Finals",
                "Secured 2nd place at the International Esports Cup",
                "Champions of the Summer Showdown Invitational",
                "Reached semifinals of the Fall Pro Series",
                "Top 4 finish in the Winter Esports Masters"),
            "2020" to arrayOf("Victorious at the Spring MPL Championship",
                "Runner-up at the Global Masters Invitational",
                "Champions of the Summer Regional Tournament",
                "Secured 3rd place at the Fall Esports League",
                "Reached semifinals of the Winter Global Finals"),
            "2021" to arrayOf("Won the Spring Professional League Championship",
                "Secured 2nd place at the Summer International Cup",
                "Champions of the Fall Invitational Tournament",
                "Reached semifinals in the Global Elite Series",
                "Top 4 finish at the Winter Pro Circuit"),
            "2022" to arrayOf("Victorious at the Spring MPL Finals",
                "Top 3 finish at the Summer Invitational Cup",
                "Reached quarterfinals of the Global Pro Series",
                "Champions of the Fall Regional Showdown",
                "Qualified for the Winter Mobile Legends Masters"),
            "2023" to arrayOf("Champions of the Spring Pro League",
                "Secured 2nd place at the Summer International Cup",
                "Reached semifinals at the Fall Invitational Tournament",
                "Champions of the Winter Global Masters",
                "Top 4 finish in the International Championship Series"),
            "2024" to arrayOf("Winners of the Spring MPL Invitational",
                "Secured 2nd place at the Summer Regional Finals",
                "Champions of the Fall International Tournament",
                "Reached quarterfinals of the Global Elite Series",
                "Top 4 finish in the Winter World Cup Finals")
        ),
        "CS:GO" to mapOf(
            "2018" to arrayOf("Winners of the Spring Major Championship",
                "Secured 2nd place at the Summer Invitational",
                "Champions of the Fall Global Showdown",
                "Reached semifinals in the Winter Pro League",
                "Qualified for the Global Masters Series Finals"),
            "2019" to arrayOf("Victorious at the Spring Regional Finals",
                "Runner-up at the Global Esports Invitational",
                "Champions of the Summer Masters Tournament",
                "Top 4 finish in the Fall Pro Series",
                "Reached quarterfinals of the Winter International Championship"),
            "2020" to arrayOf("Winners of the Spring Major Championship",
                "Champions of the Summer Esports League",
                "Reached semifinals of the Fall Invitational Cup",
                "Secured 3rd place in the Global Pro League",
                "Top 4 finish at the Winter Championship Finals"),
            "2021" to arrayOf("Victorious at the Spring Global Championship",
                "Champions of the Summer International Cup",
                "Reached semifinals in the Fall Major Tournament",
                "Runner-up at the Winter Elite Series",
                "Qualified for the Global Pro Series Finals"),
            "2022" to arrayOf("Winners of the Spring Major Tournament",
                "Secured 2nd place at the Summer Invitational",
                "Top 4 finish in the Fall Esports Pro League",
                "Reached semifinals of the Global Masters",
                "Champions of the Winter Showdown"),
            "2023" to arrayOf("Champions of the Spring Invitational Cup",
                "Secured 2nd place at the Summer Major Championship",
                "Reached semifinals in the Fall Global Series",
                "Top 4 finish in the Winter Pro League",
                "Qualified for the Global Elite Finals"),
            "2024" to arrayOf("Victorious at the Spring Major Championship",
                "Secured 3rd place in the Summer Invitational Tournament",
                "Champions of the Fall Global Masters",
                "Reached quarterfinals in the Winter Pro League",
                "Top 4 finish in the International Championship")
        ),
        "PUBG" to mapOf(
            "2018" to arrayOf("Winners of the Spring Global Championship",
                "Secured 2nd place at the Summer Invitational Cup",
                "Top 4 finish at the Fall Pro Series",
                "Reached semifinals in the Winter Elite Showdown",
                "Champions of the International Masters Series"),
            "2019" to arrayOf("Victorious at the Spring Regional Finals",
                "Secured 3rd place at the Summer Global Invitational",
                "Reached semifinals in the Fall Pro League",
                "Champions of the Winter International Tournament",
                "Top 4 finish at the Global Masters Finals"),
            "2020" to arrayOf("Champions of the Spring Global Showdown",
                "Secured 2nd place in the Summer Invitational Tournament",
                "Top 4 finish at the Fall Pro Series",
                "Reached semifinals in the Winter Elite Championship",
                "Qualified for the Global Masters"),
            "2021" to arrayOf("Victorious at the Spring International Cup",
                "Secured 2nd place in the Summer Invitational",
                "Champions of the Fall Pro League",
                "Top 4 finish in the Winter Global Series",
                "Reached quarterfinals in the International Masters"),
            "2022" to arrayOf("Winners of the Spring Global Invitational",
                "Secured 3rd place in the Summer Pro League",
                "Reached semifinals in the Fall Championship",
                "Champions of the Winter Elite Series",
                "Top 4 finish in the International Masters"),
            "2023" to arrayOf("Champions of the Spring Invitational Cup",
                "Secured 2nd place at the Summer Pro League",
                "Reached semifinals at the Fall Global Masters",
                "Top 4 finish in the Winter Championship Finals",
                "Qualified for the International Esports Tournament"),
            "2024" to arrayOf("Winners of the Spring Global Pro League",
                "Secured 2nd place in the Summer Invitational",
                "Reached semifinals in the Fall Major Tournament",
                "Champions of the Winter Elite Invitational",
                "Top 4 finish in the International Masters Series",)
        )
    )

//    var teams = mapOf(
//        "Valorant" to mapOf(
//            "Sentinels" to arrayOf(Player("TenZ", "Duelist"), Player("ShahZaM", "In-Game Leader"), Player("SicK", "Flex"), Player("dapr", "Sentinel"), Player("zombs", "Controller")),
//            "Team Liquid" to arrayOf(Player("ScreaM", "Duelist"), Player("Jamppi", "Operator"), Player("L1NK", "Controller"), Player("soulcas", "Flex"), Player("Kryptix", "Sentinel")),
//            "Fnatic" to arrayOf(Player("Boaster", "In-Game Leader"), Player("Derke", "Duelist"), Player("Doma", "Flex"), Player("Mistic", "Controller"), Player("Magnum", "Sentinel")),
//            "100 Thieves" to arrayOf(Player("Asuna", "Duelist"), Player("Hiko", "Sentinel"), Player("Ethan", "Flex"), Player("nitr0", "In-Game Leader"), Player("Steel", "Controller")),
//            "Vision Strikers" to arrayOf(Player("BuZz", "Duelist"), Player("stax", "In-Game Leader"), Player("Rb", "Flex"), Player("MaKo", "Controller"), Player("Zest", "Sentinel"))
//        ),
//        "Mobile Legends" to mapOf(
//            "Blacklist International" to arrayOf(Player("OhMyV33nus", "Support"), Player("Wise", "Jungler"), Player("Edward", "Offlane"), Player("OHEB", "Gold Lane"), Player("Hadji", "Mid Lane")),
//            "RRQ Hoshi" to arrayOf(Player("Alberttt", "Jungler"), Player("Lemon", "Offlane"), Player("Vyn", "Tank"), Player("R7", "Offlane"), Player("Skylar", "Gold Lane")),
//            "ONIC Esports" to arrayOf(Player("Drian", "Mid Lane"), Player("Butsss", "Offlane"), Player("Sanz", "Jungler"), Player("Kiboy", "Support"), Player("CW", "Gold Lane")),
//            "Evos Legends" to arrayOf(Player("Antimage", "Offlane"), Player("Ferxiic", "Jungler"), Player("Clover", "Gold Lane"), Player("Rexxy", "Mid Lane"), Player("Luminaire", "Support")),
//            "Bren Esports" to arrayOf(Player("KarlTzy", "Jungler"), Player("Flaptzy", "Offlane"), Player("Ribo", "Gold Lane"), Player("Lusty", "Tank"), Player("Pheww", "Support"))
//        ),
//        "CS:GO" to mapOf(
//            "NAVI" to arrayOf(Player("s1mple", "AWPer"), Player("electroNic", "Rifler"), Player("Boombl4", "In-Game Leader"), Player("Perfecto", "Support"), Player("B1T", "Rifler")),
//            "FaZe Clan" to arrayOf(Player("rain", "Rifler"), Player("karrigan", "In-Game Leader"), Player("broky", "AWPer"), Player("Twistzz", "Rifler"), Player("ropz", "Lurker")),
//            "Astralis" to arrayOf(Player("gla1ve", "In-Game Leader"), Player("Xyp9x", "Support"), Player("blameF", "Rifler"), Player("dev1ce", "AWPer"), Player("Buzz", "Rifler")),
//            "Vitality" to arrayOf(Player("ZywOo", "AWPer"), Player("apEX", "In-Game Leader"), Player("dupreeh", "Rifler"), Player("Magisk", "Rifler"), Player("Spinx", "Rifler")),
//            "Cloud9" to arrayOf(Player("HObbit", "Lurker"), Player("Ax1Le", "Rifler"), Player("sh1ro", "AWPer"), Player("nafany", "In-Game Leader"), Player("buster", "Rifler"))
//        ),
//        "PUBG" to mapOf(
//            "Team Liquid" to arrayOf(Player("Jeemzz", "Fragger"), Player("Ibiza", "Support"), Player("Sambty", "Scout"), Player("Clib", "In-Game Leader")),
//            "FaZe Clan" to arrayOf(Player("Fuzzface", "In-Game Leader"), Player("Aitzy", "Scout"), Player("Gustav", "Fragger"), Player("UBAH", "Support")),
//            "TSM" to arrayOf(Player("Vard", "Scout"), Player("MiracU", "Support"), Player("Fexx", "Fragger"), Player("iBiza", "In-Game Leader")),
//            "NAVI" to arrayOf(Player("Kemba", "Fragger"), Player("Mellman", "In-Game Leader"), Player("ALYO", "Support"), Player("xmpl", "Scout")),
//            "Gen.G" to arrayOf(Player("Pio", "Fragger"), Player("Inonix", "Support"), Player("Esther", "In-Game Leader"), Player("Asura", "Scout"))
//        ),
//    )
}