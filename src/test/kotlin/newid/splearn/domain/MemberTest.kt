package newid.splearn.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.*

class MemberTest {
    @Test
    fun createMember() {
        var member = Member("dongwon@its-newid.com", "DongWon Sehr", "1234");
        assertThat(member.status).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    fun activateMember() {
        var member = Member("dongwon@its-newid.com", "DongWon Sehr", "1234");

        member.activate();

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    fun activateFail() {
        var member = Member("dongwon@its-newid.com", "DongWon Sehr", "1234");
        member.activate();

        assertThatThrownBy { member.activate() }
            .isInstanceOf(IllegalStateException::class.java);
    }

    @Test
    fun deactivateMember() {
        var member = Member("dongwon@its-newid.com", "DongWon Sehr", "1234");
        member.activate();

        member.deactivate();

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    fun deactivateFail() {
        var member = Member("dongwon@its-newid.com", "DongWon Sehr", "1234");

        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java);

        member.activate();
        member.deactivate();
        assertThatThrownBy { member.deactivate() }
            .isInstanceOf(IllegalStateException::class.java);
    }
}