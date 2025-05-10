package com.gitusers.ui.screens.userlist

import com.gitusers.model.response.GithubUserResponse
import com.gitusers.ui.screens.userlist.UserListScreenOverallState.INITIAL_STATE

data class UserListScreenState(
    val userList: List<GithubUserResponse> = emptyList(),
    val inputText: String = "",
    val query: String = "",
    val overallState: UserListScreenOverallState = INITIAL_STATE
)

enum class UserListScreenOverallState {
    INITIAL_STATE,
    SEARCH_SUCCESSFUL_STATE,
    SEARCH_ERROR_STATE,
    SEARCH_EMPTY_RESULT_STATE,
    LOADING_STATE
}