package com.exfarnanda1945.diaryapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,Color.TRANSPARENT
            ),
        )
        setContent {
            DiaryAppTheme {
                val controller = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    NavGraph(
                        startDestination = getStartDestination(),
                        navHostController = controller
                    )
                }

            }
        }
    }

    private fun getStartDestination(): String {
        val user = App.create(APP_ID).currentUser

        return if (user != null && user.loggedIn) Screen.Home.route else Screen.Authentication.route
    }
}
