package com.gitusers.ui.screens.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitusers.model.ModelMocker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    private val _state: MutableStateFlow<UserListScreenState> =
        MutableStateFlow(ModelMocker.mockUserListScreenState())
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

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
            viewModelScope.launch {
                delay(5000)

                _state.update {
                    it.copy(overallState = UserListScreenOverallState.SEARCH_SUCCESSFUL_STATE)
                }
            }
        }
    }
}