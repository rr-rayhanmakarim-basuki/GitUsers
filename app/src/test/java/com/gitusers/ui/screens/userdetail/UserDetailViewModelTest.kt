@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gitusers.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import com.gitusers.model.GithubUserDetail
import com.gitusers.model.GithubUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var useCase: UserDetailUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val userName = "userName"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock()
        savedStateHandle = SavedStateHandle(mapOf("userName" to userName))
        viewModel = UserDetailViewModel(savedStateHandle, useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialLoad triggers loadUserDetail only once`() = runTest {
        val avatarUrl = "avatarUrl"
        val fullName = "fullName"
        val followers = 10
        val following = 20
        val repo = GithubUserRepo(
            name = "repo",
            description = "description",
            language = "language",
            starCount = 123,
            fork = false,
            url = "url"
        )
        val expectedDetail = GithubUserDetail(
            userName = userName,
            avatarUrl = avatarUrl,
            fullName = fullName,
            followers = followers,
            following = following,
            repoList = listOf(repo)
        )

        whenever(useCase.getUserDetail(userName)).thenReturn(Result.success(expectedDetail))

        // Act: First call triggers API
        viewModel.initialLoad()
        advanceUntilIdle()

        // Assert first call
        val stateAfterFirst = viewModel.state.value
        assertEquals(OverallState.SUCCESS, stateAfterFirst.overallState)
        assertEquals(expectedDetail, stateAfterFirst.userDetail)
        assertTrue(stateAfterFirst.hasInitialLoad)

        // Act: Second call shouldn't trigger API again
        viewModel.initialLoad()
        advanceUntilIdle()

        // Verify useCase still only called once
        verify(useCase, times(1)).getUserDetail(userName)
    }

    @Test
    fun `loadUserDetail sets FAILED state on failure`() = runTest(testDispatcher) {
        val exception = RuntimeException("Network error")
        whenever(useCase.getUserDetail(userName)).thenReturn(Result.failure(exception))

        // Act
        viewModel.loadUserDetail()
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(OverallState.FAILED, state.overallState)
    }
}
