package com.gitusers.ui.screens.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitusers.model.ModelMocker
import com.gitusers.model.response.GithubUserResponse
import com.gitusers.repositories.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<UserListScreenState> =
        MutableStateFlow(ModelMocker.mockUserListScreenState())
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onInputTextChanged(query: String) {
        _state.update { it.copy(inputText = query) }
    }

    fun onSearch() {
        val inputText = state.value.inputText
        val currentQuery = state.value.query

        if (inputText == currentQuery) {
            return
        }

        val newState = if (inputText.isEmpty()) {
            UserListScreenOverallState.INITIAL_STATE
        } else {
            UserListScreenOverallState.LOADING_STATE
        }

        _state.update {
            it.copy(
                query = it.inputText,
                overallState = newState
            )
        }

        if (inputText.isNotEmpty()) {
            search()
        }
    }

    private fun search() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val userListResult = withContext(ioDispatcher) {
                runCatching {
                    githubRepository.searchUsers(state.value.query)
                }
            }

            userListResult
                .onSuccess { userList ->
                    handleSearchSuccess(userList)
                }
                .onFailure {
                    handleSearchFailed()
                }
        }
    }

    private fun handleSearchSuccess(userList: List<GithubUserResponse>) {
        val newOverallState = if (userList.isEmpty()) {
            UserListScreenOverallState.SEARCH_EMPTY_RESULT_STATE
        } else {
            UserListScreenOverallState.SEARCH_SUCCESSFUL_STATE
        }

        _state.update {
            it.copy(
                userList = userList,
                overallState = newOverallState
            )
        }
    }

    private fun handleSearchFailed() {
        _state.update {
            it.copy(
                userList = emptyList(),
                overallState = UserListScreenOverallState.SEARCH_ERROR_STATE
            )
        }
    }
}