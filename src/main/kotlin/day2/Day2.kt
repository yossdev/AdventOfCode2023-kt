package day2

import utils.readTxt

/*
--- Day 2: Cube Conundrum ---
 */

data class GameConfig(
    var red: Int,
    var green: Int,
    var blue: Int,
)

fun part1(): Int {
    /* Cubes configuration in the Bag
    - 12 red
    - 13 green
    - 14 blue
     */
    val games = readTxt("day2/input.txt")

    val sumOfGameIds = games.fold(0) { acc, s ->
        val gameConfig = GameConfig(12, 13, 14)
        val (possible, id) = isGamePossible(s) { num, color ->
            when (color) {
                "red" -> num.toInt() > gameConfig.red
                "green" -> num.toInt() > gameConfig.green
                "blue" -> num.toInt() > gameConfig.blue
                else -> throw IllegalArgumentException()
            }
        }

        if (possible) acc + id else acc
    }

    return sumOfGameIds
}

fun part2(): Int {
    val games = readTxt("day2/input.txt")

    val sumOfPowerOfLeastPossibleGameConfig = games.fold(0) { acc, s ->
        val leastPossibleGameConfig = GameConfig(0,0,0)
        isGamePossible(s) { num, color ->
            when (color) {
                "red" -> {
                    if (num.toInt() > leastPossibleGameConfig.red)
                        leastPossibleGameConfig.red = num.toInt()
                    false
                }
                "green" -> {
                    if (num.toInt() > leastPossibleGameConfig.green)
                        leastPossibleGameConfig.green = num.toInt()
                    false
                }
                "blue" -> {
                    if (num.toInt() > leastPossibleGameConfig.blue)
                        leastPossibleGameConfig.blue = num.toInt()
                    false
                }
                else -> throw IllegalArgumentException()
            }
        }
        acc + (leastPossibleGameConfig.red * leastPossibleGameConfig.green * leastPossibleGameConfig.blue)
    }

    return sumOfPowerOfLeastPossibleGameConfig
}

fun isGamePossible(
    game: String,
    rules: (num: String, color: String) -> Boolean
): Pair<Boolean, Int> {
    val (showedCubes, gameId) = gameData(game)

    for (setOfCubes in showedCubes) {
        for (cube in setOfCubes.split(",")) {
            val (num, color) = cube.trim().split(" ")
            if (rules(num, color)) return Pair(false, gameId)
        }
    }

    return Pair(true, gameId)
}

private fun gameData(game: String): Pair<List<String>, Int> {
    val data = game.split(":")
    val showedCubes = data.last().split(";")
    val gameId = data.first().split(" ").last().toInt()
    return Pair(showedCubes, gameId)
}