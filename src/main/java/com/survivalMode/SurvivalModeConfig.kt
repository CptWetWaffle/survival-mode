package com.survivalMode

import net.runelite.client.config.Config
import net.runelite.client.config.ConfigGroup
import net.runelite.client.config.ConfigItem

@ConfigGroup("general")
interface SurvivalModeConfig : Config {
    @ConfigItem(
        keyName = "greeting",
        name = "Welcome Greeting",
        description = "The message to show to the user when they login"
    )
    fun greeting(): String {
        return "Hello"
    }

    @ConfigItem(
        keyName = "testMode",
        name = "Test mode",
        description = "Test the game mode on any account. Doesn't count towards leaderboards"
    )
    fun testMode(): Boolean {
        return false
    }
}
