package newid.splearn.domain.member

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class ProfileTest {

    @Test
    fun profile() {
        assertThatThrownBy { Profile("") }
            .isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { Profile("DONGWON") }
            .isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { Profile("thisisverylongnamedongwondongwon") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun url() {
        val profile = Profile("dongwon")
        assertThat(profile.url()).isEqualTo("@dongwon")
    }
}