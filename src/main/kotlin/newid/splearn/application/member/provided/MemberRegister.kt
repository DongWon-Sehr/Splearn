package newid.splearn.application.member.provided

import jakarta.validation.Valid
import newid.splearn.domain.member.Member
import newid.splearn.domain.member.MemberRegisterRequest

interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member

    fun activate(memberId: Long?): Member
}