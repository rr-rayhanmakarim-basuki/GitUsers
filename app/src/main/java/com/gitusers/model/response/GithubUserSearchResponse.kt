package com.gitusers.model.response

import com.google.gson.annotations.SerializedName

data class GithubUserSearchResponse(
    @SerializedName("items")
    val items: List<GithubUserResponse>
)

data class GithubUserResponse(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
