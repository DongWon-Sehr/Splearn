package newid.splearn.domain.member

import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import newid.splearn.domain.shared.Email
import org.hibernate.annotations.NaturalId

@Entity
class Member : AbstractEntity() {
    @NaturalId @Embedded
    lateinit var email: Email

    lateinit var nickname: String

    lateinit var passwordHash: String

    @Enumerated
    lateinit var status: MemberStatus

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    lateinit var detail: MemberDetail

    companion object {
        @JvmStatic
        fun register(createRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder): Member {
            val member = Member()
            member.email = requireNotNull(Email(createRequest.email))
            member.nickname = createRequest.nickname
            member.passwordHash = requireNotNull(passwordEncoder.encode(createRequest.password))
            member.status = MemberStatus.PENDING
            member.detail = MemberDetail.create()

            return member
        }
    }

    fun activate() {
        check(status == MemberStatus.PENDING)

        status = MemberStatus.ACTIVE

        detail.activate()
    }

    fun deactivate() {
        check(status == MemberStatus.ACTIVE)

        status = MemberStatus.DEACTIVATED

        detail.deactivate()
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, passwordHash)
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive(): Boolean {
        return status == MemberStatus.ACTIVE
    }

    fun updateMemberInfo(updateMemberInfoRequest: MemberInfoUpdateRequest) {
        this.nickname = updateMemberInfoRequest.nickname
        this.detail.updateMemberInfo(updateMemberInfoRequest)
    }
}