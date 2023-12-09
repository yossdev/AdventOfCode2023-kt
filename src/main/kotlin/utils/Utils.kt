package utils

import java.io.File

fun readTxt(path: String) = File("src/main/kotlin/$path").readLines()