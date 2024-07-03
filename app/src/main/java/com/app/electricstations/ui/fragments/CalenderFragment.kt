package com.app.electricstations.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.app.electricstations.R
import com.app.electricstations.model.RouteRequest
import com.app.electricstations.model.RouteResponse
import com.app.electricstations.requests.CourseRequests
import com.app.electricstations.ui.fragments.CalenderFragmentDirections.Companion.actionCalenderFragmentToSelectSubjectFragment
import com.app.electricstations.util.Constants
import com.app.electricstations.util.LoadingButton
import com.app.electricstations.util.SharedPrefsUtil
import com.app.electricstations.util.editTextKhayat.SSCustomEdittextOutlinedBorder
import com.app.electricstations.util.network.INetworkDataListener


class CalenderFragment : Fragment(), INetworkDataListener {

    var btnNext:LoadingButton?= null
    var spFrom:Spinner?= null
    var spTo:Spinner?= null
    var etRoad:SSCustomEdittextOutlinedBorder?= null
    var etUserCurrentDis:SSCustomEdittextOutlinedBorder?= null
    var etUserMaxDis:SSCustomEdittextOutlinedBorder?= null
    var city1 = ""
    var city2 = ""
    var road = ""
    var userCurrentDis:Long = 0
    var userMaxDis:Long = 0

    private var courseRequests: CourseRequests? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linkViews(view)
        btnNext = requireView().findViewById<LoadingButton>(R.id.btnLogin)
        btnNext?.setOnClickListener {
            city1 = spFrom?.selectedItem.toString()
            city2 = spTo?.selectedItem.toString()
            road = etRoad!!.getTextValue
            userCurrentDis = etUserCurrentDis!!.getTextValue.toLong()
            userMaxDis = etUserMaxDis!!.getTextValue.toLong()

            SharedPrefsUtil.saveString(requireContext(),"city1", city1)
            SharedPrefsUtil.saveString(requireContext(),"city2", city2)
            SharedPrefsUtil.saveString(requireContext(),"road", road)
            SharedPrefsUtil.saveString(requireContext(),"userCurr", etUserCurrentDis!!.getTextValue)
            SharedPrefsUtil.saveString(requireContext(),"userMax", etUserMaxDis!!.getTextValue)

            val routeReq = RouteRequest(city1,city2,road, userCurrentDis,userMaxDis)
            callRequest(routeReq)
            btnNext?.startLoading()
        }
    }

    private fun callRequest(routeReq: RouteRequest) {
        courseRequests = CourseRequests(requireContext(), Constants.GET_COURSES_REQUEST)
        courseRequests!!.iNetworkDataListener = this
        courseRequests!!.makeRequest(routeRequest = routeReq)
    }

    private fun linkViews(view:View) {
        spFrom = view.findViewById(R.id.spFrom)
        spTo = view.findViewById(R.id.spTo)
        etRoad = view.findViewById(R.id.etRoad)
        etUserCurrentDis = view.findViewById(R.id.etDistance)
        etUserMaxDis = view.findViewById(R.id.etMax)
        val cities = resources.getStringArray(R.array.cities)

        val adapter = ArrayAdapter<String>(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            cities
        )
        spFrom?.setAdapter(adapter)
        spTo?.setAdapter(adapter)
    }

    private fun setUpSpinner(result: ArrayList<String>) {

    }

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
        btnNext?.reset()
        findNavController().navigate(
            actionCalenderFragmentToSelectSubjectFragment(canTravel = (response as RouteResponse).isReachable )
        )
    }

    override fun stopLoading() {

    }

}