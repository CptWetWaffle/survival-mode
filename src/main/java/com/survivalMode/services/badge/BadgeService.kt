package com.survivalMode.services.badge

import com.survivalMode.models.GameMode
import com.survivalMode.models.GameMode.*
import net.runelite.api.Client
import net.runelite.api.IndexedSprite
import net.runelite.client.util.ImageUtil
import java.awt.image.BufferedImage
import javax.inject.Inject

class BadgeService @Inject constructor(private val client: Client): IBadgeService {
    override fun getBadge(gameMode: GameMode) : BufferedImage =
        ImageUtil.loadImageResource(
            javaClass,
            "/resources/chat/${getPrefix(gameMode)}.png"
        )

    override fun getIndexedSprite(gameMode: GameMode): IndexedSprite =
        ImageUtil.getImageIndexedSprite(getBadge(gameMode), client)

    private fun getPrefix(gameMode: GameMode): String =
        when(gameMode) {
            ApocalypticSurvivalMan   -> "ap"
            HardcoreSurvivalMan      -> "hc"
            SurvivalMan              -> "sm"
            HardcoreBankMan          -> "bh"
            BankMan                  -> "bm"
            HardcoreGroupSurvivalMan -> TODO()
            GroupSurvivalMan         -> TODO()
            HardcoreGroupBankMan     -> TODO()
            GroupBankMan             -> TODO()
            Creative                 -> "ct"
        }
}