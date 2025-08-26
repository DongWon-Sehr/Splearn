package newid.splearn.adaptor.integration

import newid.splearn.domain.shared.Email
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.junitpioneer.jupiter.StdIo
import org.junitpioneer.jupiter.StdOut

class DummyEmailSenderTest {
    @Test
    @StdIo
    fun dummyEmailSender(out: StdOut) {
        val dummyEmailSender = DummyEmailSender()
        val email = Email("dongwon@its-newid.com")
        dummyEmailSender.send(email, "subject", "body")

        assertThat(out.capturedLines()[0]).isEqualTo("Dummy email sent: " + email.address)
    }
}