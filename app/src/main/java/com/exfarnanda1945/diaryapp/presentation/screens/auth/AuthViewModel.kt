package com.exfarnanda1945.diaryapp.presentation.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exfarnanda1945.diaryapp.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    var loadingState = mutableStateOf(false)
        private set

    var isAuthenticated = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }


    fun signInWithMongoAtlas(
        tokenId: String,
        onSuccess: () -> Unit,
        onFailed: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.create(APP_ID).login(
                        Credentials.jwt(tokenId)
                    ).loggedIn
                }

                withContext(Dispatchers.Main) {
                    if(result){
                        onSuccess()
                        delay(600)
                        isAuthenticated.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailed(e)
                }
            }
        }
    }
}