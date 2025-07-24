package newid.splearn

import newid.splearn.application.required.EmailSender
import newid.splearn.domain.Email
import newid.splearn.domain.MemberFixture
import newid.splearn.domain.PasswordEncoder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class SplearnTestConfiguration {
    @Bean
    fun emailSender(): EmailSender {
        return object : EmailSender {
            override fun send(email: Email, subject: String, body: String) {

            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return MemberFixture.createPasswordEncoder()
    }
}