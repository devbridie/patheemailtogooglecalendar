package com.devbridie.pathetogcal

import com.devbridie.pathetogcal.google.GmailService
import com.devbridie.pathetogcal.google.GoogleCalendarService
import com.devbridie.pathetogcal.utils.events
import com.devbridie.pathetogcal.utils.getCalendarFromURL
import java.io.FileNotFoundException


fun main(args: Array<String>) {
    val calendarService = GoogleCalendarService()
    val emailService = GmailService()
    emailService.findPatheEmails().forEach { email ->
        val link = findCalendarURL(email.emailContents)
        if (link == null) {
            println("Could not find ICS link for film ${email.filmTitle}")
            return@forEach
        }
        try {
            val event = getCalendarFromURL(link).events()[0]
            calendarService.addEvent(event)
            println("Added calendar event for ${email.filmTitle}")
        } catch (e: FileNotFoundException) {
            println("ICal link $link expired")
        }
    }
}