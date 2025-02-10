package com.survivalMode.services.dailycycle

import lombok.Setter
import net.runelite.api.Client
import net.runelite.client.ui.overlay.Overlay
import net.runelite.client.ui.overlay.OverlayLayer
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import javax.inject.Inject

class BrightnessOverlay @Inject internal constructor(private val client: Client) : Overlay() {

    init { layer = OverlayLayer.UNDER_WIDGETS }

    @Setter
    var dayCycle = Cycle.DAY;

    enum class Cycle {
        DAY,
        NIGHT,
    }

    override fun render(graphics: Graphics2D): Dimension? {
        val gameCanvas = client.canvas ?: return null

        val overlayColor = when (dayCycle) {
            Cycle.DAY   -> Color(0x00000000, true)
            Cycle.NIGHT -> Color(0x00000066, true)
        }

        overlayColor.let { color ->
            graphics.color = color
            graphics.fillRect(0, 0, gameCanvas.width, gameCanvas.height)
        }

        return null
    }
}