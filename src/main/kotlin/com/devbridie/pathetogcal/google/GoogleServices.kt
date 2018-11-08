package com.devbridie.pathetogcal.google

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import java.io.File
import java.io.InputStreamReader


object GoogleServices {
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
    val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    val appName = "Pathe email to gcal"

    private val SCOPES = listOf(GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR)
    private val TOKENS_DIRECTORY_PATH = "tokens"

    val gmailService = Gmail.Builder(
        HTTP_TRANSPORT,
        JSON_FACTORY,
        getCredentials(HTTP_TRANSPORT)
    )
        .setApplicationName(appName)
        .build()
    val calendarService = Calendar.Builder(
        HTTP_TRANSPORT,
        JSON_FACTORY,
        getCredentials(HTTP_TRANSPORT)
    )
        .setApplicationName(appName)
        .build()

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val `in` = GoogleServices::class.java.getResourceAsStream("/credentials.json")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY, clientSecrets,
            SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }
}