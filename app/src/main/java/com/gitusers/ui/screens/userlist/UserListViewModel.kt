package com.gitusers.ui.screens.userlist

import androidx.lifecycle.ViewModel
import com.gitusers.model.ModelMocker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserListViewModel : ViewModel() {
    private val _state: MutableStateFlow<UserListScreenState> =
        MutableStateFlow(ModelMocker.mockUserListScreenState())
    val state: StateFlow<UserListScreenState> = _state.asStateFlow()

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
    }
}