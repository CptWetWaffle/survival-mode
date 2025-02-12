package com.survivalMode

import net.runelite.client.config.Config
import net.runelite.client.config.ConfigGroup
import net.runelite.client.config.ConfigItem

@ConfigGroup("general")
interface SurvivalModeConfig : Config {
    @ConfigItem(
        keyName = "disableDarknessOverlay",
        name = "Darkness Overlay",
        description = "The screen darkening on night time"
    )
    fun disableDarknessOverlay(): Boolean = false

    @ConfigItem(
        keyName = "testMode",
        name = "Test mode",
        description = "Test the game mode on any account"
    )
    fun testMode(): Boolean = false
}
