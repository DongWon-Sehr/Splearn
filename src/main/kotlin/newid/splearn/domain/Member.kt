package newid.splearn.domain

class Member private constructor(
    val email: Email,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus
) {
    companion object {
        @JvmStatic
        fun create(createRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder): Member {
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