package newid.splearn.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class MemberTest {
    lateinit var member: Member
    val passwordEncoder = object : PasswordEncoder {
        override fun encode(password: String): String {
            return password.uppercase()
        }

        override fun matches(password: String, passwordHash: String): Boolean {
            return passwordHash.equals(encode(password))
        }
    }

    @BeforeEach
    fun setUp() {
        member = Member.create(MemberCreateRequest("dongwon@its-newid.com", "DongWon Sehr", "1234"), passwordEncoder)
    }

    @Test
    fun createMember() {
        assertThat(member.status).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    fun activateMember() {
        member.activate();

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    fun activateFail() {
        member.activate();

        assertThatThrownBy { member.activate() }
            .isInstanceOf(IllegalStateException::class.java);
    }

    @Test
    fun deactivateMember() {
        member.activate();

        member.deactivate();

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    fun deactivateFail() {
        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java);

        member.activate();
        member.deactivate();
        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java);
    }

    @Test
    fun verifyPassword() {
        assertThat(member.verifyPassword("1234", passwordEncoder)).isTrue()
    }

    @Test
    fun verifyPasswordFail() {
        assertThat(member.verifyPassword("5678", passwordEncoder)).isFalse()
    }

    @Test
    fun changeNickname() {
        assertThat(member.nickname).isEqualTo("DongWon Sehr")

        member.changeNickname("Tuna")

        assertThat(member.nickname).isEqualTo("Tuna")
    }

    @Test
    fun changePassword() {
        assertThat(member.verifyPassword("1234", passwordEncoder)).isTrue()

        member.changePassword("5678", passwordEncoder)

        assertThat(member.verifyPassword("5678", passwordEncoder)).isTrue()
    }

    @Test
    fun isActive() {
        assertThat(member.isActive()).isFalse()

        member.activate()

        assertThat(member.isActive()).isTrue()

        member.deactivate()

        assertThat(member.isActive()).isFalse()
    }

    @Test
    fun email() {
        val email = Email("dongwon@its-newid.net")
        val email2 = Email("dongwon@its-newid.net")

        assertThat(email).isEqualTo(email2)
    }
}