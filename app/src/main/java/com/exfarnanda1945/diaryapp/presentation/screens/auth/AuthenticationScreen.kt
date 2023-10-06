package com.exfarnanda1945.diaryapp.presentation.screens.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.exfarnanda1945.diaryapp.utils.Constants.OAUTH_CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    oneTapSignInState: OneTapSignInState,
    messageBarState: MessageBarState,
    loadingState: Boolean,
    onButtonClicked: () -> Unit
) {
    Scaffold { paddingValues ->
        ContentWithMessageBar(messageBarState = messageBarState) {
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
            messageBarState.addSuccess("Successfully authenticated !")
        },
        onDialogDismissed = {
            messageBarState.addError(Exception(it))
        })
}
