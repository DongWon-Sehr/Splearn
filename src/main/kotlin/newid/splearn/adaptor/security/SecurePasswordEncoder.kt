package newid.splearn.adaptor.security

import newid.splearn.domain.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurePasswordEncoder : PasswordEncoder {
    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun encode(password: String): String {
        return this.bCryptPasswordEncoder.encode(password)
    }

    override fun matches(password: String, passwordHash: String): Boolean {
        return this.bCryptPasswordEncoder.matches(password, passwordHash)
    }
}