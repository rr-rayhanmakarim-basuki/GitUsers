package com.gitusers.ui.screens.userlist

import GithubUser
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitusers.R
import com.gitusers.model.ModelMocker
import com.gitusers.ui.theme.GitUsersTheme
import com.gitusers.ui.theme.Purple80
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun UserListScreen() {
    val state = ModelMocker.mockUserListScreenState()
    UserListScreenContent(state)
}

@Composable
fun UserListScreenContent(state: UserListScreenState) {
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
                    CustomSearchBar()
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    UserListView(state)
                }
            }
        )
    }
}

@Composable
fun UserListView(state: UserListScreenState) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 16.dp
        )
    ) {
        items(state.userList) { user ->
            UserView(user)
        }
    }
}

@Composable
fun CustomSearchBar() {
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
                .padding(end = 16.dp)
                .size(20.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
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
fun UserView(
    user: GithubUser,
    modifier: Modifier = Modifier,
) {

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Purple80
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = PurpleGrey40)
                    .size(40.dp)
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