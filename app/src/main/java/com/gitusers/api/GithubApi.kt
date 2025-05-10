package com.gitusers.api

import com.gitusers.model.GithubUserDetail
import com.gitusers.model.GithubUserRepo
import com.gitusers.model.response.GithubUserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserApi {

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): GithubUserDetail

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): List<GithubUserRepo>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): GithubUserSearchResponse
}
