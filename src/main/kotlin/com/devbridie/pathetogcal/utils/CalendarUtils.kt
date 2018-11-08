package com.devbridie.pathetogcal.utils

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.MapTimeZoneCache
import java.net.URL


fun Calendar.events() = this.components.mapNotNull { it as? VEvent }

fun getCalendarFromURL(url: String): Calendar {
    System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache::class.java.name)
    return URL(url).openStream().use { CalendarBuilder().build(it) }
}