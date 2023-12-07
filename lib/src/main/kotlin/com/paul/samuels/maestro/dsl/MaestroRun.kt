package com.paul.samuels.maestro.dsl

import kotlin.io.path.createTempDirectory
import kotlin.io.path.div

public fun maestroRun(bundleId: String, configure: ScriptScope.() -> Unit) {
    val tmpFile = createTempDirectory() / "commands.yaml"

    tmpFile.toFile().writeText(maestroCompose(bundleId, configure))

    ProcessBuilder("maestro", "test", tmpFile.toString())
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor()
}
