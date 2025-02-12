package com.survivalMode.services.dailycycle

import com.survivalMode.SurvivalModeConfig
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
    private val config: SurvivalModeConfig,
    private val client: Client,
    private val dailyCycleService: DailyCycleService,
) : Overlay() {
    private val logger = LoggerFactory.getLogger(BrightnessOverlay::class.java)

    init {
        layer = OverlayLayer.ABOVE_SCENE
        priority = 1f
        position = OverlayPosition.TOP_LEFT
    }

    override fun render(graphics: Graphics2D): Dimension? {
        if (config.disableDarknessOverlay())
            return null;

        val gameCanvas = client.canvas ?: return null

        Color(0, 0, 0, dailyCycleService.getCurrentDarkness()).let {
            c ->
                graphics.color      = c
                graphics.background = c
        }
        graphics.fillRect(0, 0, gameCanvas.width, gameCanvas.height)
        return Dimension(gameCanvas.width, gameCanvas.height)
    }
}