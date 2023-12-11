package day5

import utils.readTxt
import java.math.BigInteger

/*
--- Day 5: If You Give A Seed A Fertilizer ---
 */

// closest location that needs a seed
fun part1(): BigInteger {
    val path =
//        "day5/sample.txt"
        "day5/input.txt"
    val almanac = readTxt(path).filter { it != "" }.toMutableList()
    val regex = "\\d+".toRegex()
    val seedsToBePlanted = regex.findAll(almanac.removeAt(0))
    val almanacMap = parseAlmanac(almanac)

    /*
    destination range start, source range start, range length
     */
    var (seedWithLowestLocationNumber, lowestLocationNumber) = Pair("", 0.toBigInteger())
    for (seed in seedsToBePlanted) {
        val location = getLocation(seed.value, almanacMap)
        if (location!!.toBigInteger() < lowestLocationNumber || lowestLocationNumber == 0.toBigInteger()) {
            lowestLocationNumber = location.toBigInteger()
            seedWithLowestLocationNumber = seed.value
        }
    }

    println("part1 seed: $seedWithLowestLocationNumber")
    return lowestLocationNumber
}

private fun getLocation(
    seed: String,
    almanacMap: Map<String, List<String>>
): String? {
    val soil = getDestination(seed, almanacMap["seed-to-soil map:"]!!)
    val fertilizer = almanacMap["soil-to-fertilizer map:"]?.let { getDestination(soil, it) }
    val water = almanacMap["fertilizer-to-water map:"]?.let { getDestination(fertilizer!!, it) }
    val light = almanacMap["water-to-light map:"]?.let { getDestination(water!!, it) }
    val temperature = almanacMap["light-to-temperature map:"]?.let { getDestination(light!!, it) }
    val humidity = almanacMap["temperature-to-humidity map:"]?.let { getDestination(temperature!!, it) }
    return almanacMap["humidity-to-location map:"]?.let { getDestination(humidity!!, it) }
}

fun part2(): BigInteger {
    val path =
//        "day5/sample.txt"
        "day5/input.txt"
    val almanac = readTxt(path).filter { it != "" }.toMutableList()
    val regex = "\\d+".toRegex()
    val seedsToBePlanted = regex.findAll(almanac.removeAt(0))
    val almanacMap = parseAlmanac(almanac)

    var (seedWithLowestLocationNumber, lowestLocationNumber) = Pair("", 0.toBigInteger())
    for (i in 0..seedsToBePlanted.count()/2 step 2) {
        val start = seedsToBePlanted.elementAt(i).value.toBigInteger()
        val range = seedsToBePlanted.elementAt(i+1).value.toBigInteger()

        var seed = start
        var bucketRange = range

        while (true) {
            var lowestLocationSeed = ""
            var lowestLocation = 0.toBigInteger()

            // trial-error with bucket size to get the right answer
            val bucketSize = 100
            // split seed range to 10 bucket range
            val bucket = partitionToBuckets(bucketSize, bucketRange)

            var b = -1
            do {
                if (b >= 0) {
                    seed += bucket[b]
                }

                val location = getLocation(seed.toString(), almanacMap)
                if (location!!.toBigInteger() < lowestLocation || lowestLocation == 0.toBigInteger()) {
                    bucketRange = if (b == -1) 0.toBigInteger() else bucket[b]
                    lowestLocationSeed = seed.toString()
                    lowestLocation = location.toBigInteger()
                }

                b++
            } while (b < bucket.count())

            if (lowestLocation < lowestLocationNumber || lowestLocationNumber == 0.toBigInteger()) {
                seed = lowestLocationSeed.toBigInteger() - bucketRange

                seedWithLowestLocationNumber = lowestLocationSeed
                lowestLocationNumber = lowestLocation
            }

            if (bucketRange == 0.toBigInteger()) {
                bucketRange = bucket[0]
            }

            if (bucketRange == 1.toBigInteger()) {
                break
            }
        }
    }

    println("part2 seed: $seedWithLowestLocationNumber")
    return lowestLocationNumber
}

fun parseAlmanac(almanac: List<String>): Map<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    var currMap = ""
    for (el in almanac) {
        if (el.contains("map")) {
            currMap = el
            continue
        }

        val currElMap = map[currMap]?.toMutableList() ?: mutableListOf()
        currElMap.add(el)
        map += currMap to currElMap
    }

    return  map
}

fun getDestination(src: String, map: List<String>): String {
    /*
    dest, src, range
    */
    for (el in map) {
        val (destEl, srcEl, range) = el.split(" ")
        val isSeedInRange =
            src.toBigInteger() >= srcEl.toBigInteger()
                    &&
                    src.toBigInteger() <= srcEl.toBigInteger() + range.toBigInteger() - 1.toBigInteger()
        if (isSeedInRange) {
            return (src.toBigInteger() - srcEl.toBigInteger() + destEl.toBigInteger()).toString()
        }
    }

   return src
}

fun partitionToBuckets(bucketSize: Int, range: BigInteger): List<BigInteger> {
    val res = mutableListOf<BigInteger>()

    var iter = bucketSize
    val rangePerBucket = if (range / bucketSize.toBigInteger() < 1.toBigInteger()) {
        iter = range.toInt()
        1.toBigInteger()
    } else {
        range / bucketSize.toBigInteger()
    }
    val remainder = ((range.toBigDecimal() % bucketSize.toBigDecimal())).toBigInteger()

    var i = 1
    while (i <= iter) {
        if (i == iter && remainder > 0.toBigInteger()) {
            res.add(rangePerBucket+remainder-1.toBigInteger())
            break
        }
        res.add(rangePerBucket)
        i++
    }

    return res
}