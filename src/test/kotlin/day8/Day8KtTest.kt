package day8

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day8KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day8.part1()
        val expected = 20093
        Assertions.assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day8.part2()
        // LCM is 22_103_062_509_257
        val lcmVar = listOf(20659, 20093, 14999, 17263, 22357, 16697)
        Assertions.assertEquals(lcmVar, res)
    }
}