package day4

import utils.readTxt

/*
--- Day 4: Scratchcards ---
*/

fun part1(): Int {
    val path = "day4/input.txt"
    val scratchCards = readTxt(path)

    var total = 0
    for (card in scratchCards) {
        val scratchCard = card.split(":")
        val (winnings, numbers) = scratchCard[1].split("|")

        var points = 0
        val nums = numbers.trim().split(" ")
        for (number in nums) {
            val isWinningNum = winnings.trim().split(" ").contains(number)

            if (!isWinningNum || number.isEmpty()) {
                continue
            }

            if (points * 2 != 0) points *= 2 else points++
        }

        total += points
    }

    return total
}

fun part2(): Int {
    return winMoreScratchcard()
}

fun winMoreScratchcard(): Int {
    val path = "day4/input.txt"
    val scratchCards = readTxt(path)

    val cache = mutableMapOf<String, List<Int>>()
    val cardInstances = mutableMapOf<String, Int>()

    var isWinningNum: Boolean
    val copies = mutableListOf<MutableList<Int>>()
    var origin = 0
    var i = 0
    while (true) {
        val (card, numbers) = scratchCards[i].split(":")
        val (winnings, myNumbers) = numbers.split("|")

        // register card instance
        val instanceNum = cardInstances[card] ?: 0
        cardInstances += card to instanceNum + 1

        var match = i
        var winningCopies = mutableListOf<Int>()
        val nums = myNumbers.trim().split(" ")

        if (cache.containsKey(card)) {
           winningCopies = cache[card]!!.toMutableList()
        } else {
            for (number in nums) {
                isWinningNum = winnings.trim().split(" ").contains(number)

                if (!isWinningNum || number.isEmpty()) {
                    continue
                }

                match++
                if(match <= scratchCards.lastIndex) {
                    winningCopies.add(match)
                }
            }
            cache += card to winningCopies.toList()
        }

        if (winningCopies.isNotEmpty()) {
            copies.add(0, winningCopies)
        } else {
            while (true) {
                if (copies.isEmpty()) {
                    break
                }
                val lastIn = copies.first().count()
                if (lastIn > 1) {
                    copies.first().removeAt(0)
                    break
                } else {
                    copies.removeAt(0)
                }
            }
        }

        if (origin == scratchCards.lastIndex) {
            break
        }

        i = if (copies.isNotEmpty()) {
            copies[0][0]
        } else {
            ++origin
        }
    }

    var total = 0
    cardInstances.forEach {
        total += it.value
    }

    return total
}
