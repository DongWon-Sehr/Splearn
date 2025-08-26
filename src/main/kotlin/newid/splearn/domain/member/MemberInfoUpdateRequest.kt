package newid.splearn.domain.member

import jakarta.validation.constraints.Size

data class MemberInfoUpdateRequest(
    @field:Size(min = 5, max = 15) val nickname: String,
    @field:Size(min = 1, max = 15) val profileAddress: String,
    val introduction: String,
)
