package com.app.electricstations.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.app.electricstations.R
import com.app.electricstations.util.network.INetworkDataListener
import com.app.electricstations.ui.fragments.SplashFragmentDirections.Companion.actionSplashFragmentToLoginFragment
import com.khayat.app.util.BaseFragment
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment(), INetworkDataListener {
//    private var accountRequests: AccountRequests? = null
//    private var orderRequests: OrderRequests? = null
//    private var checkTokenResponse: LoginResponse? = null
//    private var profileResponse: ProfileResponse? = null
//    private var vatResponse: VATResponse? = null
//    private var expressResponse: ExpressResponse? = null
    private var isFirstLogin: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = Timer()
        //getFCMToken()
//        vat()
//        profile()
//        express()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
//                        view.findNavController().navigate(actionSplashFragmentToMainFragment())

                        view.findNavController().navigate(actionSplashFragmentToLoginFragment())


                    timer.cancel()
                }
            }
        }, 2500, 2000)
    }

//    private fun profile() {
//        // send request
//        accountRequests = AccountRequests(requireContext(), Constants.PROFILE_REQUEST)
//        accountRequests!!.iNetworkDataListener = this
//        accountRequests!!.makeRequest()
//    }
//    private fun checkToken() {
//        // send request
//        accountRequests = AccountRequests(requireContext(), Constants.CHECK_TOKEN_REQUEST)
//        accountRequests!!.iNetworkDataListener = this
//        accountRequests!!.makeRequest()
//    }
//    private fun vat() {
//        // send request
//        orderRequests = OrderRequests(requireContext(), Constants.VAT_REQUEST)
//        orderRequests!!.iNetworkDataListener = this
//        orderRequests!!.makeRequest()
//    }
//    private fun express() {
//        // send request
//        orderRequests = OrderRequests(requireContext(), Constants.EXPRESS_REQUEST)
//        orderRequests!!.iNetworkDataListener = this
//        orderRequests!!.makeRequest()
//    }

    override fun showLoadingProgress(requestID: Int) {

    }

    override fun loadingFailure(
        throwable: Throwable,
        requestID: Int,
        errorId: Int,
        message: String
    ) {
    }

    override fun responseDone(response: Any, requestID: Int) {
        when (requestID) {
//            Constants.PROFILE_REQUEST -> {
//                context?.let {
//                    profileResponse = response as ProfileResponse
//                    profileResponse?.result?.isFirstLogin?.let { it1 ->
//                        SharedPrefsUtil.saveBoolean(requireContext(),"isFirstLogin",
//                            it1
//                        )
//                        isFirstLogin = it1
//                        checkToken()
//                    }
////                    if (!profileResponse?.result?.isFirstLogin!!) {
////                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMainFragment())
////                    }
//                }
//            }


        }
    }

    override fun stopLoading() {
    }
    override fun onStop() {
        super.onStop()
//        accountRequests?.stop()
    }
}
