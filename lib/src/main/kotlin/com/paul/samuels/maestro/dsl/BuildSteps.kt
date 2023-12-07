package com.paul.samuels.maestro.dsl

import com.paul.samuels.maestro.dsl.annotations.LibraryExtensionPoint
import com.paul.samuels.maestro.dsl.types.Direction
import com.paul.samuels.maestro.dsl.types.Key

@OptIn(LibraryExtensionPoint::class)
internal fun buildSteps(configure: ScriptScope.() -> Unit): List<Any> = mutableListOf<Any>().also { steps ->
    object : ScriptScope {
        override fun add(name: String, data: Any?) {
            if (data == null) {
                steps.add(name)
            } else {
                steps.add(mapOf(name to data))
            }
        }

        override fun addMedia(vararg path: String) {
            add("addMedia", path.toList())
        }

        override fun assertNotVisible(selector: ScriptScope.Selector) {
            assert("assertNotVisible", selector)
        }

        override fun assertTrue(script: String) {
            add("assertTrue", "${"$"}{$script}")
        }

        override fun assertVisible(selector: ScriptScope.Selector) {
            assert("assertVisible", selector)
        }

        fun assert(name: String, selector: ScriptScope.Selector) {
            add(name, selector.data)
        }

        override fun back() {
            add("back", null)
        }

        override fun clearKeychain() {
            add("clearKeychain", null)
        }

        override fun clearState(bundleId: String?) {
            if (bundleId == null) {
                steps.add("clearState")
            } else {
                steps.add(mapOf("clearState" to bundleId))
            }
        }

        override fun copyTextFrom(selector: ScriptScope.Selector) {
            add("copyTextFrom", selector.data)
        }

        override fun doubleTapOn(
            selector: ScriptScope.Selector,
            repeat: Int?,
            delay: Int?,
            waitToSettleTimeoutMs: Int?,
        ) {
            tapOn("doubleTapOn", selector, repeat, delay, waitToSettleTimeoutMs)
        }

        override fun eraseText(characterCount: Int?) {
            add("eraseText", characterCount)
        }

        override fun evalScript(script: String) {
            add("evalScript", "${"$"}{$script}")
        }

        override fun extendedWaitUntilNotVisible(selector: ScriptScope.Selector, timeout: Int?) {
            extendedWaitUntilVisible("notVisible", selector, timeout)
        }

        override fun extendedWaitUntilVisible(selector: ScriptScope.Selector, timeout: Int?) {
            extendedWaitUntilVisible("visible", selector, timeout)
        }

        fun extendedWaitUntilVisible(visibility: String, selector: ScriptScope.Selector, timeout: Int?) {
            add(
                "extendedWaitUntil",
                mapOf(
                    visibility to selector.data,
                    "timeout" to timeout,
                ),
            )
        }

        override fun hideKeyboard() {
            add("hideKeyboard", null)
        }

        override fun inputText(text: String) {
            add("inputText", text)
        }

        override fun inputRandomEmail() {
            add("inputRandomEmail", null)
        }

        override fun inputRandomNumber() {
            add("inputRandomNumber", null)
        }

        override fun inputRandomPersonName() {
            add("inputRandomPersonName", null)
        }

        override fun inputRandomText() {
            add("inputRandomText", null)
        }

        override fun launchApp(
            bundleId: String,
            arguments: Map<String, Any>?,
            clearKeychain: Boolean?,
            clearState: Boolean?,
            permissions: Map<String, String>?,
            stopApp: Boolean?,
        ) {
            add(
                "launchApp",
                mapOf(
                    "appId" to bundleId,
                    "arguments" to arguments,
                    "clearKeychain" to clearKeychain,
                    "clearState" to clearState,
                    "permissions" to permissions,
                    "stopApp" to stopApp,
                ),
            )
        }

        override fun longPressOn(
            selector: ScriptScope.Selector,
            repeat: Int?,
            delay: Int?,
            waitToSettleTimeoutMs: Int?,
        ) {
            tapOn("longPressOn", selector, repeat, delay, waitToSettleTimeoutMs)
        }

        override fun openLink(url: String, autoVerify: Boolean?, browser: Boolean?) {
            add(
                "openLink",
                mapOf(
                    "link" to url,
                    "autoVerify" to autoVerify,
                    "browser" to browser,
                ),
            )
        }

        override fun pasteText() {
            add("pasteText", null)
        }

        override fun pressKey(key: Key) {
            add("pressKey", key.value)
        }

        override fun repeat(`while`: ScriptScope.Condition, configure: ScriptScope.() -> Unit) {
            add(
                "repeat",
                mapOf(
                    "while" to `while`.data,
                    "commands" to buildSteps(configure),
                ),
            )
        }

        override fun runFlow(condition: ScriptScope.Condition, configure: ScriptScope.() -> Unit) {
            add(
                "runFlow",
                mapOf(
                    "when" to condition.data,
                    "commands" to buildSteps(configure),
                ),
            )
        }

        override fun scrollUntilVisible(
            selector: ScriptScope.Selector,
            direction: Direction?,
            timeout: Int?,
            speed: Int?,
            visibilityPercentage: Int?,
            centerElement: Boolean?,
        ) {
            add(
                "scrollUntilVisible",
                mapOf(
                    "centerElement" to centerElement,
                    "direction" to direction?.name,
                    "element" to selector.data,
                    "speed" to speed,
                    "timeout" to timeout,
                    "visibilityPercentage" to visibilityPercentage,
                ),
            )
        }

        override fun scroll() {
            add("scroll", null)
        }

        override fun setLocation(latitude: String, longitude: String) {
            add(
                "setLocation",
                mapOf(
                    "latitude" to latitude,
                    "longitude" to longitude,
                ),
            )
        }

        override fun startRecording(name: String) {
            add("startRecording", name)
        }

        override fun stopApp(bundleId: String?) {
            add("stopApp", bundleId)
        }

        override fun stopRecording() {
            add("stopRecording", null)
        }

        override fun swipe(direction: Direction, duration: Int?) {
            add(
                "swipe",
                mapOf(
                    "direction" to direction.name.uppercase(),
                    "duration" to duration,
                ),
            )
        }

        override fun swipe(startX: String, startY: String, endX: String, endY: String, duration: Int?) {
            add(
                "swipe",
                mapOf(
                    "start" to "$startX, $startY",
                    "end" to "$endX, $endY",
                    "duration" to duration,
                ),
            )
        }

        override fun takeScreenshot(name: String) {
            add("takeScreenshot", name)
        }

        override fun tapOn(selector: ScriptScope.Selector, repeat: Int?, delay: Int?, waitToSettleTimeoutMs: Int?) {
            tapOn("tapOn", selector, repeat, delay, waitToSettleTimeoutMs)
        }

        private fun tapOn(
            name: String,
            selector: ScriptScope.Selector,
            repeat: Int?,
            delay: Int?,
            waitToSettleTimeoutMs: Int?,
        ) {
            add(
                name,
                selector.data + mapOf(
                    "delay" to delay,
                    "repeat" to repeat,
                    "waitToSettleTimeoutMs" to waitToSettleTimeoutMs,
                ),
            )
        }

        override fun waitForAnimationToEnd(timeout: Int?) {
            add("waitForAnimationToEnd", timeout)
        }
    }.configure()
}
