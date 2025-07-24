package newid.splearn.application.required

import newid.splearn.domain.Email

interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}