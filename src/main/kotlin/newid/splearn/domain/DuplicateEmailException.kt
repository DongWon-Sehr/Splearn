package newid.splearn.domain

import java.lang.RuntimeException

class DuplicateEmailException(message: String) : RuntimeException(message)