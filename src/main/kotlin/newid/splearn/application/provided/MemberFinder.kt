package newid.splearn.application.provided

import newid.splearn.domain.Member

/**
 *
 */
interface MemberFinder {
    fun find(id: Long?): Member
}