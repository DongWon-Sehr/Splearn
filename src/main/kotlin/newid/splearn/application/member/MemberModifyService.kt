package newid.splearn.application.member

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import newid.splearn.application.member.provided.MemberFinder
import newid.splearn.application.member.provided.MemberRegister
import newid.splearn.application.member.required.EmailSender
import newid.splearn.application.member.required.MemberRepository
import newid.splearn.domain.member.DuplicateEmailException
import newid.splearn.domain.shared.Email
import newid.splearn.domain.member.Member
import newid.splearn.domain.member.MemberRegisterRequest
import newid.splearn.domain.member.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Transactional
@Validated
class MemberModifyService: MemberRegister {
    private final val memberFinder: MemberFinder
    private final val memberRepository: MemberRepository
    private final val emailSender: EmailSender
    private final val passwordEncoder: PasswordEncoder

    constructor(
        memberFinder: MemberFinder,
        memberRepository: MemberRepository,
        emailSender: EmailSender,
        passwordEncoder: PasswordEncoder
    ) {
        this.memberFinder = memberFinder
        this.memberRepository = memberRepository
        this.emailSender = emailSender
        this.passwordEncoder = passwordEncoder
    }

    override fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(memberRegisterRequest)

        val member = Member.register(memberRegisterRequest, passwordEncoder)

        memberRepository.save(member)

        sendWelcomeEmail(member)

        return member
    }

    override fun activate(memberId: Long?): Member {
        val member: Member = memberFinder.find(memberId)

        member.activate()

        return memberRepository.save(member)
    }

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(member.email, "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요")
    }

    private fun checkDuplicateEmail(registerRequest: MemberRegisterRequest) {
        if (memberRepository.findByEmail(Email(registerRequest.email)).isPresent()) {
            throw DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email)
        }
    }
}