package com.survivalMode.services.badge

import com.survivalMode.models.GameMode
import net.runelite.api.IndexedSprite
import java.awt.image.BufferedImage

interface IBadgeService {
    fun getBadge(gameMode: GameMode): BufferedImage
    fun getIndexedSprite(gameMode: GameMode): IndexedSprite
}