package newid.splearn.domain

class MemberFixture {
    companion object {
        @JvmStatic
        fun createPasswordEncoder(): PasswordEncoder {
            return object : PasswordEncoder {
                override fun encode(password: String): String {
                    return password.uppercase()
                }

                override fun matches(password: String, passwordHash: String): Boolean {
                    return passwordHash.equals(encode(password))
                }
            }
        }

        @JvmStatic
        fun createMemberRequest(): MemberRegisterRequest {
            return MemberRegisterRequest("dongwon@its-newid.com", "DongWon Sehr", "12345678")
        }

        @JvmStatic
        fun createMemberRequest(email: String): MemberRegisterRequest {
            return MemberRegisterRequest(email, "DongWon Sehr", "12345678")
        }
    }
}