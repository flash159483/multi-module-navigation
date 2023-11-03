package com.lighthouse.multi_module_navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.lighthouse.multi_module_navigation.databinding.ActivityMainBinding
import com.lighthouse.navigation.NavigationFlow
import com.lighthouse.navigation.Navigator
import com.lighthouse.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity(), ToFlowNavigatable {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    // DI 안 쓰려면
    // private val navigator = Navigator() 을 사용 하면 됩니다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.lighthouse.home.R.id.resultFragment -> hideBottomNav()
                com.lighthouse.home.R.id.homeFragment -> showBottomNav()
                com.lighthouse.setting.R.id.settingFragment -> showBottomNav()
            }
        }

        navigator.navController = navController
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    companion object {
        fun getIntent(activity: AppCompatActivity) = Intent(activity, MainActivity::class.java)
    }
}