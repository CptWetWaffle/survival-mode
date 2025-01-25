package com.survivalMode

import net.runelite.client.RuneLite
import net.runelite.client.externalplugins.ExternalPluginManager

object SurvivalModePluginTest {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        ExternalPluginManager.loadBuiltin(SurvivalModePlugin::class.java)
        RuneLite.main(args)
    }
}