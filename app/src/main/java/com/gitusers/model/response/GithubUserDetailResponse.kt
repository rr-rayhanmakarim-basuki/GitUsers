package com.gitusers.model.response

import com.google.gson.annotations.SerializedName

data class GithubUserDetailResponse(
    @SerializedName("login")
    val name: String,
    @SerializedName("name")
    val fullName: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int
)