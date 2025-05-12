@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gitusers.ui.screens.userdetail

import com.gitusers.model.response.GithubUserDetailResponse
import com.gitusers.model.response.GithubUserRepoResponse
import com.gitusers.repositories.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserDetailUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: GithubRepository
    private lateinit var useCase: UserDetailUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        useCase = UserDetailUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserDetail returns mapped GithubUserDetail successfully`() = runTest {
        //Given
        val userName = "userName"
        val avatarUrl = "avatarUrl"
        val fullName = "fullName"
        val followers = 42
        val following = 17

        val repoName = "name"
        val repoDescription = "description"
        val repoLanguage = "language"
        val repoStarCount = 999
        val repoUrl = "webUrl"
        val isForked = false

        val userResponse = GithubUserDetailResponse(
            name = userName,
            avatarUrl = avatarUrl,
            fullName = fullName,
            followers = followers,
            following = following
        )

        val nonForkedRepo = GithubUserRepoResponse(
            name = repoName,
            description = repoDescription,
            language = repoLanguage,
            starsCount = repoStarCount,
            fork = isForked,
            webUrl = repoUrl
        )

        val forkedRepo = GithubUserRepoResponse(
            name = "forked",
            description = "ignored",
            language = "ignored",
            starsCount = 0,
            fork = true,
            webUrl = "ignored"
        )

        val repoResponses = listOf(nonForkedRepo, forkedRepo)

        whenever(repository.userDetail(userName)).thenReturn(userResponse)
        whenever(repository.repoList(userName)).thenReturn(repoResponses)

        //When
        val result = useCase.getUserDetail(userName)
        advanceUntilIdle()

        //Then
        assertTrue(result.isSuccess)
        val detail = result.getOrNull()
        assertNotNull(detail)

        assertEquals(userName, detail?.userName)
        assertEquals(fullName, detail?.fullName)
        assertEquals(avatarUrl, detail?.avatarUrl)
        assertEquals(followers, detail?.followers)
        assertEquals(following, detail?.following)

        assertEquals(1, detail?.repoList?.size)
        val repo = detail?.repoList?.first()
        assertEquals(repoName, repo?.name)
        assertEquals(repoDescription, repo?.description)
        assertEquals(repoLanguage, repo?.language)
        assertEquals(repoStarCount, repo?.starCount)
        assertEquals(isForked, repo?.fork)
        assertEquals(repoUrl, repo?.url)
    }

    @Test
    fun `getUserDetail returns failure when repository throws`() = runTest {
        //Given
        val userName = "userName"

        whenever(repository.userDetail(userName)).thenThrow(RuntimeException("User not found"))

        //When
        val result = useCase.getUserDetail(userName)
        advanceUntilIdle()

        //Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertEquals("User not found", exception?.message)
    }
}
