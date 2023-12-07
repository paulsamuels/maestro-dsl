package com.paul.samuels.maestro.dsl

import com.paul.samuels.maestro.dsl.annotations.LibraryExtensionPoint
import com.paul.samuels.maestro.dsl.annotations.ScopeMarker
import com.paul.samuels.maestro.dsl.types.Direction
import com.paul.samuels.maestro.dsl.types.Key
import com.paul.samuels.maestro.dsl.types.Platform

@ScopeMarker
public interface ScriptScope {
    public fun addMedia(vararg path: String)

    public fun assertNotVisible(selector: Selector)

    public fun assertTrue(script: String)

    public fun assertVisible(selector: Selector)

    public fun back()

    public fun clearKeychain()

    public fun clearState(bundleId: String? = null)

    public fun copyTextFrom(selector: Selector)

    public fun doubleTapOn(
        selector: Selector,
        repeat: Int? = null,
        delay: Int? = null,
        waitToSettleTimeoutMs: Int? = null,
    )

    public fun eraseText(characterCount: Int? = null)

    public fun evalScript(script: String)

    public fun extendedWaitUntilNotVisible(selector: Selector, timeout: Int? = null)

    public fun extendedWaitUntilVisible(selector: Selector, timeout: Int? = null)

    public fun hideKeyboard()

    public fun inputText(text: String)

    public fun inputRandomEmail()

    public fun inputRandomNumber()

    public fun inputRandomPersonName()

    public fun inputRandomText()

    public fun launchApp(
        bundleId: String,
        arguments: Map<String, Any>? = null,
        clearKeychain: Boolean? = null,
        clearState: Boolean? = null,
        permissions: Map<String, String>? = null,
        stopApp: Boolean? = null,
    )

    public fun longPressOn(
        selector: Selector,
        repeat: Int? = null,
        delay: Int? = null,
        waitToSettleTimeoutMs: Int? = null,
    )

    public fun openLink(url: String, autoVerify: Boolean? = null, browser: Boolean? = null)

    public fun pasteText()

    public fun pressKey(key: Key)

    public fun repeat(`while`: Condition, configure: ScriptScope.() -> Unit)

    public fun runFlow(condition: Condition, configure: ScriptScope.() -> Unit)

    public fun scroll()

    public fun scrollUntilVisible(
        selector: Selector,
        direction: Direction? = null,
        timeout: Int? = null,
        speed: Int? = null,
        visibilityPercentage: Int? = null,
        centerElement: Boolean? = false,
    )

    public fun setLocation(latitude: String, longitude: String)

    public fun startRecording(name: String)

    public fun stopApp(bundleId: String? = null)

    public fun stopRecording()

    public fun swipe(direction: Direction, duration: Int? = null)

    public fun swipe(startX: String, startY: String, endX: String, endY: String, duration: Int? = null)

    public fun takeScreenshot(name: String)

    public fun tapOn(
        selector: Selector,
        repeat: Int? = null,
        delay: Int? = null,
        waitToSettleTimeoutMs: Int? = null,
    )

    public fun waitForAnimationToEnd(timeout: Int? = null)

    // Conditions

    public fun notVisible(matcher: Selector): Condition = Condition(mapOf("notVisible" to matcher.data))

    public fun platform(platform: Platform): Condition = Condition(mapOf("platform" to platform.name))

    public fun `true`(value: String): Condition = Condition(mapOf("true" to "${"$"}{$value}"))

    public fun visible(matcher: Selector): Condition = Condition(mapOf("visible" to matcher.data))

    @JvmInline
    public value class Condition internal constructor(internal val data: Map<String, Any>)

    // Selectors

    public fun id(
        id: String,
        checked: Boolean? = null,
        enabled: Boolean? = null,
        focused: Boolean? = null,
        optional: Boolean? = null,
        selected: Boolean? = null,
    ): Selector = Selector(
        mapOf(
            "checked" to checked,
            "enabled" to enabled,
            "focused" to focused,
            "id" to id,
            "optional" to optional,
            "selected" to selected,
        ),
    )
    public fun text(
        text: String,
        index: Int? = null,
        checked: Boolean? = null,
        enabled: Boolean? = null,
        focused: Boolean? = null,
        optional: Boolean? = null,
        selected: Boolean? = null,
    ): Selector = Selector(
        mapOf(
            "checked" to checked,
            "enabled" to enabled,
            "focused" to focused,
            "index" to index,
            "optional" to optional,
            "selected" to selected,
            "text" to text,
        ),
    )

    @JvmInline
    public value class Selector internal constructor(internal val data: Map<String, Any?>)

    /**
     * Method to add a new command without waiting for a release of this library to add
     * new maestro commands.
     *
     * For a hypothetical shake command a consuming project could define its own extension
     * like this
     *
     * ```
     * @LibraryExtensionPoint
     * fun ScriptScope.shake() {
     *     add("shake", null)
     * }
     * ```
     */
    @LibraryExtensionPoint
    public fun add(name: String, data: Any?)
}
