package org.nunocky.android_patternmatching

import org.junit.Assert.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object {
        const val TAG = "ExampleUnitTest"
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     * 文字列 "[result] key:0x55 val:0xaa" から 0x55, 0xaaを(数値で)取得したい。
     */
    @Test
    fun testRegExp() {

        val targetStr = "[result] key:0x55 val:0xaa"

        runCatching { parseResultText(targetStr) }
            .fold(
                onSuccess = {
                    val key = it.first
                    val value = it.second

                    assertEquals(0x55.toByte(), key)
                    assertEquals(0xaa.toByte(), value)
                },
                onFailure = {
                    it.printStackTrace()
                    fail()
                }
            )
    }

    /**
     * 書式が異なるので失敗する
     */
    @Test
    fun testRegExpFail1() {
        val targetStr = "[KeyValue] key:0x55 val:0xaa."

        runCatching { parseResultText(targetStr) }
            .fold(
                onSuccess = {
                    // こちらを通ってはならない
                    fail()
                },
                onFailure = {
                    // こちらに来るのが正しい
                    //it.printStackTrace()
                }

            )

    }

    /**
     * 末尾に余計な文字列がついているので失敗する
     */
    @Test
    fun testRegExpFail2() {
        val targetStr = "[result] key:0x55 val:0xaa.asdfg"

        val kv: Result<Pair<Byte, Byte>> = runCatching { parseResultText(targetStr) }
        assertTrue(kv.isFailure)

        //kv.exceptionOrNull()?.printStackTrace()
    }
}