package newid.splearn.adaptor.security

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class SecurePasswordEncoderTest {
    val securePasswordEncoder: SecurePasswordEncoder = SecurePasswordEncoder()

    @Test
    fun encodePassword() {
        val password = "123"
        val passwordHash = securePasswordEncoder.encode(password)

        assertThat(securePasswordEncoder.matches(password, passwordHash)).isTrue()
    }
}