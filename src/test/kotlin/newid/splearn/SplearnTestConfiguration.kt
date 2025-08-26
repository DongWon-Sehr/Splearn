package newid.splearn

import newid.splearn.application.member.required.EmailSender
import newid.splearn.domain.shared.Email
import newid.splearn.domain.member.MemberFixture
import newid.splearn.domain.member.PasswordEncoder
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