package com.survivalMode.services.dailycycle

import javax.inject.Inject
import net.runelite.api.Client
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class DayNightSkyboxService {
    @Inject
    private lateinit var client: Client
    private val logger: Logger = LoggerFactory.getLogger(DayNightSkyboxService::class.java)
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val totalCycleTicks = minute * 30

    enum class TimeOfDay(val colour: Int, val ticks: Long){
        EARLY_DAWN (0x4B0082,  1.minutesMillis()),
        MID_DAWN   (0xFFDAB9,  2.minutesMillis()),
        LATE_DAWN  (0xB8DDE8,  3.minutesMillis()),
        DAY        (0x87CEEB, 20.minutesMillis()),
        EARLY_DUSK (0xB8DDE8, 21.minutesMillis()),
        MID_DUSK   (0xFFDAB9, 22.minutesMillis()),
        LATE_DUSK  (0x4B0082, 23.minutesMillis()),
        EARLY_NIGHT(0x000080, 24.minutesMillis()),
        MID_NIGHT  (0x000040, 29.minutesMillis()),
        LATE_NIGHT (0x000080, 30.minutesMillis()),
    }

    fun scheduleTimeBasedCycle() {
        executor.scheduleAtFixedRate(
            /* command = */      this::updateDayNightCycle,
            /* initialDelay = */ 0,
            /* period = */       1,
            /* unit = */         TimeUnit.MINUTES
        )
    }

    private fun updateDayNightCycle() {
        val gameTime =
            Duration.between(LocalTime.MIDNIGHT, LocalTime.now()).toMillis()

        if (gameTime == -1L) return

        val cyclePositionTicks = gameTime % totalCycleTicks

        when {
            cyclePositionTicks < TimeOfDay.EARLY_DAWN.ticks ->
                client.skyboxColor = TimeOfDay.EARLY_DAWN.colour

            cyclePositionTicks < TimeOfDay.MID_DAWN.ticks ->
                client.skyboxColor = TimeOfDay.MID_DAWN.colour

            cyclePositionTicks < TimeOfDay.LATE_DAWN.ticks ->
                client.skyboxColor = TimeOfDay.LATE_DAWN.colour

            cyclePositionTicks < TimeOfDay.DAY.ticks ->
                client.skyboxColor = TimeOfDay.DAY.colour

            cyclePositionTicks < TimeOfDay.EARLY_DUSK.ticks ->
                client.skyboxColor = TimeOfDay.EARLY_DUSK.colour

            cyclePositionTicks < TimeOfDay.MID_DUSK.ticks ->
                client.skyboxColor = TimeOfDay.MID_DUSK.colour

            cyclePositionTicks < TimeOfDay.LATE_DUSK.ticks ->
                client.skyboxColor = TimeOfDay.LATE_DUSK.colour

            cyclePositionTicks < TimeOfDay.EARLY_NIGHT.ticks ->
                client.skyboxColor = TimeOfDay.EARLY_NIGHT.colour

            cyclePositionTicks < TimeOfDay.MID_NIGHT.ticks ->
                client.skyboxColor = TimeOfDay.MID_NIGHT.colour

            else ->
                client.skyboxColor = TimeOfDay.LATE_NIGHT.colour
        }
    }

    companion object {
        private val minute = Duration.ofMinutes(/* minutes = */ 1).toMillis()
        fun Int.minutesMillis() = minute * this
    }
}