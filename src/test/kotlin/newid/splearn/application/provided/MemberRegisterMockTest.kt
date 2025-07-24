package newid.splearn.application.provided

import newid.splearn.application.MemberModifyService
import newid.splearn.application.required.EmailSender
import newid.splearn.application.required.MemberRepository
import newid.splearn.domain.Member
import newid.splearn.domain.MemberFixture
import newid.splearn.domain.MemberStatus
import org.mockito.Mockito
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class MemberRegisterMockTest {
    @Test
    fun registerTestMockito() {
        val memberFinderMock: MemberFinder = Mockito.mock(MemberFinder::class.java)
        val emailSenderMock: EmailSender = Mockito.mock(EmailSender::class.java)
        val memberRepositoryMock: MemberRepository = Mockito.mock(MemberRepository::class.java)

        val register: MemberRegister = MemberModifyService(
            memberFinderMock,
            memberRepositoryMock,
            emailSenderMock,
            MemberFixture.createPasswordEncoder()
        )

        val member: Member = register.register(MemberFixture.createMemberRequest())

        assertThat(member.id).isNotNull()
        assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }
}