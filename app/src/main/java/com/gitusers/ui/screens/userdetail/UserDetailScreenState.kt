package com.gitusers.ui.screens.userdetail

import com.gitusers.model.GithubUserDetail

data class UserDetailScreenState(
    val userDetail: GithubUserDetail = GithubUserDetail(),
    val overallState: OverallState = OverallState.LOADING
)

enum class OverallState {
    LOADING,
    SUCCESS,
    FAILED
}