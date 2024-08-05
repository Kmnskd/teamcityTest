import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

version = "2024.03"

project {

    vcsRoot(HttpsGithubComMarcobehlerjetbrainsBuildpipelinesGitRefsHeadsMain)

    buildType(Build)
}

object Build : BuildType({
    name = "BuildStep"

    vcs {
        root(HttpsGithubComMarcobehlerjetbrainsBuildpipelinesGitRefsHeadsMain)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                cd calculator-service
                mvn clean package
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        notifications {
            notifierSettings = emailNotifier {
//                email = "kun.kun-li@ubisoft.com"
                email = "kmnskdlikun@163.com"
            }
            buildStarted = true
            buildFinishedSuccessfully = true
            buildFailed = true
        }
    }
})

object HttpsGithubComMarcobehlerjetbrainsBuildpipelinesGitRefsHeadsMain : GitVcsRoot({
    name = "buildpipelines"
    url = "https://github.com/marcobehlerjetbrains/buildpipelines.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
