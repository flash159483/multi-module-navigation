package com.lighthouse.navigation

sealed class NavigationFlow {
    object HomeFlow: NavigationFlow()
    data class SettingFlow(val data: String): NavigationFlow()
}