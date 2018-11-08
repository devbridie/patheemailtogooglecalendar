package com.devbridie.pathetogcal.utils

import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart
import java.nio.charset.Charset


fun List<MessagePart>.join(): String {
    return this.joinToString { it.body.decodeData().toString(Charset.defaultCharset()) }
}

val Message.subject get() = this.payload.headers.find { it.name == "Subject" }!!