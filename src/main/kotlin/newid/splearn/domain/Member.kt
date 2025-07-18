package newid.splearn.domain

class Member {
    val email: String
    var nickname: String
    var passworkdHash: String
    var status: MemberStatus

    constructor(email: String, nickname: String, passworkdHash: String) {
        this.email = requireNotNull(email)
        this.nickname = requireNotNull(nickname)
        this.passworkdHash = requireNotNull(passworkdHash)
        this.status = MemberStatus.PENDING
    }

    fun activate() {
        check(this.status == MemberStatus.PENDING)

        this.status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        check(this.status == MemberStatus.ACTIVE)

        this.status = MemberStatus.DEACTIVATED
    }

}