package day8

import utils.readTxt

data class Network(val left: String, val right: String)

fun part1(): Int {
    val path =
//        "day8/sample.txt"
        "day8/input.txt"

    val documents = readTxt(path)
    val instructions = documents[0]
    val maps = getParsedMaps(documents)

    val startAt = listOf("AAA")
    val destination = listOf("ZZZ")

    return calculateSteps(instructions, maps, startAt[0], destination[0])
}

fun part2(): List<Int> {
    val path =
//        "day8/sample.txt"
        "day8/input.txt"

    val documents = readTxt(path)
    val instructions = documents[0]
    val maps = getParsedMaps(documents)

    val regexStartAt = "[0-9A-Z]{2}A".toRegex()

    val startAt = mutableListOf<String>()
    regexStartAt.findAll(maps.keys.toString()).forEach { startAt.add(it.value) }

    // get the lcm (Least common multiple) variable
    val lcm = mutableListOf<Int>()
    for (i in 0..startAt.lastIndex) {
        val res = calculateSteps(instructions, maps, startAt[i], "Z", true)
        lcm.add(res)
    }
    // then calculate the LCM number
    // the answer is 22_103_062_509_257
    return lcm
}

fun getParsedMaps(docs: List<String>): Map<String, Network> {
    val maps = mutableMapOf<String, Network>()

    val regex = "[0-9A-Z]+".toRegex()
    for (i in 2..docs.lastIndex) {
        val (name, left, right) = regex.findAll(docs[i]).toList()
        val node = Network(left.value, right.value)
        maps += name.value to node
    }

    return maps
}

fun calculateSteps(
    instructions: String,
    maps: Map<String, Network>,
    startAt: String,
    destination: String,
    isGhostPath: Boolean = false
): Int {
    var pointer = 0
    var node = startAt
    var steps = 0

    while (true) {
        if (node == destination
            || (isGhostPath && node[2].toString() == destination)) {
            break
        }
        steps++

        val instruction = instructions[pointer]
        val network = maps[node]
        if (network != null) {
            node = if (instruction == 'L') {
                network.left
            } else {
                network.right
            }
        }

        pointer = if (pointer == instructions.lastIndex) 0 else ++pointer
    }

    return steps
}