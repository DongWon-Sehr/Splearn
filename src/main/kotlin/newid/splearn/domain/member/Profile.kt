package newid.splearn.domain.member

import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
data class Profile(var address: String) {
    companion object {
        private val PROFILE_ADDRESS_PATTERN: Pattern =
            Pattern.compile("^[a-z0-9]+$")
    }

    init {
        require(PROFILE_ADDRESS_PATTERN.matcher(address).matches())
        require(address.length <= 15)
    }

    fun url(): String {
        return "@${address}"
    }
}