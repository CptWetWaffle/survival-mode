package com.survivalMode.services.dailycycle

import net.runelite.api.Client
import net.runelite.client.ui.overlay.Overlay
import net.runelite.client.ui.overlay.OverlayLayer
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import javax.inject.Inject

// Define your BrightnessOverlay class as an inner class or in a separate file
@javax.inject.Singleton
class BrightnessOverlay @Inject internal constructor(
    private val config: BrightnessAdjustConfig // Inject your config (if you make brightness configurable)
) : Overlay() {
    @Inject
    private lateinit var client: Client

    init {
        layer = OverlayLayer.ALWAYS_ON_TOP // Ensure it's always on top
    }

    override fun render(graphics: Graphics2D): Dimension? {
        val gameCanvas = client.canvas ?: return null

        val overlayColor = when {
            config.brightnessLevel() > 0 -> { // Brighten
                Color(255, 255, 255, (config.brightnessLevel() * 2.55f).toInt().coerceIn(0, 255)) // White overlay
            }
            config.brightnessLevel() < 0 -> { // Darken
                Color(0, 0, 0, ((-config.brightnessLevel()) * 2.55f).toInt().coerceIn(0, 255)) // Black overlay
            }
            else -> null // No overlay if brightness level is 0
        }

        overlayColor?.let { color ->
            graphics.color = color
            graphics.fillRect(0, 0, gameCanvas.width, gameCanvas.height)
        }

        return null // Don't affect overlay dimension
    }
}