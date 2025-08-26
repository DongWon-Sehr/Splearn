package newid.splearn.domain.member

import newid.splearn.domain.shared.Email
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach

class MemberTest {
    lateinit var member: Member
    val passwordEncoder = MemberFixture.createPasswordEncoder()
    val createRequest = MemberFixture.createMemberRequest()

    @BeforeEach
    fun setUp() {
        member = Member.register(createRequest, passwordEncoder)
    }

    @Test
    fun createMember() {
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
        assertThat(member.detail.registeredAt).isNotNull()
    }

    @Test
    fun activateMember() {
        assertThat(member.detail.activatedAt).isNull()
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)

        assertThat(member.detail.activatedAt).isNotNull()
    }

    @Test
    fun activateFail() {
        member.activate()

        assertThatThrownBy { member.activate() }
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun deactivateMember() {
        assertThat(member.detail.deactivatedAt).isNull()
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
        assertThat(member.detail.deactivatedAt).isNotNull()
    }

    @Test
    fun deactivateFail() {
        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java)

        member.activate()
        member.deactivate()
        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun verifyPassword() {
        assertThat(member.verifyPassword("12345678", passwordEncoder)).isTrue()
    }

    @Test
    fun verifyPasswordFail() {
        assertThat(member.verifyPassword("56781234", passwordEncoder)).isFalse()
    }

    @Test
    fun changeNickname() {
        assertThat(member.nickname).isEqualTo("DongWon Sehr")

        member.changeNickname("Tuna")

        assertThat(member.nickname).isEqualTo("Tuna")
    }

    @Test
    fun changePassword() {
        assertThat(member.verifyPassword("12345678", passwordEncoder)).isTrue()

        member.changePassword("56781234", passwordEncoder)

        assertThat(member.verifyPassword("56781234", passwordEncoder)).isTrue()
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

    @Test
    fun updateMemberInfo() {
        member.activate()

        val request = MemberInfoUpdateRequest("dongwon", "dongwon", "my name is dongwon")
        member.updateMemberInfo(request)

        assertThat(member.nickname).isEqualTo(request.nickname)
        assertThat(member.detail.profile?.address).isEqualTo(request.profileAddress)
        assertThat(member.detail.introduction).isEqualTo(request.introduction)
    }

    @Test
    fun updateMemberInfoFail() {
        val request = MemberInfoUpdateRequest("dongwon", "veryverylonglongaddressdongwondongwon", "my name is dongwon")

        assertThatThrownBy { member.updateMemberInfo(request) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}