package com.app.electricstations.util

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.electricstations.R
import com.app.electricstations.model.FragmentModel
import java.util.*
import kotlin.collections.ArrayList


open class BaseActivity : AppCompatActivity() {
    private var fragmentList = ArrayList<FragmentModel>()
    var onBackPressAlternative: (() -> Unit)? = null

    fun hideAllFragments() {
        for (fragmentModel in fragmentList) {
            val manager = fragmentModel.fragmentManager
            val trans = manager.beginTransaction()
            trans.remove(fragmentModel.fragment)
            trans.commit()
            manager.popBackStack(
                fragmentModel.fragment.javaClass.name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        fragmentList.clear()
    }

    fun removeBackStackItem() {
        hideFragment(fragmentList.removeAt(fragmentList.lastIndex))
    }

    private fun hideFragment(fragmentModel: FragmentModel) {
        val manager = fragmentModel.fragmentManager
        val trans = manager.beginTransaction()
        trans.remove(fragmentModel.fragment)
        trans.commit()
        manager.popBackStack(
            fragmentModel.fragment.javaClass.name,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun addFragment(targetFragment: Fragment, fragmentManager: FragmentManager) {
        addBackStackItem(targetFragment, fragmentManager)
        val backStateName = targetFragment.javaClass.name
        val manager = fragmentManager
        val ft = manager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        ft.add(R.id.mainFragmentHostFragment, targetFragment, backStateName)
        ft.addToBackStack(backStateName)
        ft.commitAllowingStateLoss()
    }

    fun addFragmentBundle(targetFragment: Fragment, fragmentManager: FragmentManager, b: Bundle) {
        addBackStackItem(targetFragment, fragmentManager)
        val backStateName = targetFragment.javaClass.name
        val manager = fragmentManager
        val ft = manager.beginTransaction()


        targetFragment.arguments = b


        ft.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        ft.add(R.id.mainFragmentHostFragment, targetFragment, backStateName)
        ft.addToBackStack(backStateName)
        ft.commitAllowingStateLoss()
    }

    fun addFragmentWithResultData(BackFragment: Fragment, targetFragment: Fragment, code: Int) {
        addBackStackItem(targetFragment, BackFragment.requireFragmentManager())
        val backStateName = targetFragment.javaClass.name
        val manager = BackFragment.requireFragmentManager()
        val ft = manager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )
        targetFragment.setTargetFragment(BackFragment, code)
        ft.add(R.id.mainFragmentHostFragment, targetFragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    fun addFragmentWithResultDataBundle(
        backFragment: Fragment,
        targetFragment: Fragment,
        code: Int,
        fragmentManager: FragmentManager,
        bundle: Bundle
    ) {
        addBackStackItem(
            fragment = targetFragment,
            fragmentManager = fragmentManager
        )
        val backStateName = targetFragment.javaClass.name
        val manager =fragmentManager
        val ft = manager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )

        targetFragment.arguments = bundle

        targetFragment.setTargetFragment(backFragment, code)
        ft.add(R.id.mainFragmentHostFragment, targetFragment)
        ft.addToBackStack(backStateName)
        ft.commit()
    }

    private fun addBackStackItem(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentList.add(FragmentModel(fragment, fragmentManager))
    }

    override fun onBackPressed() {
        if (onBackPressAlternative != null) {
            onBackPressAlternative!!()
        } else {
            if (fragmentList.isEmpty()) {
                super.onBackPressed()
            } else {
                hideFragment(fragmentList.removeAt(fragmentList.lastIndex))
            }
        }
    }

    fun print(message: String) {
        Log.d("test1", message)
    }

    fun scaleAnim(view: View) {
        val myAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 10.0)
        myAnim.interpolator = interpolator
        view.startAnimation(myAnim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.e(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//            token?.let {
//                SharedPrefsUtil.saveString(applicationContext,"firebase_token",
//                    it
//                )
//            }
            // Log and toast
//            val msg = getString(R.string.today, token)
//            token?.let { Log.e(ContentValues.TAG, it) }
//            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    //========================
    private var progressDialog: ProgressDialog? = null

    protected open fun showProgressDialog(messageId: Int) {
        if (progressDialog != null && progressDialog!!.isShowing) {
            return
        }
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setCancelable(false)
        }
        progressDialog!!.setMessage(getString(messageId))
        progressDialog!!.show()
    }

    protected open fun hideProgressDialog() {
        if (progressDialog == null) {
            return
        }
        progressDialog!!.dismiss()
    }

    protected open fun showAlertDialog(message: String?) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(R.string.hello_text, null)
            .setCancelable(false)
            .show()
    }

    protected open fun showAlertDialog(messageId: Int) {
        showAlertDialog(getString(messageId))
    }
}