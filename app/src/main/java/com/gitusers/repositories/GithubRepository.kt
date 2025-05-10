package com.gitusers.repositories

import com.gitusers.api.GitHubUserApi
import com.gitusers.model.response.GithubUserDetailResponse
import com.gitusers.model.response.GithubUserRepoResponse
import com.gitusers.model.response.GithubUserResponse
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val gitHubUserApi: GitHubUserApi
) {
    suspend fun searchUsers(query: String): List<GithubUserResponse> {
        return gitHubUserApi.searchUsers(query = query).items
    }

    suspend fun userDetail(userName: String): GithubUserDetailResponse {
        return gitHubUserApi.getUserDetail(userName)
    }

    suspend fun repoList(userName: String): List<GithubUserRepoResponse> {
        return gitHubUserApi.getUserRepos(userName)
    }
}