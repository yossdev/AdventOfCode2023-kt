package day3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day3KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day3.part1()
        val expected = 521601
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day3.part2()
        val expected = 80694070
        assertEquals(expected, res)
    }
}