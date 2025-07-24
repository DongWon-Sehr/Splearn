package newid.splearn.application.provided

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.validation.ConstraintViolationException
import newid.splearn.SplearnTestConfiguration
import newid.splearn.domain.DuplicateEmailException
import newid.splearn.domain.Member
import newid.splearn.domain.MemberFixture
import newid.splearn.domain.MemberRegisterRequest
import newid.splearn.domain.MemberStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.context.annotation.Import

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
class MemberRegisterTest {
    @Autowired
    lateinit var memberRegister: MemberRegister

    @Autowired
    lateinit var entityManager : EntityManager

    @Test
    fun register() {
        val member: Member = memberRegister.register(MemberFixture.createMemberRequest())

        assertThat(member.id).isNotNull
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRequest())

        assertThatThrownBy { memberRegister.register(MemberFixture.createMemberRequest()) }
            .isInstanceOf(DuplicateEmailException::class.java)
    }

    @Test
    fun memberRegisterFail() {
        assertMemberRegisterFail(MemberRegisterRequest("dongwon.com", "DongWon Sehr", "12345678"))

        assertMemberRegisterFail(MemberRegisterRequest("dongwon@its-newid.com", "Dong", "12345678"))

        assertMemberRegisterFail(MemberRegisterRequest("dongwon@its-newid.com", "DongWon Sehr", "123"))
    }

    private fun assertMemberRegisterFail(memberRegisterRequest: MemberRegisterRequest) {
        assertThatThrownBy { memberRegister.register(memberRegisterRequest) }
            .isInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    fun activate() {
        var member: Member = memberRegister.register(MemberFixture.createMemberRequest())

        entityManager.flush()
        entityManager.clear()

        member = memberRegister.activate(member.id)
        entityManager.flush()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }
}