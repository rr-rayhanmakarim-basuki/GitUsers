package com.gitusers.model

import com.gitusers.ui.screens.userlist.UserListScreenState

class ModelMocker {
    companion object {
        fun mockGithubUserList(): List<GithubUser> {
            val user1 = GithubUser(
                "mojombo",
                "https://avatars.githubusercontent.com/u/1?v=4"
            )

            val user2 = GithubUser(
                "defunkt",
                "https://avatars.githubusercontent.com/u/2?v=4"
            )

            return listOf(user1, user2)
        }

        fun mockUserListScreenState(): UserListScreenState {
            return UserListScreenState(
                mockGithubUserList() + mockGithubUserList() + mockGithubUserList() + mockGithubUserList() + mockGithubUserList() + mockGithubUserList() + mockGithubUserList()
            )
        }
    }
}