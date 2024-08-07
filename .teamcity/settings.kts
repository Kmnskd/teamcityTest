import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.vcs.PerforceVcsRoot
import vcs.HelloWorld
import vcs.GitMain


/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

//version = "2024.03"
version = "2023.11"

project {
    vcsRoot(GitMain)
    buildType(Build)
    buildType(HelloWorld)
}

object Build : BuildType({
    name = "BuildStep"

    vcs {
        root(GitMain)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                #echo %teamcity.user.email%
                curl -u "%system.teamcity.auth.userId%:%system.teamcity.auth.password%" "%teamcity.serverUrl%/httpAuth/app/rest/builds/id:%teamcity.build.id%"
                cd calculator-service
                #mvn clean package
            """.trimIndent()
        }
    }

    triggers {
        schedule {
            schedulingPolicy = cron {
                minutes = "39"
                hours = "17"
//                timezone = "Asia/Chongqing"
            }
            triggerBuild = always()
            withPendingChangesOnly = false
        }
    }

    features {
        notifications {
            notifierSettings = emailNotifier {
//                email = "kun.kun-li@ubisoft.com\n" +
//                        "kmnskdlikun@163.com"
                email = "%teamcity.user.email%"
//                email = "kmnskdlikun@163.com"
            }

//            buildStarted = true
            buildFinishedSuccessfully = true
            buildFailed = true
        }
    }
})

//object HelloWorld: BuildType({
//    name = "HelloWorld"
//    steps {
//        script { scriptContent = "echo HelloWorld" }
//    }
//})

//object GithubMain: GitVcsRoot({
//    name = "buildpipelines"
//    url = "https://github.com/marcobehlerjetbrains/buildpipelines.git"
//    branch = "refs/heads/main"
//    branchSpec = "refs/heads/*"
//})