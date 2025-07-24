package newid.splearn.application.required

import newid.splearn.domain.Email
import newid.splearn.domain.Member
import org.springframework.data.repository.Repository
import java.util.Optional

/**
 * 회원 정보를 저장하거나 조회한다.
 */
interface MemberRepository : Repository<Member, Long>{
    fun save(member: Member): Member

    fun findByEmail(email: Email): Optional<Member>

    fun findById(id: Long?): Optional<Member>
}