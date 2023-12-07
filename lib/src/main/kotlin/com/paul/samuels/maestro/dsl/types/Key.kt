package com.paul.samuels.maestro.dsl.types

public enum class Key(rawValue: String? = null) {
    Back,
    Backspace,
    Enter,
    Home,
    Lock,
    Power,
    VolumeDown("volume down"),
    VolumeUp("volume up"),
    ;

    internal val value = rawValue ?: name.lowercase()
}
