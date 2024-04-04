package com.exfarnanda1945.diaryapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.exfarnanda1945.diaryapp.navigation.NavGraph
import com.exfarnanda1945.diaryapp.navigation.Screen
import com.exfarnanda1945.diaryapp.ui.theme.DiaryAppTheme
import com.exfarnanda1945.diaryapp.utils.Constants.APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,Color.TRANSPARENT
            ),
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DiaryAppTheme {
                val controller = rememberNavController()
                NavGraph(
                    startDestination = getStartDestination(),
                    navHostController = controller
                )
            }
        }
    }

    private fun getStartDestination(): String {
        val user = App.create(APP_ID).currentUser

        return if (user != null && user.loggedIn) Screen.Home.route else Screen.Authentication.route
    }
}
