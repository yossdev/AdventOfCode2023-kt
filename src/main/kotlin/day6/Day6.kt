package day6

import utils.readTxt
import java.math.BigInteger

fun part1(): BigInteger {
    val path =
//        "day6/sample.txt"
        "day6/input.txt"
    val raceHistory = readTxt(path)
    val regex = "(\\d+)".toRegex()
    val time = regex.findAll(raceHistory[0])
    val distance = regex.findAll(raceHistory[1])

    var res = 1.toBigInteger()
    for (i in 0..<time.count()) {
        val numberOfWaysToWin = calculateNumberOfPossibleWin(
            time.elementAt(i).value.toBigInteger(),
            distance.elementAt(i).value.toBigInteger()
        )

        res *= numberOfWaysToWin
    }

    return res
}

private fun calculateNumberOfPossibleWin(
    time: BigInteger,
    distance: BigInteger
): BigInteger {
    var numberOfWaysToWin = time
    var isLowerLimitFound = false
    var isUpperLimitFound = false

    var start = 1.toBigInteger()
    var end = time - 1.toBigInteger()
    while (!isLowerLimitFound && !isUpperLimitFound) {
        if (!isLowerLimitFound) {
            val remainingTime = time - start
            val boatDistance = start * remainingTime
            if (boatDistance <= distance) {
                start++
                numberOfWaysToWin--
            } else {
                isLowerLimitFound = true
            }
        }

        if (!isUpperLimitFound) {
            val remainingTime = time - end
            val boatDistance = end * remainingTime
            if (boatDistance <= distance) {
                end++
                numberOfWaysToWin--
            } else {
                isUpperLimitFound = true
            }
        }
    }
    return numberOfWaysToWin
}

fun part2(): BigInteger {
    val path =
//        "day6/sample.txt"
        "day6/input.txt"
    val raceHistory = readTxt(path)
    val regex = "(\\d+)".toRegex()
    val time = regex.findAll(raceHistory[0]).joinToString("") { it.value }
    val distance = regex.findAll(raceHistory[1]).joinToString("") { it.value }

    return calculateNumberOfPossibleWin(time.toBigInteger(), distance.toBigInteger())
}