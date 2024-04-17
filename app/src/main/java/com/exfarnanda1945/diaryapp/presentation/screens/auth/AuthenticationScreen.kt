package com.exfarnanda1945.diaryapp.presentation.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.exfarnanda1945.diaryapp.utils.Constants.OAUTH_CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@Composable
fun AuthenticationScreen(
    oneTapSignInState: OneTapSignInState,
    messageBarState: MessageBarState,
    loadingState: Boolean,
    isAuthenticated: Boolean,
    onButtonClicked: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    LaunchedEffect(key1 = isAuthenticated) {
        if (isAuthenticated) navigateToHome()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.onSurface)
    ) { paddingValues ->
        ContentWithMessageBar(messageBarState = messageBarState, errorMaxLines = 3) {
            AuthenticationContent(
                loadingState = loadingState,
                onButtonClicked = onButtonClicked,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = OAUTH_CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            Log.d("token", "AuthenticationScreen: $tokenId")
            onTokenReceived(tokenId)
        },
        onDialogDismissed = {
            onDialogDismissed(it)
        })
}
