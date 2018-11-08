package com.devbridie.pathetogcal

import org.jsoup.Jsoup


fun findCalendarURL(content: String): String? {
    val document = Jsoup.parse(content)
    val link = document.select("a[href*='downloads/ical']")
    return link.firstOrNull()?.attr("href")
}