package newid.splearn.application.provided

import jakarta.validation.Valid
import newid.splearn.domain.Member
import newid.splearn.domain.MemberRegisterRequest

interface MemberRegister {
    fun register(@Valid memberRegisterRequest: MemberRegisterRequest): Member

    fun activate(memberId: Long?): Member
}