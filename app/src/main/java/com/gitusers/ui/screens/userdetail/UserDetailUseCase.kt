package com.gitusers.ui.screens.userdetail

import com.gitusers.model.GithubUserDetail
import com.gitusers.repositories.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend fun getUserDetail(userName: String): Result<GithubUserDetail> =
        withContext(Dispatchers.IO) {
            runCatching {
                val response = repository.userDetail(userName)
                GithubUserDetail(
                    userName = response.name,
                    avatarUrl = response.avatarUrl,
                    fullName = response.fullName,
                    followers = response.followers,
                    following = response.following,
                    listOf()
                )
            }
        }
}