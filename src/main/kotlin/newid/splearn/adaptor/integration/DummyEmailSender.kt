package newid.splearn.adaptor.integration

import newid.splearn.application.member.required.EmailSender
import newid.splearn.domain.shared.Email
import org.springframework.context.annotation.Fallback
import org.springframework.stereotype.Component

@Component
@Fallback
class DummyEmailSender: EmailSender {
    override fun send(email: Email, subject: String, body: String) {
        System.out.println("Dummy email sent: " + email.address)
    }
}