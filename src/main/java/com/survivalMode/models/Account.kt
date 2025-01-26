package com.survivalMode.models

// @Getter
data class Account(
    val username: String,
    val combatLevel: Int,
    val totalLevel: Int,
    val totalTimePlayed: Int,
    val gameMode: GameMode,
)