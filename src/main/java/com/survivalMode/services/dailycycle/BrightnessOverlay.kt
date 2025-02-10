package com.survivalMode.services.dailycycle

import net.runelite.api.Client
import net.runelite.client.ui.overlay.Overlay
import net.runelite.client.ui.overlay.OverlayLayer
import net.runelite.client.ui.overlay.OverlayPosition
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import javax.inject.Inject

class BrightnessOverlay @Inject internal constructor(
    private val client: Client,
    private val dailyCycleService: DailyCycleService,
) : Overlay() {
    private val logger = LoggerFactory.getLogger(BrightnessOverlay::class.java)

    init {
        layer = OverlayLayer.ABOVE_SCENE
        priority = 1f
        position = OverlayPosition.TOP_LEFT
    }

    companion object {
        val DAY_COLOUR = Color(0, 0, 0, 0)
        val NIGHT_COLOUR = Color(0, 0, 0, 69)
    }

    override fun render(graphics: Graphics2D): Dimension? {
        val gameCanvas = client.canvas ?: return null
        val colour =
            if (dailyCycleService.currentTimeOfDay.isNight())
                Color(0, 0, 0, 169)
            else
                Color(0, 0, 0, 0)

        graphics.color = colour
        graphics.background = colour
        graphics.fillRect(0, 0, gameCanvas.width, gameCanvas.height)

        return Dimension(gameCanvas.width, gameCanvas.height)
    }
}