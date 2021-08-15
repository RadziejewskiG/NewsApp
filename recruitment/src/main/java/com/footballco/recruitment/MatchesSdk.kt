package com.footballco.recruitment

import android.app.Activity
import kotlin.random.Random

/* This file shouldn't be edited - it's an attachment to the task */

data class MatchSchema(
    val id: Int,
    val teamAName: String,
    val scoreA: Int,
    val teamBName: String,
    val scoreB: Int,
)

/* "Blackpool," was spelled incorrectly so I've fixed it("Blackpool") */
private val teamCombinations = listOf(
    "Arsenal" to "Aston Villa",
    "Barnsley" to "Birmingham City",
    "Blackburn Rovers" to "Blackpool",
    "Bolton Wanderers" to "Brentford",
    "Chelsea" to "Crystal Palace",
    "Everton" to "Leeds United",
    "Bayern Monachium" to "RB Lipsk",
    "Borussia Dortmund" to "VfL Wolfsburg",
    "Eintracht Frankfurt" to "Bayer Leverkusen",
    "Union Berlin" to "Borussia Mönchengladbach",
    "VfB Stuttgart" to "SC Freiburg",
    "TSG Hoffenheim" to "1. FSV Mainz 05",
    "FC Augsburg" to "Hertha BSC",
)

class MatchesApiFake {

    companion object {

        private val MATCHES = List(teamCombinations.count()) { index ->
            val (teamAName, teamBName) = teamCombinations[index]
            MatchSchema(
                id = index,
                teamAName = teamAName,
                scoreA = 0,
                teamBName = teamBName,
                scoreB = 0
            )
        }
    }

    fun getMatches(): List<MatchSchema> {
        // Simulating network call
        Thread.sleep(3000)

        return MATCHES
    }
}

data class MatchSocketUpdate(
    val id: Int,
    val scoreA: Int,
    val scoreB: Int,
)

class MatchesSocketFake(private val activity: Activity) {

    private class MatchScore(var scoreA: Int, var scoreB: Int)

    var onMatchUpdate: (MatchSocketUpdate) -> Unit = { }

    private val matches: Map<Int, MatchScore> = createInitialScores()
    private var thread: Thread? = null

    fun start() {
        thread = Thread {
            try {
                while (true) {
                    // Simulating network call
                    Thread.sleep(500)
                    val update = createMatchUpdate()
                    activity.runOnUiThread {
                        onMatchUpdate(update)
                    }
                }
            } catch (e: Exception) {
                /* Empty */
            }
        }
        thread?.start()
    }

    fun stop() {
        thread?.interrupt()
        thread = null
    }

    private fun createInitialScores() =
        List(teamCombinations.count()) { index ->
            index to MatchScore(0, 0)
        }.toMap()

    private fun createMatchUpdate(): MatchSocketUpdate {
        val matchIndex = Random.nextInt(teamCombinations.count())
        return if (Random.nextBoolean()) {
            MatchSocketUpdate(
                id = matchIndex,
                scoreA = matches[matchIndex]!!.scoreA++,
                scoreB = matches[matchIndex]!!.scoreB
            )
        } else {
            MatchSocketUpdate(
                id = matchIndex,
                scoreA = matches[matchIndex]!!.scoreA,
                scoreB = matches[matchIndex]!!.scoreB++
            )
        }
    }
}
