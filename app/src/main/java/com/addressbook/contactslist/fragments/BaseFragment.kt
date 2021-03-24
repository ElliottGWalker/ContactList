package com.addressbook.contactslist.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.addressbook.contactslist.R
import com.addressbook.contactslist.activities.MainActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    protected var layoutID = R.layout.fragment_base
    protected var mBindingRoot: ViewDataBinding? = null
    protected var dialog: AlertDialog? = null

    lateinit var baseView: View

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baseView = inflater.inflate(layoutID, container, false)

        mBindingRoot = DataBindingUtil.bind(baseView)
        mBindingRoot!!.lifecycleOwner = this

        setAsCurrentActiveFragment()

        initViewModels()
        initBinding()
        initObservers()

        return baseView
    }

    private fun setAsCurrentActiveFragment() {
        val thisActivity = activity
        if (thisActivity is MainActivity) {
            thisActivity.currentActiveFragment = this
        }
    }

    open fun navigateTo(navigationID: Int) {
        NavHostFragment.findNavController(this).navigate(navigationID)
    }

    fun navigateTo(navigationID: Int, inclusive: Boolean) {
        val navOptions: NavOptions =
            NavOptions.Builder().setPopUpTo(navigationID, inclusive).build()
        NavHostFragment.findNavController(this).navigate(navigationID, null, navOptions)
    }

    protected fun navigateTo(navigationID: Int, bundle: Bundle) {
        NavHostFragment.findNavController(this).navigate(navigationID, bundle)
    }

    fun navigateTo(action: NavDirections) {
        NavHostFragment.findNavController(this).navigate(action)
    }

    // This method links a view model provider to a nav graph (instead of an activity) so the view model is shared among all fragments in the graph
    protected fun getViewModelProviderFor(graph: Int): ViewModelProvider {
        return ViewModelProvider(
            NavHostFragment.findNavController(this).getViewModelStoreOwner(graph),
            ViewModelProvider.AndroidViewModelFactory(activity?.application!!)
        )
    }

    // This method links a view model provider to a nav graph (instead of an activity) so the view model is shared among all fragments in the graph
    protected fun getGraphViewModelProvider(): ViewModelProvider {
        val graphID = NavHostFragment.findNavController(this).graph.id
        return ViewModelProvider(
            NavHostFragment.findNavController(this).getViewModelStoreOwner(graphID),
            ViewModelProvider.AndroidViewModelFactory(activity?.application!!)
        )
    }

    fun popBackstack(): Boolean {
        return NavHostFragment.findNavController(this).popBackStack()
    }

    protected abstract fun initViewModels()
    protected abstract fun initBinding()
    protected abstract fun initObservers()
    protected abstract fun fragmentTag(): String

    fun makeSnack(msg: String, duration: Int) {
        Snackbar.make(baseView, msg, duration).show()
    }
}