package com.gitusers.ui.screens.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitusers.R
import com.gitusers.model.GithubUserDetail
import com.gitusers.model.GithubUserRepo
import com.gitusers.model.ModelMocker
import com.gitusers.ui.common.CircularUserImageWithPlaceholderView
import com.gitusers.ui.theme.GitUsersTheme
import com.gitusers.ui.theme.Purple80
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun UserDetailScreen() {
    UserDetailScreenContent(ModelMocker.mockUserDetailScreenState())
}

@Composable
fun UserDetailScreenContent(state: UserDetailScreenState) {
    GitUsersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = {
                Text(
                    stringResource(R.string.user_detail_toolbar_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    UserDetailHeaderView(
                        userDetail = state.userDetail,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.userDetail.repoList) { repo ->
                            UserDetailRepoCardView(repo)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun UserDetailHeaderView(
    userDetail: GithubUserDetail,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        CircularUserImageWithPlaceholderView("", size = 64.dp)
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = userDetail.userName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PurpleGrey40
            )

            userDetail.fullName?.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = PurpleGrey40
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Text(
                    text = "Followers: " + userDetail.followers,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = PurpleGrey40
                )

                VerticalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = "Followers: " + userDetail.followers,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = PurpleGrey40
                )
            }
        }
    }
}

@Composable
fun UserDetailRepoCardView(
    repo: GithubUserRepo,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Purple80
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PurpleGrey40,
            )

            repo.description?.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = repo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = PurpleGrey40,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = repo.language ?: "1",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = PurpleGrey40,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.Yellow),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp)
                )

                Text(
                    text = repo.starCount.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = PurpleGrey40,
                )
            }

        }
    }
}

@Preview
@Composable()
fun UserDetailScreenPreview() {
    UserDetailScreenContent(ModelMocker.mockUserDetailScreenState())
}
