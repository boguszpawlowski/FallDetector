package com.example.bpawlowski.falldetector

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val string = "GAGTTGAAAATATTGCGGCCGCTGGTAATGATAACATTGCGGCATTTGCTACACCGAGGCGTCGGA"

        val result = string.map { map[it.toString()] }
        println(result)
    }

    val map = mapOf(
        "G" to "01",
        "A" to "00",
        "C" to "10",
        "T" to "11"
    )
}
