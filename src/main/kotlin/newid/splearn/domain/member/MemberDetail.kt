package newid.splearn.domain.member

import jakarta.persistence.Entity
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Entity
@Validated
class MemberDetail : AbstractEntity() {
    private var _profile: Profile? = null
    internal val profile: Profile?
        get() = _profile

    private var _introduction: String? = null
    internal val introduction: String?
        get() = _introduction

    private lateinit var _registeredAt: LocalDateTime
    internal val registeredAt: LocalDateTime
        get() = _registeredAt

    private var _activatedAt: LocalDateTime? = null
    internal val activatedAt: LocalDateTime?
        get() = _activatedAt

    private var _deactivatedAt: LocalDateTime? = null
    internal val deactivatedAt: LocalDateTime?
        get() = _deactivatedAt

    companion object {
        @JvmStatic
        fun create(): MemberDetail {
            val detail: MemberDetail = MemberDetail()

            detail._registeredAt = LocalDateTime.now()

            return detail
        }
    }

    fun activate() {
        assert(this._activatedAt == null, { "activatedAt is already set" })

        this._activatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        assert(this._deactivatedAt == null, { "deactivatedAt is already set" })

        this._deactivatedAt = LocalDateTime.now()
    }

    fun updateMemberInfo(updateMemberInfoRequest: MemberInfoUpdateRequest) {
        this._profile = Profile(updateMemberInfoRequest.profileAddress)
        this._introduction = updateMemberInfoRequest.introduction
    }
}