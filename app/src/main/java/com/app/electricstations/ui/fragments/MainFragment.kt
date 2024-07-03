package com.app.electricstations.ui.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.app.electricstations.R
import com.app.electricstations.ui.activites.MainActivity
import com.app.electricstations.util.Constants
import com.app.electricstations.util.network.INetworkDataListener
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.khayat.app.util.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : BaseFragment(), INetworkDataListener {
//    private var accountRequests: AccountRequests? = null
//    private var logoutResponse: LoginResponse? = null

    private var drawerOpened = false

    private val listener = NavController.OnDestinationChangedListener { _, _, _ ->
        hideAllFragments()
    }
    private var customBackListener: BackClickListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launch {
            delay(500)
            removeViewAnim()
        }
        handleBottomClicks()
        //removeViewAnim()
        //updateDrawerState()
//        handelBackButton()


    }

    private fun handleBottomClicks() {
        val bottomNav = requireView().findViewById<ChipNavigationBar>(R.id.menu)
//        bottomNav.showBadge(R.id.manage,4)
        bottomNav.setItemEnabled(R.id.calender,true)
        bottomNav.setOnItemSelectedListener {
            when(it){
                R.id.calender -> {
                    val navController =
                        requireActivity().findNavController(R.id.mainFragmentHostFragment)
                    navController.navigate(R.id.calenderFragment)
                }
                R.id.performance -> {
                    val navController =
                        requireActivity().findNavController(R.id.mainFragmentHostFragment)
                    navController.navigate(R.id.performance)
                }
                R.id.manage -> {
                    val navController =
                        requireActivity().findNavController(R.id.mainFragmentHostFragment)
                    navController.navigate(R.id.manage)
                }
                R.id.profile -> {
                    val navController =
                        requireActivity().findNavController(R.id.mainFragmentHostFragment)
                    navController.navigate(R.id.profile)
                }
            }
        }
    }

    private fun initViews() {
        val navController = this.requireActivity().findNavController(R.id.mainFragmentHostFragment)
        (context as MainActivity).setSupportActionBar(requireView().findViewById<Toolbar>(R.id.toolbar))
        setHasOptionsMenu(true)
//        if(navController.currentDestination?.id == R.id.mapFragment) {
////            toolbar.title = resources.getString(R.string.pickup)
////            (context as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
////            (context as MainActivity).supportActionBar?.title = resources.getString(R.string.pickup)
//        }else {
//            (context as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
//        }

        NavigationUI.setupWithNavController(requireView().findViewById<Toolbar>(R.id.toolbar), navController)

        navController.addOnDestinationChangedListener(listener)

        requireView().findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            when {
//                navController.currentDestination?.id == R.id.addMedicineFragment -> {
//                    customBackListener?.onBack(0)
//                }
//                navController.currentDestination?.id != R.id.mapFragment -> {
//                    navController.popBackStack()
//                }
//                navController.currentDestination?.id == R.id.myOrdersFragment ->{
//                    customBackListener?.onBack(0)
//                    //findNavController().popBackStack(R.id.mapFragment, true)
//                }
                else -> {
//                    navController.navigateUp(drawerLayout)
                }
            }
        }
    }

    interface BackClickListener {
        fun onBack(item: Int?)
    }
    private fun removeViewAnim() {

        val x: Int = requireView().findViewById<View>(R.id.centerPoint).right
        val y: Int = requireView().findViewById<View>(R.id.centerPoint).bottom
        val startRadius: Float = requireView().findViewById<ConstraintLayout>(R.id.drawerLayout)
            .width.coerceAtLeast(requireView().findViewById<ConstraintLayout>(R.id.drawerLayout).height).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(requireView().findViewById<View>(R.id.animate_view), x, y, startRadius, 0f)
        animator.duration = 700
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                requireView().findViewById<View>(R.id.animate_view).visibility = View.GONE

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

    }

    override fun showLoadingProgress(requestID: Int) {
        showProgress()
    }

    override fun loadingFailure(
        throwable: Throwable,
        requestID: Int,
        errorId: Int,
        message: String
    ) {
        dismissProgress()
    }

    override fun responseDone(response: Any, requestID: Int) {
        dismissProgress()
        when (requestID) {
            Constants.LOGOUT_REQUEST -> {
                context?.let {

                }
            }
        }
    }

    override fun stopLoading() {
        dismissProgress()
    }

}
