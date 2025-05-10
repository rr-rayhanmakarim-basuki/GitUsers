package com.gitusers.model.response

import com.google.gson.annotations.SerializedName

data class GithubUserRepoResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("stargazers_count")
    val starsCount: Int,
    @SerializedName("html_url")
    val webUrl: String
)