package com.nagopy.android.bashfulclock.domain.japaneseera

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(JUnitPlatform::class)
class JapaneseEraSpec : Spek({
    val je = JapaneseEra(1989, "平成", "H")

    describe("平成31年") {
        given("2019/01/01") {
            val date = SimpleDateFormat("yyyy/MM/dd").parse("2019/01/01")
            on("formatLong") {
                val result = je.formatLong(date)
                it("should be 平成31年") {
                    Assert.assertEquals("平成31年", result)
                }
            }
            on("formatShort") {
                val result = je.formatShort(date)
                it("should be H31") {
                    Assert.assertEquals("H31", result)
                }
            }
        }
    }
    describe("平成元年") {
        given("1089/12/31") {
            val date = SimpleDateFormat("yyyy/MM/dd").parse("1989/12/31")
            on("formatLong") {
                val result = je.formatLong(date)
                it("should be 平成元年") {
                    Assert.assertEquals("平成元年", result)
                }
            }
            on("formatShort") {
                val result = je.formatShort(date)
                it("should be H1") {
                    Assert.assertEquals("H1", result)
                }
            }
        }
    }

})
