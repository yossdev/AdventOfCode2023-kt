package day1

import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

/*
--- Day 1: Trebuchet?! ---
*/

fun part1(): Int {
   val calibrationDocs = File("src/main/kotlin/day1/part1.txt")
      .readLines()

   val sumOfCalibrationValue = calibrationDocs.fold(0) { acc, s ->
      val num = calibrationValue(s)
      acc + num
   }
   return sumOfCalibrationValue
}

// Divide & Conquer
fun calibrationValue(s: String): Int {
   val len = s.length
   val pivot: Int = (len / 2.0f).roundToInt()

   var x = ""
   var y = ""

   var x1 = 0
   var y1 = 0

   for (i in 0..<pivot) {
      if (x.isNotEmpty() && y.isNotEmpty()) {
         return "$x$y".toInt()
      }

      try {
         if (x.isEmpty()) {
            x += s[i].digitToInt()
         }
         x1 = s[i].digitToInt()
      } catch (_: Exception) {}

      try {
         if (y.isEmpty()) {
            y += s[len - 1 - i].digitToInt()
         }
         y1 = s[len - 1 - i].digitToInt()
      } catch (_: Exception) {}
   }

   if (x.isNotEmpty() && y.isNotEmpty()) {
      return "$x$y".toInt()
   }

   if (x.isEmpty()) {
      return "$y1$y".toInt()
   }

   return "$x$x1".toInt()
}

fun part2(): Int {
   val calibrationDocs = File("src/main/kotlin/day1/part2.txt")
      .readLines()

   val sumOfCalibrationValue = calibrationDocs.fold(0) { acc, s ->
      val num = parseDigit(s)
      acc + num
   }

   return sumOfCalibrationValue
}

enum class Digit(val num: Int) {
   one(1),
   two(2),
   three(3),
   four(4),
   five(5),
   six(6),
   seven(7),
   eight(8),
   nine(9)
}

fun parseDigit(s: String): Int {
   // one, two, six
   var threeLetterNum: String
   // four, five, nine
   var fourLetterNum: String
   // three, seven, eight
   var fiveLetterNum: String

   var x = ""
   var y = ""

   val num = calibrationValue(s)

   val len = s.length
   val iter = s.length - 2
   /*
   IMPROVEMENT:
   - Remove succeed strNum
   -
    */
   for (i in 1..iter) {
      if (i+2 <= len) {
         threeLetterNum = s.substring(i-1, i+2)
         try {
            Digit.valueOf(threeLetterNum).num
            if (x.isEmpty()) {
               x += threeLetterNum
               y = threeLetterNum
               continue
            }

            y = threeLetterNum
         } catch (_: IllegalArgumentException) {}
      }

      if (i+3 <= len) {
         fourLetterNum = s.substring(i-1, i+3)
         try {
            Digit.valueOf(fourLetterNum).num
            if (x.isEmpty()) {
               x += fourLetterNum
               y = fourLetterNum
               continue
            }

            y = fourLetterNum
         } catch (_: IllegalArgumentException) {}
      }

      if (i+4 <= len) {
         fiveLetterNum = s.substring(i-1, i+4)
         try {
            Digit.valueOf(fiveLetterNum).num
            if (x.isEmpty()) {
               x += fiveLetterNum
               y = fiveLetterNum
               continue
            }

            y = fiveLetterNum
         } catch (_: IllegalArgumentException) {}
      }
   }

   if (x.isEmpty() && y.isEmpty()) return num

   try {
      x = if (s.split(x).first().contains(num.toString().first())) {
         num.toString().substring(0,1)
      } else {
         Digit.valueOf(x).num.toString()
      }

      y = if (s.split(y).last().contains(num.toString().last())) {
         num.toString().substring(1)
      } else {
         Digit.valueOf(y).num.toString()
      }
   } catch (_: Exception) {
     return num
   }

   return "$x$y".toInt()
}