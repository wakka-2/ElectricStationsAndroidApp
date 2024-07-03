package com.khayat.app.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.electricstations.ui.activites.MainActivity
import com.app.electricstations.util.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.khayat.app.util.loadingview.LoadingDialog
import java.util.*


abstract class BaseFragment : Fragment() {
    //private var waitingDialog: SpotsDialog? = null
    private var waitingDialog: LoadingDialog? = null

    fun hideKeyboard() {
        val imm =
            this.requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


    fun removeBackStackItem() {
        (activity as MainActivity).removeBackStackItem()

    }

    fun addFragmentWithResultData(BackFragment: Fragment, targetFragment: Fragment, code: Int) {
        (activity as MainActivity).addFragmentWithResultData(BackFragment, targetFragment, code)
    }

    fun addFragmentWithResultDataBundle(
        backFragment: Fragment,
        targetFragment: Fragment,
        code: Int,
        fragmentManager: FragmentManager,
        bundle: Bundle
    ) {
        (activity as MainActivity).addFragmentWithResultDataBundle(
            backFragment=backFragment,
            targetFragment=targetFragment,
            code=code,
            fragmentManager = fragmentManager,
            bundle=bundle
        )
    }

    fun addFragment(targetFragment: Fragment, fragmentManager: FragmentManager) {
        (activity as MainActivity).addFragment(
            targetFragment = targetFragment,
            fragmentManager = fragmentManager
        )
    }

    fun addFragmentBundle(targetFragment: Fragment, fragmentManager: FragmentManager, b: Bundle) {
        (activity as MainActivity).addFragmentBundle(
            targetFragment = targetFragment, fragmentManager = fragmentManager, b = b
        )
    }

    fun hideAllFragments() {
        (activity as MainActivity).hideAllFragments()
    }

    fun scaleAnim(view: View) {
        (context as MainActivity).scaleAnim(view)
    }

    fun showLongToast(strMsg: String = "", intMsg: Int = 0) {
        if (strMsg.isNotEmpty()) {
            Toast.makeText(context, strMsg, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, intMsg, Toast.LENGTH_LONG).show()
        }
    }

//    fun showToasty(isSuccess: Boolean, message: String) {
//        if (isSuccess) {
//            Toasty.success(requireContext(), message, Toast.LENGTH_SHORT, true).show()
//        } else {
//            Toasty.warning(requireContext(), message, Toast.LENGTH_SHORT, true).show()
//        }
//    }

    fun showSnackBar(view: View, strMsg: String = "", intMsg: Int = 0) {
        val imm = this.requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        if (strMsg.isNotEmpty()) {
            Snackbar.make(view, strMsg, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(view, intMsg, Snackbar.LENGTH_LONG).show()
        }

    }

    fun printer(message: String) {
        Log.d("test3", message)
    }

//    protected open fun showProgress(isCancelable: Boolean) {
//        if (waitingDialog == null) waitingDialog = SpotsDialog(requireContext(), R.style.Custom)
//        waitingDialog!!.setCancelable(isCancelable)
//        waitingDialog!!.show()
//        waitingDialog!!.setMessage(getString(R.string.Loading))
//    }

//    protected open fun dismissProgress() {
//        if (waitingDialog != null && waitingDialog!!.isShowing) {
//            waitingDialog!!.dismiss()
//        }
//    }

//    fun getDuration(filePath: String): String {
//        val uri: Uri = Uri.parse(filePath)
//        val mmr = MediaMetadataRetriever()
//        mmr.setDataSource(context?.applicationContext, uri)
//        val durationStr =
//            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//        val millSecond = durationStr?.toInt()?.plus(1000)
//        val seconds = (millSecond % (1000 * 60 * 60) % (1000 * 60) / 1000)
//        val minutes = (seconds % 3600) / 60
//        return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
//    }

    override fun onStart() {
        super.onStart()
        setOnBackPressed(null)
    }

    //Method must be declared as open, for overriding in child class
    open fun setOnBackPressed(onBackAlternative: (() -> Unit)?) {
        (activity as BaseActivity).onBackPressAlternative = onBackAlternative
    }
    open fun animate(image:ImageView,text:TextView){
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(image, "translationY", -1000f, 0f),
            ObjectAnimator.ofFloat(image, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(text, "translationX", -200f, 0f)
        )
        animatorSet.duration = 2000
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
//                val animatorSet2 = AnimatorSet()
//                animatorSet2.playTogether(
//                    ObjectAnimator.ofFloat(image, "scaleX", 1f, 0.5f, 1f),
//                    ObjectAnimator.ofFloat(image, "scaleY", 1f, 0.5f, 1f)
//                )
//                animatorSet2.interpolator = AccelerateInterpolator()
//                animatorSet2.duration = 1000
//                animatorSet2.start()
            }
        })
        animatorSet.start()
    }
    fun changeTitle(title:Int){
        val fragment = requireParentFragment().parentFragment
//        val toolbar  = fragment?.toolbar
//        toolbar?.findViewById<TextView>(R.id.tvTitle)?.setText(title)
    }
    fun Context.getStringByLocale(@StringRes stringRes: Int, locale: Locale, vararg formatArgs: Any): String {
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        return createConfigurationContext(configuration).resources.getString(stringRes, *formatArgs)
    }

    protected open fun showProgress() {
        if (waitingDialog == null) waitingDialog = LoadingDialog[requireActivity()]
//        waitingDialog!!.(isCancelable)
//        waitingDialog?.setLoadImage(R.drawable.load)
//        waitingDialog.
        waitingDialog!!.show()
        //waitingDialog!!.setMessage(getString(R.string.Loading))
    }

    protected open fun dismissProgress() {
        if (waitingDialog != null) {
            waitingDialog!!.hide()
        }
    }
}