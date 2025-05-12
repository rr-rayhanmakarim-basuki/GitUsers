@file:OptIn(ExperimentalCoroutinesApi::class)

package com.gitusers.ui.screens.userlist

import com.gitusers.model.response.GithubUserResponse
import com.gitusers.repositories.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: GithubRepository
    private lateinit var viewModel: UserListViewModel

    private val mockUser = GithubUserResponse(
        userName = "rayhan",
        avatarUrl = "https://avatar.url"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = UserListViewModel(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onInputTextChanged updates inputText only`() {
        val newText = "rayhan"
        viewModel.onInputTextChanged(newText)

        val state = viewModel.state.value
        assertEquals(newText, state.inputText)
        assertNotEquals(newText, state.query)
    }

    @Test
    fun `onSearch triggers successful state update with results`() = runTest(testDispatcher) {
        val query = "rayhan"
        viewModel.onInputTextChanged(query)

        whenever(repository.searchUsers(query)).thenReturn(listOf(mockUser))

        viewModel.onSearch()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UserListScreenOverallState.SEARCH_SUCCESSFUL_STATE, state.overallState)
        assertEquals(query, state.query)
        assertEquals(listOf(mockUser), state.userList)
    }

    @Test
    fun `onSearch sets empty state if search returns no users`() = runTest(testDispatcher) {
        val query = "noone"
        viewModel.onInputTextChanged(query)

        whenever(repository.searchUsers(query)).thenReturn(emptyList())

        viewModel.onSearch()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UserListScreenOverallState.SEARCH_EMPTY_RESULT_STATE, state.overallState)
        assertTrue(state.userList.isEmpty())
    }

    @Test
    fun `onSearch sets error state when API fails`() = runTest(testDispatcher) {
        val query = "errorcase"
        viewModel.onInputTextChanged(query)

        whenever(repository.searchUsers(query)).thenThrow(RuntimeException("network error"))

        viewModel.onSearch()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(UserListScreenOverallState.SEARCH_ERROR_STATE, state.overallState)
        assertTrue(state.userList.isEmpty())
    }

    @Test
    fun `onSearch skips execution when inputText equals query`() = runTest(testDispatcher) {
        val query = "duplicate"
        whenever(repository.searchUsers(query)).thenReturn(emptyList())

        viewModel.onInputTextChanged(query)
        viewModel.onSearch()
        advanceUntilIdle()

        // Set the same text again
        viewModel.onInputTextChanged(query)

        viewModel.onSearch() // Should skip internal `search()`
        advanceUntilIdle()

        // Only one actual repository call expected
        verify(repository, times(1)).searchUsers(query)
    }
}
