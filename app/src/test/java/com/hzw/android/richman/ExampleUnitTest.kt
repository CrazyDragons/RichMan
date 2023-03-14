package com.hzw.android.richman

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {

        var  x = 100

        println("开始")

        for (i in 1 .. 10) {
            if (Math.random() > 0.6) {
                x -= (10 * Math.random()).toInt()
            }else {
                x += (10 * Math.random()).toInt()
            }
            println(x)
        }

    }
}