package com.survivalMode

import com.google.inject.Provides
import com.survivalMode.models.GameMode
import com.survivalMode.services.badge.IBadgeService
import com.survivalMode.services.dailycycle.BrightnessOverlay
import com.survivalMode.services.dailycycle.IDailyCycle
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import net.runelite.api.Client
import net.runelite.api.IndexedSprite
import net.runelite.api.events.ChatMessage
import net.runelite.api.events.GameStateChanged
import net.runelite.client.config.ConfigManager
import net.runelite.client.eventbus.Subscribe
import net.runelite.client.plugins.Plugin
import net.runelite.client.plugins.PluginDescriptor
import net.runelite.client.ui.overlay.OverlayManager
import net.runelite.client.util.Text
import java.util.regex.Pattern
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
    private lateinit var dailyCycle: IDailyCycle

    @Inject
    private lateinit var overlayManager: OverlayManager

    @Inject
    private lateinit var overlay: BrightnessOverlay

    @Inject
    private lateinit var badgeService: IBadgeService

    @Throws(Exception::class)
    override fun startUp() {
        dailyCycle.start()
        overlayManager.add(overlay)
        client.modIcons += badgeService.getIndexedSprite(GameMode.Creative)
    }

    @Throws(Exception::class)
    override fun shutDown() {
        logger.info("Example stopped!")
        overlayManager.remove(overlay)
    }

    @Subscribe
    fun onGameStateChanged(gameStateChanged: GameStateChanged) {
        /*
                if (gameStateChanged.gameState == GameState.LOGGED_IN)
                    client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null)
        */
    }

    @Subscribe
    fun onChatMessage(event: ChatMessage) {
        event.messageNode.value = replaceBadgeInMessage(
            event,
            badgeService.getIndexedSprite(GameMode.Creative)
        )
    }
    private val badgePattern = Pattern.compile("<img=\\d+>")
    private fun replaceBadgeInMessage(message: ChatMessage, replacementImage: IndexedSprite): String? {
        val isPlayer =
            Text.standardize(message.name) == Text.standardize(client.localPlayer.name)

        if (!isPlayer) return message.messageNode.value;


        message.messageNode.value.replaceFirst(
            badgePattern.toRegex(),
            "<img=${client.modIcons.indexOf(replacementImage)}>"
        )

        return message.messageNode.value
    }

    @Provides
    fun provideConfig(configManager: ConfigManager): SurvivalModeConfig =
        configManager.getConfig(SurvivalModeConfig::class.java)
}
