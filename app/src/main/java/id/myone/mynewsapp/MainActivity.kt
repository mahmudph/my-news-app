package id.myone.mynewsapp

import android.view.View
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import id.myone.mynewsapp.base.ui.BaseActivity
import id.myone.mynewsapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            val menuItem = binding.toolbar.menu[0]
            when (destination.id) {

                R.id.source_screen -> {
                    menuItem.isVisible = true
                    val categoryName = arguments?.getString("categoryName", null)
                    categoryName?.let { ctgName ->
                        binding.toolbar.title = ctgName
                    }
                }

                R.id.article_list -> {
                    menuItem.isVisible = true
                    binding.toolbar.visibility = View.VISIBLE
                    val sourceName = arguments?.getString("sourcename", null)
                    sourceName?.let { srcName ->
                        binding.toolbar.title = srcName

                    }
                }

                R.id.article_detail -> {
                    binding.toolbar.visibility = View.GONE
                }

                else -> {
                    menuItem.isVisible = false
                }
            }

            val actionView = (menuItem.actionView as androidx.appcompat.widget.SearchView)
            if (!actionView.isIconified) actionView.onActionViewCollapsed()

        }

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navController = (navHostFragment as NavHostFragment).findNavController()

        binding.toolbar.apply {
            setNavigationIcon(R.drawable.baseline_arrow_back_24)
            setupWithNavController(navController, AppBarConfiguration(navController.graph))
        }

        navController.addOnDestinationChangedListener(destinationListener)
    }

    override fun setupObserver() {}
}