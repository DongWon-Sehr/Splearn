package newid.splearn.domain

interface PasswordEncoder {
    public fun encode(password: String): String
    public fun matches(password: String, passwordHash: String): Boolean
}