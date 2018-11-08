package com.devbridie.pathetogcal.google

import com.devbridie.pathetogcal.services.CalendarService
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.DateProperty

class GoogleCalendarService() : CalendarService {
    override fun addEvent(event: VEvent) {
        val googleEvent = event.toGCalEvent()
        GoogleServices.calendarService.events().insert("primary", googleEvent).execute()
    }

    private fun VEvent.toGCalEvent(): Event {
        val originalEvent = this
        return Event().apply {
            summary = originalEvent.description.value.split("\n")[0]
            description = originalEvent.description.value
            location = originalEvent.location.value
            start = originalEvent.startDate.convertToGCalDateTime()
            end = originalEvent.endDate.convertToGCalDateTime()
        }.also { println(it)}
    }

    private fun DateProperty.convertToGCalDateTime(): EventDateTime {
        val old = this
        return EventDateTime().apply {
            dateTime = DateTime(old.date.time)
            timeZone = old.timeZone.id
        }
    }

}