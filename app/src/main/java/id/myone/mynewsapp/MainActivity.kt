package id.myone.mynewsapp

import androidx.navigation.fragment.NavHostFragment
import id.myone.mynewsapp.base.ui.BaseActivity
import id.myone.mynewsapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        val navController = (navHostFragment as NavHostFragment).navController
    }

    override fun setupObserver() {
    }
}