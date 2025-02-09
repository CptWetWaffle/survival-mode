package com.survivalMode.services.dailycycle

import net.runelite.api.Client
import net.runelite.client.plugins.gpu.GpuPlugin
import net.runelite.client.plugins.skybox.SkyboxPlugin
import com.google.inject.Provides
import javax.inject.Inject
import net.runelite.client.config.ConfigManager


class DailyCycle : IDailyCycle {
    /*@Inject
    private lateinit var client: Client

    @Inject
    private lateinit var skybox: SkyboxPlugin

    @Inject
    private lateinit var gpu: GpuPlugin*/

    @Inject
    private lateinit var dayNightSkyboxService: DayNightSkyboxService

    override fun start() {
        dayNightSkyboxService.scheduleTimeBasedCycle()
    }

    @Provides
    fun provideConfig(configManager: ConfigManager): BrightnessAdjustConfig {
        return configManager.getConfig(BrightnessAdjustConfig::class.java)
    }

    @Inject
    private lateinit var overlayManager: net.runelite.client.ui.overlay.OverlayManager // Need to inject OverlayManager
}
