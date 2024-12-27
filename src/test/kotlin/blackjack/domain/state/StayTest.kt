package blackjack.domain.state

import blackjack.domain.CLUBS_ACE
import blackjack.domain.CLUBS_KING
import blackjack.domain.CLUBS_TEN
import blackjack.domain.CLUBS_TWO
import blackjack.domain.player.DealerState
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StayTest {
    @Test
    fun constructor() {
        assertThrows<IllegalArgumentException> {
            Stay(Hands())
        }
        assertThrows<IllegalArgumentException> {
            Stay(Hands(CLUBS_ACE))
        }
    }

    @Test
    fun profit() {
        val state = Stay(Hands(CLUBS_TWO, CLUBS_TEN, CLUBS_KING))
        val acutal = state.profit(1000, DealerState(isBust = false, isBlackjack = false))

        acutal shouldBe 1_000
    }
}
