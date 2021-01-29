package com.example.other

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        OtherModuleNameSaver.putModuleName("asdfasf")
        println(OtherModuleNameSaver.getModuleName())
    }
}