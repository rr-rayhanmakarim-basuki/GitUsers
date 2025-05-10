package com.gitusers.model

data class GithubUserDetail(
    val userName: String,
    val avatarUrl: String,
    val fullName: String?,
    val followers: Int,
    val following: Int,
    val repoList: List<GithubUserRepo>
)

data class GithubUserRepo(
    val name: String,
    val description: String?,
    val language: String?,
    val starCount: Int,
    val fork: Boolean,
    val url: String
)