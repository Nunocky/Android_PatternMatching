package org.nunocky.android_patternmatching

/**
 * 文字列マッチングの実験
 */
fun parseResultText(targetStr: String): Pair<Byte, Byte> {
    val regex = Regex(pattern = "\\[result\\] key:0x(..) val:0x(..)$")

    regex.matchEntire(targetStr)?.destructured?.let { (k, v) ->
        val key = k.toInt(16).toByte()
        val value = v.toInt(16).toByte()
        return Pair(key, value)
    }

    throw Exception()
}
