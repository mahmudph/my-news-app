package id.myone.mynewsapp.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import id.myone.mynewsapp.base.ui.interfaces.BaseActivityContract

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), BaseActivityContract<T> {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding()
        setContentView(binding.root)
        setupObserver()
        setupView()
    }

    override fun setupView() {

    }
}