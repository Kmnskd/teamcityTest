package vcs

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object HelloWorld: BuildType({
    name = "HelloWorld"
    steps {
        script { scriptContent = "echo HelloWorld" }
    }
})