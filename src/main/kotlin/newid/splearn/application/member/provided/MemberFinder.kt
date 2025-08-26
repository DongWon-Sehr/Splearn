package newid.splearn.application.member.provided

import newid.splearn.domain.member.Member

/**
 *
 */
interface MemberFinder {
    fun find(id: Long?): Member
}