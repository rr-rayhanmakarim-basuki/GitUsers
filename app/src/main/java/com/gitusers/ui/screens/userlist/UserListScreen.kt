package com.gitusers.ui.screens.userlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gitusers.R
import com.gitusers.model.GithubUser
import com.gitusers.model.ModelMocker
import com.gitusers.ui.common.CircularUserImageWithPlaceholderView
import com.gitusers.ui.navigation.AppNavigationScreenList
import com.gitusers.ui.theme.GitUsersTheme
import com.gitusers.ui.theme.Purple80
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    UserListScreenContent(
        state,
        { query ->
            viewModel.onQueryChanged(query)
        },
        { user ->
            navController.navigate(AppNavigationScreenList.USER_LIST_DETAIL.route)
        }
    )
}

@Composable
fun UserListScreenContent(
    state: UserListScreenState,
    onQueryChanged: (String) -> Unit = {},
    onCardClick: (GithubUser) -> Unit = {}
) {
    GitUsersTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = {
                Text(
                    stringResource(R.string.toolbar_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            },
            content = { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    CustomSearchBar(
                        state.query,
                        onQueryChanged
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    UserListView(
                        state,
                        onCardClick
                    )
                }
            }
        )
    }
}

@Composable
fun UserListView(
    state: UserListScreenState,
    onCardClick: (GithubUser) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 16.dp
        )
    ) {
        items(state.userList) { user ->
            UserCardView(
                user,
                onCardClick
            )
        }
    }
}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = PurpleGrey40,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painterResource(R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
        )
        TextField(
            value = query,
            onValueChange = { newValue -> onQueryChanged.invoke(newValue) },
            placeholder = {
                Text(stringResource(R.string.search_placehoder))
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                },
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun UserCardView(
    user: GithubUser,
    onCardClick: (GithubUser) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Purple80
        ),
        onClick = { onCardClick.invoke(user) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            CircularUserImageWithPlaceholderView(
                imageUrl = user.avatarUrl,
                size = 40.dp
            )

            Text(
                text = user.userName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = PurpleGrey40,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun UserListScreenContentPreview() {
    UserListScreenContent(ModelMocker.mockUserListScreenState())
}