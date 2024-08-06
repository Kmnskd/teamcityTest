package vcs

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot


object GithubMain: GitVcsRoot({
    name = "buildpipelines"
    url = "https://github.com/marcobehlerjetbrains/buildpipelines.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
})
