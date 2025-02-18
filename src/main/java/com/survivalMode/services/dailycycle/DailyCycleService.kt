package com.survivalMode.services.dailycycle

import javax.inject.Inject
import net.runelite.api.Client
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class DailyCycleService @Inject internal constructor(
    private val client: Client,
): IDailyCycle {
    private val logger = LoggerFactory.getLogger(DailyCycleService::class.java)
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private val totalCycleTicks = minute * 30

    enum class TimeOfDay(val colour: Int, val ticks: Long){
        EARLY_DAWN (0x000080,  1.minutesMillis()),
        MID_DAWN   (0x35349B,  2.minutesMillis()),
        LATE_DAWN  (0x6967B6,  3.minutesMillis()),
        DAY        (0x87CEEB, 20.minutesMillis()),
        EARLY_DUSK (0x6967B6, 21.minutesMillis()),
        MID_DUSK   (0x35349B, 22.minutesMillis()),
        LATE_DUSK  (0x000080, 23.minutesMillis()),
        EARLY_NIGHT(0x000040, 24.minutesMillis()),
        MID_NIGHT  (0x000030, 29.minutesMillis()),
        LATE_NIGHT (0x000040, 30.minutesMillis());

        fun isNight() =
            this == EARLY_NIGHT || this == MID_NIGHT || this == LATE_NIGHT

        fun isDarker() =
            this == MID_DUSK || this == LATE_DUSK || this == EARLY_DAWN || this == MID_DAWN
    }

    private var currentTimeOfDay: TimeOfDay = TimeOfDay.EARLY_DAWN

    override fun start() {
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
                setTimeOfDay(TimeOfDay.EARLY_DAWN)

            cyclePositionTicks < TimeOfDay.MID_DAWN.ticks ->
                setTimeOfDay(TimeOfDay.MID_DAWN)

            cyclePositionTicks < TimeOfDay.LATE_DAWN.ticks ->
                setTimeOfDay(TimeOfDay.LATE_DAWN)

            cyclePositionTicks < TimeOfDay.DAY.ticks ->
                setTimeOfDay(TimeOfDay.DAY)

            cyclePositionTicks < TimeOfDay.EARLY_DUSK.ticks ->
                setTimeOfDay(TimeOfDay.EARLY_DUSK)

            cyclePositionTicks < TimeOfDay.MID_DUSK.ticks ->
                setTimeOfDay(TimeOfDay.MID_DUSK)

            cyclePositionTicks < TimeOfDay.LATE_DUSK.ticks ->
                setTimeOfDay(TimeOfDay.LATE_DUSK)

            cyclePositionTicks < TimeOfDay.EARLY_NIGHT.ticks ->
                setTimeOfDay(TimeOfDay.EARLY_NIGHT)

            cyclePositionTicks < TimeOfDay.MID_NIGHT.ticks ->
                setTimeOfDay(TimeOfDay.MID_NIGHT)

            else ->
                setTimeOfDay(TimeOfDay.LATE_NIGHT)
        }
    }

    private fun setTimeOfDay(timeOfDay: TimeOfDay){
        if (currentTimeOfDay == timeOfDay)
            return

        currentTimeOfDay = timeOfDay
        client.skyboxColor = timeOfDay.colour

        logger.info("setTimeOfDay $currentTimeOfDay")
    }

    private var currentDarkness : Int = 0
    fun getCurrentDarkness(): Int {
        if (currentTimeOfDay.isDarker())
            if (currentDarkness < DARKER_DARKNESS_LIMIT)
                currentDarkness++
            else
                currentDarkness--
        else if (currentTimeOfDay.isNight())
            if (currentDarkness < NIGHT_DARKNESS_LIMIT)
                currentDarkness++
            else
                currentDarkness--
        else
            if (currentDarkness > DAY_DARKNESS_LIMIT)
                currentDarkness--

        return currentDarkness / FRAME_DELAY
    }

    companion object {
        private val minute = Duration.ofMinutes(/* minutes = */ 1).toMillis()
        fun Int.minutesMillis() = minute * this

        const val FRAME_DELAY = 12

        const val NIGHT_DARKNESS_LIMIT  = (69 + 69) * FRAME_DELAY
        const val DARKER_DARKNESS_LIMIT = 69 * FRAME_DELAY
        const val DAY_DARKNESS_LIMIT    = 0
    }
}