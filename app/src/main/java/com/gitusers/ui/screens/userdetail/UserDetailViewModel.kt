package com.gitusers.ui.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: UserDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserDetailScreenState())
    var state = _state.asStateFlow()

    private val userName: String = savedStateHandle["userName"] ?: ""

    fun initialLoad() {
        if (!state.value.hasInitialLoad) {
            loadUserDetail()
            _state.update {
                it.copy(hasInitialLoad = true)
            }
        }
    }

    fun loadUserDetail() {
        _state.update {
            it.copy(
                overallState = OverallState.LOADING
            )
        }
        viewModelScope.launch {
            val userDetailResult = useCase.getUserDetail(userName)

            userDetailResult
                .onSuccess { detail ->
                    _state.update {
                        it.copy(
                            overallState = OverallState.SUCCESS,
                            userDetail = detail
                        )
                    }
                }
                .onFailure { _ ->
                    _state.update {
                        it.copy(
                            overallState = OverallState.FAILED
                        )
                    }
                }
        }
    }
}