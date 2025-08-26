package newid.splearn.application.member.required

import newid.splearn.domain.shared.Email

interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}