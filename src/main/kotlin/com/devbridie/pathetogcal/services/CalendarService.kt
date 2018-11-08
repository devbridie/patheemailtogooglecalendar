package com.devbridie.pathetogcal.services

import net.fortuna.ical4j.model.component.VEvent


interface CalendarService {
    fun addEvent(event: VEvent)
}
