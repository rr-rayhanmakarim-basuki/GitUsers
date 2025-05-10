package com.gitusers.model

import com.gitusers.ui.screens.userdetail.UserDetailScreenState
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

        fun mockUserDetailScreenState(): UserDetailScreenState {
            return UserDetailScreenState(
                userDetail = GithubUserDetail(
                    userName = "mojombo",
                    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                    fullName = "Pharel William",
                    followers = 9999,
                    following = 7,
                    repoList = mockUserRepoList()
                )
            )
        }

        fun mockUserRepoList(): List<GithubUserRepo> {
            val repo1 = GithubUserRepo(
                name = "domainy",
                description = "for getting the base of a domain",
                language = "Ruby",
                starCount = 4,
                fork = false,
                url = "https://github.com/bmizerany/domainy"
            )

            val repo2 = GithubUserRepo(
                name = "em-syslog",
                description = "Basic support for remote syslog in EM.",
                language = "Ruby",
                starCount = 2,
                fork = false,
                url = "https://github.com/bmizerany/em-sylog"
            )

            return listOf(repo1, repo2)
        }
    }
}