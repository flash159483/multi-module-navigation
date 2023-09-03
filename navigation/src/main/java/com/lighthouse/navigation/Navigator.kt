package com.lighthouse.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.HomeFlow -> navController.navigate(NavGraphDirections.actionGlobalHomeFlow())
        is NavigationFlow.SettingFlow -> navController.navigate(
            NavGraphDirections.actionGlobalSettingFlow(
                navigationFlow.data
            )
        )
    }
}