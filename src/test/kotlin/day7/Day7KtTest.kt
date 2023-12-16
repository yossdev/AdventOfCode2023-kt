package day7

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day7KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day7.part1()
        val expected: Long = 246795406
        Assertions.assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day7.part2()
        val expected: Long = 249356515
        Assertions.assertEquals(expected, res)
    }
}