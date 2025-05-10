package com.gitusers.repositories

import com.gitusers.api.GitHubUserApi
import com.gitusers.model.response.GithubUserResponse
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val gitHubUserApi: GitHubUserApi
) {
    suspend fun searchUsers(query: String): List<GithubUserResponse> {
        return gitHubUserApi.searchUsers(query = query).items
    }
}