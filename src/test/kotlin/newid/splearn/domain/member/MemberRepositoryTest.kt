package newid.splearn.domain.member

import jakarta.persistence.EntityManager
import newid.splearn.application.member.required.MemberRepository
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

    @Test
    fun updateMemberInfo() {
        val member: Member = Member.register(MemberFixture.createMemberRequest(), MemberFixture.createPasswordEncoder())

        val memberInfoUpdateRequest = MemberInfoUpdateRequest("dongwon", "dongwon", "my name is dongwon")
        member.updateMemberInfo(memberInfoUpdateRequest)

        memberRepository.save(member)

        entityManager.flush()
        entityManager.clear()

        val found: Member = memberRepository.findById(member.id).orElseThrow()

        assertThat(found.nickname).isEqualTo(memberInfoUpdateRequest.nickname)
        assertThat(found.detail.profile?.address).isEqualTo(memberInfoUpdateRequest.profileAddress)
        assertThat(found.detail.introduction).isEqualTo(memberInfoUpdateRequest.introduction)
    }
}