package com.survivalMode

import com.google.inject.Provides
import com.survivalMode.services.dailycycle.DailyCycle
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import net.runelite.api.ChatMessageType
import net.runelite.api.Client
import net.runelite.api.GameState
import net.runelite.api.events.GameStateChanged
import net.runelite.client.config.ConfigManager
import net.runelite.client.eventbus.Subscribe
import net.runelite.client.plugins.Plugin
import net.runelite.client.plugins.PluginDescriptor
import javax.inject.Inject

@Slf4j
@PluginDescriptor(name = "Survival Mode")
class SurvivalModePlugin : Plugin() {
    private val logger: Logger = LoggerFactory.getLogger(SurvivalModePlugin::class.java)

    @Inject
    private lateinit var config: SurvivalModeConfig

    @Inject
    private lateinit var client: Client

    @Inject
    private lateinit var dailyCycle: DailyCycle

    @Throws(Exception::class)
    override fun startUp() {
        logger.info("Example started!")
        dailyCycle.start()
    }

    @Throws(Exception::class)
    override fun shutDown() {
        logger.info("Example stopped!")
    }

    @Subscribe
    fun onGameStateChanged(gameStateChanged: GameStateChanged) {
        if (gameStateChanged.gameState == GameState.LOGGED_IN)
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null)
    }

    @Provides
    fun provideConfig(configManager: ConfigManager): SurvivalModeConfig =
        configManager.getConfig(SurvivalModeConfig::class.java)
}
