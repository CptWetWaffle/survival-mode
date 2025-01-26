package com.survivalMode.services.highscore

import com.survivalMode.models.Account
import com.survivalMode.models.GameMode

interface IHighScoreService {
    fun putAsync(account: Account)
    fun getAsync(username: String)
    fun getManyAsync(page: Int, gameMode: GameMode)
}
