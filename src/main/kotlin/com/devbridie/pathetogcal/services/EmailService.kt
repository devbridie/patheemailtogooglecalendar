package com.devbridie.pathetogcal.services

data class PatheEmail(val filmTitle: String, val emailContents: String)

interface EmailService {
    fun findPatheEmails(): List<PatheEmail>
}

