package com.devbridie.pathetogcal.google

import com.devbridie.pathetogcal.services.EmailService
import com.devbridie.pathetogcal.services.PatheEmail
import com.devbridie.pathetogcal.utils.join
import com.devbridie.pathetogcal.utils.subject
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Thread

class GmailService() : EmailService {
    private fun Gmail.findPatheReservationThreads(): List<Thread> {
        return this.users().Threads().list("me")
            .setQ("{subject: ticketbevestiging subject:reservering} from:pathe")
            .execute().threads
    }
    override fun findPatheEmails(): List<PatheEmail> {
        return GoogleServices.gmailService.findPatheReservationThreads().map { thread ->
            val threadData = GoogleServices.gmailService.users().threads().get("me", thread.id).execute()
            val message = threadData.messages[0]
            val emailSubject = message.subject.value
            val name = Regex(".+? (.*) -.*").matchEntire(emailSubject)!!.groups[1]!!.value
            val content = message.payload.parts.join()
            PatheEmail(name, content)
        }
    }
}