package com.gitusers.ui.screens.userdetail

import ErrorView
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gitusers.R
import com.gitusers.model.GithubUserDetail
import com.gitusers.model.GithubUserRepo
import com.gitusers.model.ModelMocker
import com.gitusers.ui.common.CircularUserImageWithPlaceholderView
import com.gitusers.ui.common.LoadingView
import com.gitusers.ui.navigation.AppNavigationScreen
import com.gitusers.ui.theme.GitUsersTheme
import com.gitusers.ui.theme.Purple80
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadUserDetail()
    }

    val state by viewModel.state.collectAsState()

    UserDetailScreenContent(
        state = state,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onCardClicked = { repo ->
            navController.navigate(
                AppNavigationScreen.WebPage.createRoute(
                    repo.url,
                    repo.name
                )
            )
        },
        onRetryButtonClicked = {
            viewModel.loadUserDetail()
        }
    )
}

@Composable
fun UserDetailScreenContent(
    state: UserDetailScreenState,
    onBackButtonClicked: () -> Unit = {},
    onCardClicked: (GithubUserRepo) -> Unit = {},
    onRetryButtonClicked: () -> Unit = {}
) {
    GitUsersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                ) {
                    IconButton(onClick = { onBackButtonClicked.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }

                    Text(
                        stringResource(R.string.user_detail_toolbar_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AnimatedContent(state.overallState) { targetState ->
                        when (targetState) {
                            OverallState.LOADING -> LoadingView()
                            OverallState.SUCCESS -> {
                                UserDetailView(
                                    state.userDetail,
                                    onCardClicked
                                )
                            }

                            OverallState.FAILED -> ErrorView(
                                onRetry = onRetryButtonClicked,
                                buttonText = stringResource(R.string.retry)
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun UserDetailView(
    userDetail: GithubUserDetail,
    onCardClicked: (GithubUserRepo) -> Unit
) {
    Column {
        UserDetailHeaderView(
            userDetail = userDetail,
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
            items(userDetail.repoList) { repo ->
                UserDetailRepoCardView(
                    repo,
                    onCardClicked
                )
            }
        }
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
        CircularUserImageWithPlaceholderView(
            userDetail.avatarUrl,
            size = 64.dp
        )
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
                    text = stringResource(R.string.followers, userDetail.followers),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = PurpleGrey40
                )

                VerticalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = stringResource(R.string.following, userDetail.following),
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
    onCardClicked: (GithubUserRepo) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Purple80
        ),
        onClick = { onCardClicked.invoke(repo) },
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
