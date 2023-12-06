package com.paul.samuels.maestro.dsl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

public fun maestroCompose(bundleId: String, configure: ScriptScope.() -> Unit): String = "appId: $bundleId\n" +
    YAMLMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .writeValueAsString(buildSteps(configure))
