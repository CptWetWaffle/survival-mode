package com.survivalMode.services.dailycycle

import net.runelite.client.config.Config
import net.runelite.client.config.ConfigGroup
import net.runelite.client.config.ConfigItem

@ConfigGroup("brightnessadjust") // Config group name
interface BrightnessAdjustConfig : Config {
    @ConfigItem(
        keyName = "brightnessLevel",
        name = "Brightness Level",
        description = "Adjusts the brightness of the game. Positive values brighten, negative values darken.",
        position = 1,
    )
    fun brightnessLevel(): Int {
        return 0 // Default to no brightness adjustment
    }
}
