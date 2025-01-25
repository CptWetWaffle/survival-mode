package com.survivalMode.services.highscore.models

import lombok.Getter
import java.util.*

@Getter
class Score(
    private val username: String,
    private val combatLevel: Int,
    private val totalLevel: Int,
    private val totalTimePlayed: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Score) return false
        return username == other.username
    }

    override fun hashCode(): Int {
        return Objects.hash(username)
    }

    override fun toString(): String {
        return "Username: " + username +
                ", Combat Level: " + combatLevel +
                ", Total Level: " + totalLevel +
                ", Time Played: " + totalTimePlayed
    }
}
