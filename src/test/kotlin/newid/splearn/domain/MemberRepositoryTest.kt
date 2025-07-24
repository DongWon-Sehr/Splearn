package newid.splearn.domain

import jakarta.persistence.EntityManager
import newid.splearn.application.required.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.springframework.dao.DataIntegrityViolationException
import kotlin.test.Test

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository : MemberRepository

    @Autowired
    lateinit var entityManager : EntityManager

    @Test
    fun registerMember() {
        val member: Member = Member.register(MemberFixture.createMemberRequest(), MemberFixture.createPasswordEncoder())

        assertThat(member.id).isNull()

        memberRepository.save(member)

        assertThat(member.id).isNotNull()

        entityManager.flush()
    }

    @Test
    fun duplicateEmailFail() {
        val member: Member = Member.register(MemberFixture.createMemberRequest(), MemberFixture.createPasswordEncoder())
        memberRepository.save(member)

        val member2: Member = Member.register(MemberFixture.createMemberRequest(), MemberFixture.createPasswordEncoder())
        assertThatThrownBy { memberRepository.save(member2) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }
}