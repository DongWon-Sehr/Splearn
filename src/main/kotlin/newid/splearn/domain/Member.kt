package newid.splearn.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Member private constructor(
    @Embedded val email: Email,
    var nickname: String,
    var passwordHash: String,
    @Enumerated var status: MemberStatus
): AbstractEntity() {
    companion object {
        @JvmStatic
        fun register(createRequest: MemberRegisterRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                email = requireNotNull(Email(createRequest.email)),
                nickname = requireNotNull(createRequest.nickname),
                passwordHash = requireNotNull(passwordEncoder.encode(createRequest.password)),
                status = MemberStatus.PENDING
            )
        }
    }

    fun activate() {
        check(status == MemberStatus.PENDING)

        status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        check(status == MemberStatus.ACTIVE)

        status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, passwordHash)
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        passwordHash = passwordEncoder.encode(password)
    }

    fun isActive(): Boolean {
        return status == MemberStatus.ACTIVE
    }
}