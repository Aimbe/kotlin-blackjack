package blackjack.domain

import blackjack.domain.card.Deck
import blackjack.domain.player.Dealer
import blackjack.domain.player.DealerState
import blackjack.domain.player.Player
import blackjack.domain.state.GameResult

class BlackjackGame {
    private val deck = Deck()
    val dealer = Dealer()
    private var players = listOf<Player>()

    fun drawCard() = deck.draw()

    fun start(players: List<Player>) {
        this.players = players
        dealInitialCards()
    }

    fun calculateProfits(players: List<Player>, dealer: Dealer): Map<Player, Double> {
        val dealerState = dealer.getState()

        return players.associateWith { player ->
            determineResult(player.score(), dealerState)
            player.calculateProfit(dealerState)
        }
    }

    fun calculateDealerProfit(players: List<Player>, dealer: Dealer): Double {
        val dealerState = dealer.getState()

        return -players.sumOf { player ->
            determineResult(player.score(), dealerState)
            player.calculateProfit(dealerState)
        }
    }

    private fun dealInitialCards() {
        repeat(INITIAL_CARD_COUNT) {
            dealer.drawCard(deck.draw())
            players.forEach { player ->
                player.drawCard(deck.draw())
            }
        }
    }

    private fun determineResult(playerScore: Int, dealerState: DealerState): GameResult {
        return when {
            dealerState.isBust -> GameResult.WIN
            dealerState.isBlackjack -> GameResult.LOSE
            playerScore > dealerState.score -> GameResult.WIN
            playerScore < dealerState.score -> GameResult.LOSE
            else -> GameResult.DRAW
        }
    }

    companion object {
        private const val INITIAL_CARD_COUNT = 2
    }
}
