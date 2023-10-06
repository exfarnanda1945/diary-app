package com.exfarnanda1945.diaryapp.navigation

import com.exfarnanda1945.diaryapp.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY

sealed class Screen( val route: String) {
    data object Authentication : Screen("authentication_screen")
    data object Home : Screen("home_screen")
    data object Write :
        Screen("write_screen?$WRITE_SCREEN_ARGUMENT_KEY={$WRITE_SCREEN_ARGUMENT_KEY}") {
        fun passDiaryId(diaryId: String) = "write_screen?$WRITE_SCREEN_ARGUMENT_KEY=$diaryId"
    }

}
