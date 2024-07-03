package com.app.electricstations.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.electricstations.R
import com.app.electricstations.ui.fragments.LoginFragmentDirections.Companion.actionLoginFragmentToMainFragment
import com.app.electricstations.util.Constants.LOGIN_REQUEST
import com.app.electricstations.util.LoadingButton
import com.app.electricstations.util.SharedPrefsUtil
import com.app.electricstations.util.editTextKhayat.SSCustomEdittextOutlinedBorder
import com.app.electricstations.util.network.INetworkDataListener
import com.khayat.app.util.BaseFragment
import com.khayat.app.util.Tools
import java.util.Timer
import java.util.TimerTask


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment(), INetworkDataListener {
//    private var accountRequests: AccountRequests? = null
//    private var loginResponse: LoginResponse? = null
    private var mobile = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SharedPrefsUtil.saveBoolean(requireContext(), "isLogin", true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<LoadingButton>(R.id.btnLogin).isEnabled = false
        requireView().findViewById<LoadingButton>(R.id.btnLogin).setOnClickListener {
            if (!correctCredintials()) {
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorEnable(true, errorMessage = R.string.invalid_email)
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).hideTitle(false)
            } else {
                signIn()
//                mobile = etPhone.getEditTextView()?.text.toString().trim()
//                findNavController().navigate(actionLoginFragmentToVerifyFragment(phone = mobile))
                requireView().findViewById<LoadingButton>(R.id.btnLogin).startLoading()
                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        activity?.runOnUiThread {
//                        view.findNavController().navigate(actionSplashFragmentToMainFragment())

                            SharedPrefsUtil.clearPreference(requireContext(), "isLogin")
                            findNavController().navigate(
                                actionLoginFragmentToMainFragment()
                            )

                            timer.cancel()
                        }
                    }
                }, 2500, 2000)
            }

            }
        requireView().findViewById<ConstraintLayout>(R.id.cl).setOnClickListener {
            requireView().findViewById<ConstraintLayout>(R.id.cl).requestFocus()
        }
        removeErrorwhenTyping()

        requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).getEditTextView()?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setEditTextHint("")
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).hideTitle(false)
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).changeBorderColor()
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorDisable(isShown = false, isFirst = false)

                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPass).setEditTextHint("")
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPass).hideTitle(false)
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPass).changeBorderColor()
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPass).setIsErrorDisable(isShown = false, isFirst = false)
            } else {
                requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setEditTextHint(resources.getString(R.string.email))
                if (requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).editText?.text.isNullOrEmpty()) {
                    requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorEnable(true, R.string.error_email)
                    requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).hideTitle(true)
                    requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setErrorBorder()
                } else {
                    if (!correctCredintials()) {
                        requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorEnable(true, R.string.error_email)
                        requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).hideTitle(false)
                    }
                }

            }
        }
    }

    private fun correctCredintials(): Boolean {
        var checker = false
        if (!Tools.validateEmail(requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).editText?.text.toString())) {
            requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).editText?.error = resources.getString(R.string.invalid_email)
//        } else
//        if (!Tools.isSaudiNumber(requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).editText?.text.toString())) {
            //etPhone.editText.error = resources.getString(R.string.invalid_phone1)
        } else {
            checker = true

        }
        return checker
    }

    private fun removeErrorwhenTyping() {
        requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPass).editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length!! > 0) {
                    requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorDisable(isShown = false, isFirst = false)
                    requireView().findViewById<LoadingButton>(R.id.btnLogin).isEnabled = true
                } else {
                    requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).setIsErrorEnable(true, errorMessage = R.string.error_email)
                    requireView().findViewById<LoadingButton>(R.id.btnLogin).isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun signIn() {
        // send request
        mobile = ("+966" + requireView().findViewById<SSCustomEdittextOutlinedBorder>(R.id.etPhone).getEditTextView()?.text.toString().trim().substring(1))
//        accountRequests = AccountRequests(requireContext(), LOGIN_REQUEST)
//        accountRequests!!.iNetworkDataListener = this
//        accountRequests!!.makeRequest(
//            mobile = mobile
//        )
    }

    override fun onDestroy() {
        super.onDestroy()
//        requireView().findViewById<ConstraintLayout>(R.id.cl)?.requestFocus()
    }

    override fun showLoadingProgress(requestID: Int) {
        requireView().findViewById<LoadingButton>(R.id.btnLogin).startLoading()
    }

    override fun loadingFailure(
        throwable: Throwable,
        requestID: Int,
        errorId: Int,
        message: String
    ) {
        showLongToast(message)
        requireView().findViewById<LoadingButton>(R.id.btnLogin).loadingFailed()
    }

    override fun responseDone(response: Any, requestID: Int) {
        when (requestID) {
            LOGIN_REQUEST -> {
                context?.let {
//                    loginResponse = response as LoginResponse
//                    btnLogin.loadingSuccessful()
//                    hideKeyboard()
//                    if(loginResponse?.code == 1L){
//                        val phone = etPhone.getEditTextView()?.text.toString().trim()
//                        findNavController().navigate(
//                            actionLoginFragmentToMainFragment()
//                        )
//                    }else{
//                        loginResponse?.result?.message?.let { it1 ->
//                            showSnackBar(requireView(),
//                                it1
//                            )
//                        }
//                    }
                }
            }
        }
    }

    override fun stopLoading() {
    }
    override fun onStop() {
        super.onStop()
//        accountRequests?.stop()
    }
}
