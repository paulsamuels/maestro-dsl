package com.paul.samuels.maestro.dsl

import com.paul.samuels.maestro.dsl.types.Direction
import com.paul.samuels.maestro.dsl.types.Key
import com.paul.samuels.maestro.dsl.types.Platform
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class MaestroComposeTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    fun `renders commands to yaml`(@Suppress("UNUSED_PARAMETER") name: String, expected: String, body: ScriptScope.() -> Unit) {
        val document = maestroCompose("com.example", body)

        assertEquals("appId: com.example\n---\n$expected\n", document)
    }

    companion object {
        @JvmStatic
        fun fixtures() = listOf(
            fixtureOf(
                """
                addMedia:
                  - "image1"
                  - "image2"
                """.trimIndent(),
            ) { addMedia("image1", "image2") },

            fixtureOf(
                """
                assertNotVisible:
                    index: 4
                    text: "label"
                - assertNotVisible:
                    id: "my-id"
                - assertNotVisible:
                    checked: false
                    enabled: true
                    focused: false
                    id: "my-id"
                    optional: true
                    selected: false
                """.trimIndent(),
            ) {
                assertNotVisible(text("label", index = 4))
                assertNotVisible(id("my-id"))
                assertNotVisible(id("my-id", checked = false, enabled = true, focused = false, optional = true, selected = false))
            },

            fixtureOf(
                """
                assertTrue: "${"$"}{output.viewA == maestro.copiedText}"
                """.trimIndent(),
            ) {
                assertTrue("output.viewA == maestro.copiedText")
            },

            fixtureOf(
                """
                assertVisible:
                    index: 4
                    text: "label"
                - assertVisible:
                    id: "my-id"
                - assertVisible:
                    checked: false
                    enabled: true
                    focused: false
                    id: "my-id"
                    optional: true
                    selected: false
                """.trimIndent(),
            ) {
                assertVisible(text("label", index = 4))
                assertVisible(id("my-id"))
                assertVisible(id("my-id", checked = false, enabled = true, focused = false, optional = true, selected = false))
            },

            fixtureOf("back") { back() },
            fixtureOf("clearKeychain") { clearKeychain() },
            fixtureOf("clearState") { clearState() },
            fixtureOf("clearState: \"com.other.app\"") { clearState("com.other.app") },

            fixtureOf(
                """
                copyTextFrom:
                    index: 4
                    text: "label"
                - copyTextFrom:
                    id: "my-id"
                """.trimIndent(),
            ) {
                copyTextFrom(text("label", index = 4))
                copyTextFrom(id("my-id"))
            },

            fixtureOf(
                """
                doubleTapOn:
                    index: 4
                    text: "label"
                - doubleTapOn:
                    id: "my-id"
                """.trimIndent(),
            ) {
                doubleTapOn(text("label", index = 4))
                doubleTapOn(id("my-id"))
            },

            fixtureOf("eraseText") { eraseText() },
            fixtureOf("eraseText: 200") { eraseText(200) },
            fixtureOf("evalScript: \"${'$'}{output.myFlow = MY_NAME.toUpperCase()}\"") { evalScript("output.myFlow = MY_NAME.toUpperCase()") },

            fixtureOf(
                """
                extendedWaitUntil:
                    notVisible:
                      text: "label"
                    timeout: 10000
                - extendedWaitUntil:
                    notVisible:
                      id: "my-id"
                    timeout: 10000
                """.trimIndent(),
            ) {
                extendedWaitUntilNotVisible(text("label"), 10000)
                extendedWaitUntilNotVisible(id("my-id"), 10000)
            },

            fixtureOf(
                """
                extendedWaitUntil:
                    visible:
                      text: "label"
                    timeout: 10000
                - extendedWaitUntil:
                    visible:
                      id: "my-id"
                    timeout: 10000
                """.trimIndent(),
            ) {
                extendedWaitUntilVisible(text("label"), 10000)
                extendedWaitUntilVisible(id("my-id"), 10000)
            },

            fixtureOf("hideKeyboard") { hideKeyboard() },
            fixtureOf("inputText: \"example\"") { inputText("example") },
            fixtureOf("inputRandomEmail") { inputRandomEmail() },
            fixtureOf("inputRandomNumber") { inputRandomNumber() },
            fixtureOf("inputRandomPersonName") { inputRandomPersonName() },
            fixtureOf("inputRandomText") { inputRandomText() },

            fixtureOf(
                """
                launchApp:
                    appId: "com.example.app"
                    arguments:
                      example: true
                      other: "name"
                    clearKeychain: true
                    clearState: true
                    permissions:
                      all: "deny"
                    stopApp: false
                """.trimIndent(),
            ) {
                launchApp(
                    "com.example.app",
                    clearState = true,
                    clearKeychain = true,
                    stopApp = false,
                    permissions = mapOf(
                        "all" to "deny",
                    ),
                    arguments = mapOf(
                        "example" to true,
                        "other" to "name",
                    ),
                )
            },

            fixtureOf(
                """
                openLink:
                    link: "https://example.com"
                - openLink:
                    link: "https://example.com"
                    autoVerify: false
                - openLink:
                    link: "https://example.com"
                    browser: false
                """.trimIndent(),
            ) {
                openLink("https://example.com")
                openLink("https://example.com", autoVerify = false)
                openLink("https://example.com", browser = false)
            },

            fixtureOf("pasteText") { pasteText() },

            fixtureOf(
                """
                pressKey: "back"
                - pressKey: "backspace"
                - pressKey: "enter"
                - pressKey: "home"
                - pressKey: "lock"
                - pressKey: "power"
                - pressKey: "volume down"
                - pressKey: "volume up"
                """.trimIndent(),
            ) {
                pressKey(Key.Back)
                pressKey(Key.Backspace)
                pressKey(Key.Enter)
                pressKey(Key.Home)
                pressKey(Key.Lock)
                pressKey(Key.Power)
                pressKey(Key.VolumeDown)
                pressKey(Key.VolumeUp)
            },

            fixtureOf(
                """
                repeat:
                    while:
                      platform: "iOS"
                    commands:
                    - "stopApp"
                """.trimIndent(),
            ) {
                repeat(platform(Platform.iOS)) {
                    stopApp()
                }
            },

            fixtureOf(
                """
                runFlow:
                    when:
                      platform: "iOS"
                    commands:
                    - "stopApp"
                """.trimIndent(),
            ) {
                runFlow(platform(Platform.iOS)) {
                    stopApp()
                }
            },

            fixtureOf("scroll") { scroll() },

            fixtureOf(
                """
                scrollUntilVisible:
                    centerElement: false
                    direction: "Down"
                    element:
                      id: "viewId"
                    speed: 40
                    timeout: 50000
                    visibilityPercentage: 100
                """.trimIndent(),
            ) {
                scrollUntilVisible(
                    selector = id("viewId"),
                    direction = Direction.Down,
                    timeout = 50000,
                    speed = 40,
                    visibilityPercentage = 100,
                    centerElement = false,
                )
            },

            fixtureOf(
                """
                setLocation:
                    latitude: "52.3599976"
                    longitude: "4.8830301"
                """.trimIndent(),
            ) { setLocation("52.3599976", "4.8830301") },

            fixtureOf("startRecording: \"myRecording\"") { startRecording("myRecording") },
            fixtureOf("stopApp") { stopApp() },
            fixtureOf("stopApp: \"other.app\"") { stopApp("other.app") },
            fixtureOf("stopRecording") { stopRecording() },

            fixtureOf(
                """
                swipe:
                    start: "90%, 50%"
                    end: "10%, 50%"
                - swipe:
                    direction: "LEFT"
                    duration: 2000
                """.trimIndent(),
            ) {
                swipe(startX = "90%", startY = "50%", endX = "10%", endY = "50%")
                swipe(Direction.Left, 2000)
            },

            fixtureOf("takeScreenshot: \"MainScreen\"") { takeScreenshot("MainScreen") },

            fixtureOf(
                """
                tapOn:
                    index: 4
                    text: "label"
                - tapOn:
                    id: "my-id"
                """.trimIndent(),
            ) {
                tapOn(text("label", index = 4))
                tapOn(id("my-id"))
            },

            fixtureOf("waitForAnimationToEnd") { waitForAnimationToEnd() },
            fixtureOf("waitForAnimationToEnd: 5000") { waitForAnimationToEnd(5000) },
        )

        private fun fixtureOf(expected: String, body: ScriptScope.() -> Unit): Arguments? {
            val items = expected.split(":", limit = 2)
            val (name, formattedExpected) = if (items.size == 2) {
                Pair("${items[0]} with arguments", "- $expected")
            } else {
                Pair(items[0], "- \"${items[0]}\"")
            }

            return Arguments.of(name, formattedExpected, body)
        }
    }
}
