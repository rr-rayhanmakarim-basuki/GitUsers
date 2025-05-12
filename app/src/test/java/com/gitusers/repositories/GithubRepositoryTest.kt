@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gitusers.repositories

import com.gitusers.api.GitHubUserApi
import com.gitusers.model.response.GithubUserDetailResponse
import com.gitusers.model.response.GithubUserRepoResponse
import com.gitusers.model.response.GithubUserResponse
import com.gitusers.model.response.GithubUserSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class GithubRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var api: GitHubUserApi
    private lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = mock()
        repository = GithubRepository(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchUsers returns items from API`() = runTest {
        //Given
        val query = "query"
        val expectedUserName = "userName"
        val expectedAvatarUrl = "avatarUrl"

        val userResponse = GithubUserResponse(
            userName = expectedUserName,
            avatarUrl = expectedAvatarUrl
        )

        val searchResponse = GithubUserSearchResponse(items = listOf(userResponse))

        whenever(api.searchUsers(query)).thenReturn(searchResponse)

        //When
        val result = repository.searchUsers(query)
        advanceUntilIdle()

        //Then
        assertEquals(1, result.size)
        val resultUser = result.first()
        assertEquals(expectedUserName, resultUser.userName)
        assertEquals(expectedAvatarUrl, resultUser.avatarUrl)
    }

    @Test
    fun `userDetail returns detail from API`() = runTest {
        //Given
        val userName = "userName"
        val expectedFullName = "fullName"
        val expectedAvatarUrl = "avatarUrl"
        val expectedFollowers = 42
        val expectedFollowing = 17

        val userDetailResponse = GithubUserDetailResponse(
            name = userName,
            fullName = expectedFullName,
            avatarUrl = expectedAvatarUrl,
            followers = expectedFollowers,
            following = expectedFollowing
        )

        whenever(api.getUserDetail(userName)).thenReturn(userDetailResponse)

        //When
        val result = repository.userDetail(userName)
        advanceUntilIdle()

        //Then
        assertEquals(userName, result.name)
        assertEquals(expectedFullName, result.fullName)
        assertEquals(expectedAvatarUrl, result.avatarUrl)
        assertEquals(expectedFollowers, result.followers)
        assertEquals(expectedFollowing, result.following)
    }

    @Test
    fun `repoList returns repos from API`() = runTest {
        //Given
        val userName = "userName"
        val repoName = "name"
        val description = "description"
        val language = "language"
        val starsCount = 99
        val fork = false
        val webUrl = "webUrl"

        val repoResponse = GithubUserRepoResponse(
            name = repoName,
            description = description,
            language = language,
            starsCount = starsCount,
            fork = fork,
            webUrl = webUrl
        )

        whenever(api.getUserRepos(userName)).thenReturn(listOf(repoResponse))

        //When
        val result = repository.repoList(userName)
        advanceUntilIdle()

        //Then
        assertEquals(1, result.size)
        val repo = result.first()
        assertEquals(repoName, repo.name)
        assertEquals(description, repo.description)
        assertEquals(language, repo.language)
        assertEquals(starsCount, repo.starsCount)
        assertEquals(fork, repo.fork)
        assertEquals(webUrl, repo.webUrl)
    }
}
