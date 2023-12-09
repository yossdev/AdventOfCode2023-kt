package day3

import utils.readTxt

/*
--- Day 3: Gear Ratios ---
*/

fun part1(): Int {
   val path = "day3/input.txt"
   val engineSchematic = readTxt(path).map { it }

   return readEngineSchematic(engineSchematic).first
}

fun part2(): Int {
   val path = "day3/input.txt"
   val engineSchematic = readTxt(path).map { it }
   val gearRatio = readEngineSchematic(engineSchematic).second

   var sumOfGearRatio = 0
   gearRatio.forEach {
      if (it.value.size == 2) {
         sumOfGearRatio += it.value[0] * it.value[1]
      }
   }

   return sumOfGearRatio
}

fun isGear(char: Char) = char == '*'

fun isNumber(char: Char) = char.digitToIntOrNull() != null

fun isSymbol(
   schematic: List<String>,
   y: Int,
   x: Int,
   dir: Pair<Int, Int>
): Pair<Boolean?, Pair<Int, Int>> {
   val dy = y + dir.first
   val dx = x + dir.second
   // check if it's inside the bound
   if (dy >= 0
      && dx >= 0
      && dy <= schematic.lastIndex
      && dx <= schematic[y].lastIndex
      ) {
      val isSymbol = !isNumber(schematic[dy][dx]) && schematic[dy][dx] != '.'
      val coord = Pair(dy, dx)
      return Pair(isSymbol, coord)
   }

   return Pair(null, Pair(-1, -1))
}

fun readEngineSchematic(schematic: List<String>): Pair<Int, Map<String, List<Int>>> {
   val dirs = listOf(
      listOf(-1,-1), listOf(-1,0), listOf(-1,1),
      listOf(0,-1), listOf(0,1),
      listOf(1,-1), listOf(1,0), listOf(1,1)
   )

   // part1
   var currNum = ""
   var check = false
   var isNum: Boolean
   var sum = 0

   // part2
   var isGear = false
   var gearCoord: Pair<Int, Int>? = null
   val gearRatio = mutableMapOf<String, List<Int>>()

   for (y in 0..schematic.lastIndex) {
      for (x in 0..schematic[y].lastIndex) {
         val char = schematic[y][x]
         isNum = isNumber(char)

         if (!isNum && !check) {
            currNum = ""
            check = false
         }

         if (isNum) {
            currNum += char
            if (!check) {
               check = dirs.fold(false) { acc, it ->
                  val checkRes = isSymbol(schematic, y, x, Pair(it.first(), it.last()))
                  val isSymbol = checkRes.first ?: false
                  val coord = checkRes.second

                  // part2
                  if (isSymbol) {
                     isGear = isGear(schematic[coord.first][coord.second])
                     if (isGear) {
                        gearCoord = Pair(coord.first, coord.second)
                     }
                  }

                  acc || isSymbol
               }
            }
         }

         if (!isNum && check) {
            // part1
            sum += currNum.toInt()

            // part2
            if (isGear) {
               val partList: List<Int> = gearRatio[gearCoord.toString()]?.toMutableList() ?: mutableListOf()
               partList.addFirst(currNum.toInt())
               gearRatio += gearCoord.toString() to partList
            }

            currNum = ""
            check = false
         }
      }
   }

   return Pair(sum, gearRatio)
}