package com.gitusers.ui.screens.userdetail

import com.gitusers.model.GithubUserDetail
import com.gitusers.model.GithubUserRepo
import com.gitusers.repositories.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend fun getUserDetail(userName: String): Result<GithubUserDetail> =
        withContext(Dispatchers.IO) {
            runCatching {
                coroutineScope {
                    val userDetailResponseDeferred = async { repository.userDetail(userName) }
                    val repoListResponseDeferred = async { repository.repoList(userName) }

                    val userDetailResponse = userDetailResponseDeferred.await()
                    val repoListResponse = repoListResponseDeferred.await()

                    GithubUserDetail(
                        userName = userDetailResponse.name,
                        avatarUrl = userDetailResponse.avatarUrl,
                        fullName = userDetailResponse.fullName,
                        followers = userDetailResponse.followers,
                        following = userDetailResponse.following,
                        repoList = repoListResponse
                            .filter { !it.fork }
                            .map { repoResponse ->
                                GithubUserRepo(
                                    name = repoResponse.name,
                                    description = repoResponse.description,
                                    language = repoResponse.language,
                                    starCount = repoResponse.starsCount,
                                    fork = repoResponse.fork,
                                    url = repoResponse.webUrl
                                )
                            }
                    )
                }
            }
        }
}