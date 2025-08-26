package newid.splearn.application.member

import jakarta.transaction.Transactional
import newid.splearn.application.member.provided.MemberFinder
import newid.splearn.application.member.required.MemberRepository
import newid.splearn.domain.member.Member
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Transactional
@Validated
class MemberQueryService: MemberFinder {
    private final val memberRepository: MemberRepository

    constructor(
        memberRepository: MemberRepository
    ) {
        this.memberRepository = memberRepository
    }

    override fun find(memberId: Long?): Member {
        val member: Member = memberRepository.findById(memberId).orElseThrow { throw IllegalArgumentException("회원 아이디를 찾을 수 없습니다. memberId: " + memberId) }

        return member
    }
}