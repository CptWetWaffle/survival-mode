package com.survivalMode.services.badge

import com.survivalMode.models.GameMode
import java.awt.image.BufferedImage

interface IBadgeService {
    fun getBadge(gameMode: GameMode) : BufferedImage
}