package com.example.digiit

import android.content.Context
import com.example.digiit.data.UserProvider
import com.example.digiit.data.user.User
import com.example.digiit.navigation.AppNav
import com.example.digiit.navigation.Routes


class ApplicationData(val context: Context, val navigation: AppNav, val userProvider: UserProvider) {
    val user: User?
        get() {
            if (userProvider.user == null) {
                navigation.navigate(Routes.LOGIN.path)
            }
            return userProvider.user
        }
}
