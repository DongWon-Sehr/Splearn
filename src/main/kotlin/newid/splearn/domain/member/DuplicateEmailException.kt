package newid.splearn.domain.member

import java.lang.RuntimeException

class DuplicateEmailException(message: String) : RuntimeException(message)