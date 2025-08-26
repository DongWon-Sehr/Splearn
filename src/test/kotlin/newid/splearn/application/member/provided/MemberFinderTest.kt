package newid.splearn.application.member.provided

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import newid.splearn.SplearnTestConfiguration
import newid.splearn.domain.member.Member
import newid.splearn.domain.member.MemberFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
class MemberFinderTest {
    @Autowired
    lateinit var memberRegister: MemberRegister

    @Autowired
    lateinit var memberFinder: MemberFinder

    @Autowired
    lateinit var entityManager : EntityManager

    @Test
    fun find() {
        val member: Member = memberRegister.register(MemberFixture.createMemberRequest())

        entityManager.flush()
        entityManager.clear()

        val found: Member = memberFinder.find(member.id)

        assertThat(member.id).isEqualTo(found.id)
    }

    @Test
    fun findFail() {
        assertThatThrownBy { memberFinder.find(999L) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}