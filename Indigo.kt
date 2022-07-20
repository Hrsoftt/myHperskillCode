const val RANKS = "A 2 3 4 5 6 7 8 9 10 J Q K"
const val SUITS = "♦ ♥ ♠ ♣"
var condition = true
fun main() {

    Play().menu()
//    val game = Game()
//    val a = listOf("oe", "wr", "wp")
//    println(game.handChecking(a.toMutableList()))

//    val card = Card()
//    val game = Game()
//    game.initialCards()
//    println(Card.cardDeck.contains("♣"))
//    game.computer()
//    game.player()


//    while (true) {
//        println("Choose an action (reset, shuffle, get, exit):")
//        val input = readln().split(" ")
//        when (input[0]) {
//            "reset" -> {
//                card.reset()
//            }
//            "shuffle" -> {
//                card.shuffle()
//            }
//            "get" -> {
//                println("Number of cards:")
//                val noOfCards = readln()
//                try {
//                    if (noOfCards.toInt() in 1..52) {
//                        card.getVal(noOfCards.toInt())
//                    } else {
//                        println("Invalid number of cards.")
//                    }
//                } catch (e: NumberFormatException) {
//                    println("Invalid number of cards.")
//                }
//            }
//            "exit" -> {
//                println("Bye")
//                break
//            }
//            else -> {
//                println("Wrong action.")
//            }
//        }
//    }
}

class Play() {
    private val game = Game()
    fun menu() {

        println("Indigo Card Game")
        while (condition) {
            println("Play first?")
            when (readln()) {
                "yes" -> {
                    game.initialCards()
                    while (condition) {
                        game.player()
                        if (!condition) break
                        game.computer()
                    }
                }
                "no" -> {
                    game.initialCards()
                    while (condition) {
                        game.computer()
                        if (!condition) break
                        game.player()
                    }
                }
                else -> {
                    continue
                }
            }

        }
    }
}


class Game : Card() {
    companion object {
        val cardOnTable = mutableListOf<String>()
        val cardInHand = mutableListOf<String>()
        val comHand = mutableListOf<String>()
        val playerCards = mutableListOf<String>()
        val computerCards = mutableListOf<String>()
        val totalCards = mutableListOf<String>()
        val pointGiver = listOf("A", "10", "J", "Q", "K")
        var printScores = false
        var playerWin = false
        var playerPoints = 0
        var computerPoints = 0
    }


    fun initialCards() {
        if (cardDeck.isEmpty()) newCardDeck()
        val value = cardDeck.drop(cardDeck.size - 4).toMutableList()
        cardDeck.removeAll(value)
        cardOnTable.addAll(value)
        totalCards.addAll(value)
        println("Initial cards on the table: ${cardOnTable.joinToString(" ")}")
        println()
    }

    private fun deal(list: MutableList<String>) {
        cardDeck.shuffle()
        val value = cardDeck.subList(0, 6)
        list.addAll(value)
        cardDeck.removeAll(value)
    }

    fun player() {
        if (totalCards.size == 52) {
            condition = false
            if (cardOnTable.isEmpty()) {
                println("No cards on the table")
            } else {
                println("${cardOnTable.size} cards on the table, and the top card is ${cardOnTable.last()}")
            }
            endGamePoints()
            println("game over")

        } else {
            if (cardInHand.isEmpty()) {
                deal(cardInHand)
            }
            if (cardOnTable.isEmpty()) {
                println("No cards on the table")
            } else {
                println("${cardOnTable.size} cards on the table, and the top card is ${cardOnTable.last()}")
            }
            println("Cards in hand: " + cardInHand.mapIndexed { i, s -> "${i + 1})$s" }.joinToString(" "))
            checkInput()
            println()
        }

    }

    private fun checkInput() {
        while (true) {
            println("Choose a card to play (1-${cardInHand.size}):")
            val input = readln()
            if (input.all { it.isDigit() } && input.toInt() > 0 && input.toInt() <= cardInHand.size) {
                val value = cardInHand.removeAt(input.toInt() - 1)
                winner(value, "player")
                if (printScores) {
                    println("Player wins cards")
                    println("Score: Player $playerPoints - Computer $computerPoints")
                    println("Cards: Player ${playerCards.size} - Computer ${computerCards.size}")
                    cardOnTable.clear()
                    printScores = false
                } else {
                    cardOnTable.add(value)
                    totalCards.add(value)
                }
                break
            }
            if (input == "exit") {
                condition = false
                println("Game over")
                break
            }
        }
    }

    fun computer() {
        if (totalCards.size == 52) {
            condition = false
            if (cardOnTable.isEmpty()) {
                println("No cards on the table")
            } else {
                println("${cardOnTable.size} cards on the table, and the top card is ${cardOnTable.last()}")
            }
            endGamePoints()
            println("Game Over")
        } else {
            if (comHand.isEmpty()) {
                deal(comHand)
            }
            if (cardOnTable.isEmpty()) {
                println("No cards on the table")
            } else {
                println("${cardOnTable.size} cards on the table, and the top card is ${cardOnTable.last()}")
            }
            println(comHand.joinToString(" "))
            val element = computerAI()
            val value = comHand.removeAt(comHand.indexOf(element))
            winner(value, "computer")
            println("Computer plays $value")
            if (printScores) {
                println("Computer wins cards")
                println("Score: Player $playerPoints - Computer $computerPoints")
                println("Cards: Player ${playerCards.size} - Computer ${computerCards.size}")
                cardOnTable.clear()
                printScores = false
            } else {
                cardOnTable.add(value)
                totalCards.add(value)
            }
            println()
        }
    }

    private fun computerAI(): String {
        val candidateCards = mutableListOf<String>()
        val otherCards = mutableListOf<String>()

        if (cardOnTable.isNotEmpty()) {
            for (i in comHand) {
                if (cardOnTable.last().contains(i.first()) || cardOnTable.last().contains(i.last())) {
                    candidateCards.add(i)
                } else {
                    otherCards.add(i)
                }
            }
        }

        val value = if (candidateCards.isNotEmpty()) {
            handChecking(candidateCards)
        }else if (cardOnTable.isEmpty()){
            handChecking(comHand)
        } else {
            handChecking(otherCards)
        }
        return value
    }

    private fun handChecking(cards: MutableList<String>): String {
        val maxMatchSuit = mutableListOf<String>()
        val maxMatchSuit1 = mutableListOf<String>()
        val unMatchSuit = mutableListOf<String>()
        val matchedRanks = mutableListOf<String>()
        val unMatchRanks = mutableListOf<String>()

        cards
            .groupBy { it.last() }
            .forEach {
                if (it.value.size >= 3) {
                    maxMatchSuit.addAll(it.value)
                } else if (it.value.size == 2) {
                    maxMatchSuit1.addAll(it.value)
                } else {
                    unMatchSuit.addAll(it.value)
                }
            }

        if (maxMatchSuit.isEmpty() && maxMatchSuit1.isEmpty()) {
            cards
                .groupBy { it.first() }
                .forEach {
                    if (it.value.size > 1) {
                        matchedRanks.addAll(it.value)
                    } else {
                        unMatchRanks.addAll(it.value)
                    }
                }
        }

        val value: String = if (maxMatchSuit.isNotEmpty()) {
            maxMatchSuit.random()
        } else if (maxMatchSuit1.isNotEmpty()) {
            maxMatchSuit1.random()
        } else if (matchedRanks.isNotEmpty()) {
            matchedRanks.random()
        } else {
            unMatchRanks.random()
        }
        return value
    }

    private fun winner(value: String, who: String) {
        val newVal = mutableListOf<String>()
        newVal.add(value)
        if (cardOnTable.isNotEmpty()) {
            if (cardOnTable.last().contains(newVal[0].first()) || cardOnTable.last().contains(newVal[0].last())) {
                cardOnTable.add(value)
                totalCards.add(value)
                playerWin = if (who == "player"){
                    playerCards.addAll(cardOnTable)
                    true
                } else{
                    computerCards.addAll(cardOnTable)
                    false
                }
                points(who)
                printScores = true

            }
        }
    }

    private fun points(who: String) {
        cardOnTable.forEach { i ->
            pointGiver.forEach { j ->
                if (i.contains(j)) {
                    if (who == "player") {
                        playerPoints++
                    } else {
                        computerPoints++
                    }
                }
            }
        }
    }

    private fun endGamePoints() {
        if (playerWin) {
            points("player")
            playerCards.addAll(cardOnTable)
            playerCards.size
        } else {
            points("computer")
            computerCards.addAll(cardOnTable)
            println(computerCards.size)
        }
        if (playerCards.size > computerCards.size) {
            println("Score: Player ${playerPoints + 3} - Computer $computerPoints")
            println("Cards: Player ${playerCards.size} - Computer ${computerCards.size}")
        } else {
            println("Score: Player $playerPoints - Computer ${computerPoints + 3}")
            println("Cards: Player ${playerCards.size} - Computer ${computerCards.size}")
        }
    }
}

open class Card {

    companion object {

        var cardDeck: MutableList<String> = mutableListOf()
        var userCards: MutableList<String> = mutableListOf()


        fun newCardDeck() {
            cardDeck = SUITS.split(" ").flatMap { suit -> RANKS.split(" ").map { it + suit } }.toMutableList()
            cardDeck.shuffle()
        }

        fun getCards(number: Int) {
            userCards = cardDeck.subList(0, number).also { cardDeck = cardDeck.drop(number).toMutableList() }
        }
    }


    fun reset() {
        newCardDeck()
        println("Card deck is reset.")
    }

    fun shuffle() {
        cardDeck.shuffle()
        println("Card deck is shuffled.")
    }

    fun getVal(cardNo: Int) {
        if (cardNo <= cardDeck.size) {
            for (i in 1..cardNo) {
                getCards(cardNo)
            }
            println(userCards.joinToString(" "))
        } else {
            println("The remaining cards are insufficient to meet the request.")
        }

    }
}


