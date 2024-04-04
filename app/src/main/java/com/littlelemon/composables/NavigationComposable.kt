package com.littlelemon.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.littlelemon.navigation.Home
import com.littlelemon.navigation.Onboarding
import com.littlelemon.navigation.Profile

@Composable
fun NavigationComposable(context: Context,navController: NavHostController) {

    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if (sharedPreferences.getBoolean("userRegistered", false)) {
        startDestination = Home.route
    }

    NavHost(navController = navController, startDestination = startDestination){
        composable(Onboarding.route){
            Onboarding(context, navController)
        }
        composable(Home.route){
            Home(navController)
        }
        composable(Profile.route){
            Profile(context, navController)
        }
    }
}