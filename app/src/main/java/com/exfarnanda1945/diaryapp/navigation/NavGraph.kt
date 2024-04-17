package com.exfarnanda1945.diaryapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exfarnanda1945.diaryapp.presentation.components.DisplayAlertDialog
import com.exfarnanda1945.diaryapp.presentation.screens.auth.AuthViewModel
import com.exfarnanda1945.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.exfarnanda1945.diaryapp.presentation.screens.home.HomeScreen
import com.exfarnanda1945.diaryapp.utils.Constants
import com.exfarnanda1945.diaryapp.utils.Constants.APP_ID
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NavGraph(startDestination: String, navHostController: NavHostController) {
    NavHost(startDestination = startDestination, navController = navHostController) {
        authenticationRoute(
            navigateToHome = {
                navHostController.apply {
                    popBackStack()
                    navigate(Screen.Home.route)
                }
            }
        )
        homeRoute(navigateToWrite = {
            navHostController.navigate(Screen.Write.route)
        }, navigateToAuth = {
            navHostController.popBackStack()
            navHostController.navigate(
                Screen.Authentication.route
            )
        })
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val authViewModel = viewModel<AuthViewModel>()
        val loadingState by authViewModel.loadingState
        val isAuthenticated by authViewModel.isAuthenticated

        AuthenticationScreen(
            loadingState = loadingState,
            onButtonClicked = {
                oneTapSignInState.open()
                authViewModel.setLoading(true)
            },
            oneTapSignInState = oneTapSignInState,
            messageBarState = messageBarState,
            isAuthenticated = isAuthenticated,
            onDialogDismissed = {
                messageBarState.addError(Exception(it))
                authViewModel.setLoading(false)
            },
            onTokenReceived = {
                authViewModel.signInWithMongoAtlas(tokenId = it,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully authenticated !")
                        authViewModel.setLoading(false)
                    },
                    onFailed = { e ->
                        messageBarState.addError(e)
                        authViewModel.setLoading(false)
                    })
            },
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var isDialogOpen by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()

        HomeScreen(
            onMenuClick = {
                scope.launch {
                    drawerState.open()
                }
            },
            navigateToWrite = navigateToWrite,
            drawerState = drawerState,
            onSignOutClick = {
                isDialogOpen = true
            }
        )

        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure want to sign out?",
            dialogOpened = isDialogOpen,
            onCloseDialog = { isDialogOpen = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    user?.let {
                        it.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            })
    }
}

fun NavGraphBuilder.writeRoute() {
    composable(route = Screen.Write.route, arguments = listOf(
        navArgument(name = Constants.WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    )) {

    }
}