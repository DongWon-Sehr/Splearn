package newid.splearn.domain

import java.util.regex.Pattern

data class Email(val address: String) {
    companion object {
        private val EMAIL_PATTERN: Pattern =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    }

    init {
        require(EMAIL_PATTERN.matcher(address).matches())
    }
}
