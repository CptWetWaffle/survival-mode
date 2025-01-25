package com.survivalMode.services.highscore

import com.survivalMode.services.highscore.models.Score

interface IHighScoreService {
    fun Put(score: Score?)
}
