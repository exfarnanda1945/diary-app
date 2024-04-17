package com.exfarnanda1945.diaryapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    drawerState:DrawerState,
    onMenuClick: () -> Unit,
    navigateToWrite: () -> Unit,
    onSignOutClick:() -> Unit
) {
    HomeDrawer(drawerState = drawerState, onSignOutClicked = onSignOutClick) {
        Scaffold(
            topBar = {
                HomeTopBar(onMenuClick = onMenuClick)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = navigateToWrite) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Add or Edit"
                    )
                }
            }
        ) {

        }
    }

}