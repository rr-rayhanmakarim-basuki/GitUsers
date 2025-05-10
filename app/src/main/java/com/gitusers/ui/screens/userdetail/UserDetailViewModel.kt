package com.gitusers.ui.screens.userdetail

import android.util.Log
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
    val useCase: UserDetailUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserDetailScreenState())
    var state = _state.asStateFlow()

    private val userName: String = savedStateHandle["userName"] ?: ""

    init {
        loadUserDetail()
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
                .onFailure { exception ->
                    Log.e("FailedCall", exception.message.toString())
                    _state.update {
                        it.copy(
                            overallState = OverallState.FAILED
                        )
                    }
                }
        }
    }
}