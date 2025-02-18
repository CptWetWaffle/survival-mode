package com.survivalMode.services.badge

import com.survivalMode.models.GameMode
import net.runelite.client.util.ImageUtil
import java.awt.image.BufferedImage

class BadgeService : IBadgeService {
    override fun getBadge(gameMode: GameMode) : BufferedImage {
        /*val prefix: String =
            when(gameMode) {
                GameMode.Regular     -> "rg"
                GameMode.Hardcore    -> "hc"
                GameMode.Apocalyptic -> "ap",
            }

        val fullName: String =
            "$prefix-im.png"

        return ImageUtil.loadImageResource(
            javaClass,
            "/resources/chat/$fullName"
        )*/
        TODO()
    }
}