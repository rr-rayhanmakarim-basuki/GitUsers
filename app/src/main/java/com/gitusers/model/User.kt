package com.gitusers.model

data class GithubUserDetail(
    val userName: String = "",
    val avatarUrl: String = "",
    val fullName: String? = null,
    val followers: Int = 0,
    val following: Int = 0,
    val repoList: List<GithubUserRepo> = listOf()
)

data class GithubUserRepo(
    val name: String,
    val description: String?,
    val language: String?,
    val starCount: Int,
    val fork: Boolean,
    val url: String
)