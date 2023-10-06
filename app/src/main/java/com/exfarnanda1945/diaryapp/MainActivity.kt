package com.exfarnanda1945.diaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.exfarnanda1945.diaryapp.navigation.NavGraph
import com.exfarnanda1945.diaryapp.navigation.Screen
import com.exfarnanda1945.diaryapp.ui.theme.DiaryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DiaryAppTheme {
                val controller = rememberNavController()
                NavGraph(
                    startDestination = Screen.Authentication.route,
                    navHostController = controller
                )
            }
        }
    }
}
