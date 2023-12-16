package day7

import utils.readTxt

/*
--- Day 7: Camel Cards ---
*/

/**
 * A hand consists of five cards labeled one of
 * A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2
 * Every hand is exactly one type. From strongest to weakest
 */

enum class CardLabel(val s: String) {
//    J("J"), // uncomment this for part2
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    T("T"),
//    J("J"), // uncomment this for part1
    Q("Q"),
    K("K"),
    A("A");

    companion object {
        fun fromString(value: String) = entries.first { it.s == value }
    }
}

// lowest to highest with ordinal
enum class HandType {
    HighCard,
    OnePair,
    TwoPair,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind
}

fun part1(): Long {
    val path =
//        "day7/sample.txt"
        "day7/input.txt"

    val camelCardsBid = readTxt(path)

    return totalWinnings(camelCardsBid)
}

private fun totalWinnings(camelCardsBid: List<String>, withWildcard: Boolean = false): Long {
    val handsType = mutableMapOf<String, HashMap<String, Int>>()
    for (hands in camelCardsBid) {
        val (hand, bid) = hands.split(" ")

        val (handType, newHand) = if (withWildcard) {
            getNewHand(hand)
        } else {
            val handType = getHandType(hand)
            Pair(handType, hand)
        }

        val handValue = getHandOrderingValue(hand)

        val bucket = handsType[handType.name] ?: HashMap()

        val key = "${handType.name}@$hand@$newHand:$handValue"
        bucket[key] = bid.toInt()

        handsType += handType.name to bucket
    }

    val sortedHandsType = handsType.toSortedMap(compareBy { enumValueOf<HandType>(it).ordinal })
    var sum: Long = 0
    var rank = 0
    for (label in sortedHandsType) {
        val rankedHand = label.value.toSortedMap(compareBy { it.split(":").last().toLong() })
        for (r in rankedHand) {
            rank++
            sum += (r.value * rank)
        }
    }
    return sum
}

fun part2(): Long {
    val path =
//        "day7/sample.txt"
        "day7/input.txt"

    val camelCardsBid = readTxt(path)

    return totalWinnings(camelCardsBid, true)
}

fun getHandType(hand: String): HandType {
    val mapCard = mutableMapOf<Char, Int>()

    for (card in hand) {
        val num = mapCard[card]?.plus(1) ?: 1
        mapCard += card to num
    }

    val handType = when (mapCard.count()) {
        1 -> HandType.FiveOfAKind
        2 -> {
            var handType: HandType = HandType.FourOfAKind
            mapCard.forEach {
                if (it.value == 3) handType = HandType.FullHouse
            }
            handType
        }
        3 -> {
            var handType = HandType.TwoPair
            mapCard.forEach {
                if (it.value == 3) handType = HandType.ThreeOfAKind
            }
            handType
        }
        4 -> HandType.OnePair
        else -> HandType.HighCard
    }

    return handType
}

fun getNewHand(hand: String): Pair<HandType, String> {
    var wildHand = hand
    var handType = HandType.HighCard

    if (hand.contains("J")) {
        val type = getHandType(hand)

        // This an edge case
        if (type == HandType.FiveOfAKind) {
            return Pair(HandType.FiveOfAKind, hand)
        }

        for (h in hand) {
            if (h == 'J') continue
            val tmpWildHand = hand.replace("J", h.toString())
            val tmpHandType = getHandType(tmpWildHand)
            if (tmpHandType.ordinal > handType.ordinal) {
                handType = tmpHandType
                wildHand = tmpWildHand
            }
        }
    } else {
        val type = getHandType(hand)
        return Pair(type, hand)
    }

    return Pair(handType, wildHand)
}

fun getHandOrderingValue(hand: String): String {
    var res = ""
    for (card in hand) {
        val num = 11 + CardLabel.fromString(card.toString()).ordinal
        res += num.toString()
    }
    return res
}

fun calculateSecondOrderingRule(hands1: String, hands2: String): String {
    for (i in hands1.indices) {
        val card1 = CardLabel.fromString(hands1[i].toString())
        val card2 = CardLabel.fromString(hands2[i].toString())

        if (card1.ordinal > card2.ordinal) return hands1

        if (card2.ordinal > card1.ordinal) return hands2
    }
    return hands1
}