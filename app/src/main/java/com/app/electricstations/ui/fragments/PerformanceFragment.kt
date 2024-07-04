package com.app.electricstations.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.electricstations.R
import com.app.electricstations.model.RouteRequest
import com.app.electricstations.model.RouteResponse
import com.app.electricstations.requests.CourseRequests
import com.app.electricstations.util.Constants
import com.app.electricstations.util.SharedPrefsUtil
import com.app.electricstations.util.network.INetworkDataListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [PerformanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerformanceFragment : Fragment(), OnMapReadyCallback,INetworkDataListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private var courseRequests: CourseRequests? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_performance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLocationPermissionGranted()
        getElectricStations()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    private fun getElectricStations() {

        val city1 = SharedPrefsUtil.getString(requireContext(), "city1", "")
        val city2 = SharedPrefsUtil.getString(requireContext(), "city2", "")
        val road = SharedPrefsUtil.getString(requireContext(), "road", "")
        val userCurr = SharedPrefsUtil.getString(requireContext(), "userCurr", "0")
        val userMax = SharedPrefsUtil.getString(requireContext(), "userMax", "0")

        val routeReq = RouteRequest(city1,city2,road, userCurr.toLong(),userMax.toLong())
        callRequest(routeReq)

    }

    private fun callRequest(routeReq: RouteRequest) {
        courseRequests = CourseRequests(requireContext(), Constants.GET_COURSES_REQUEST)
        courseRequests!!.iNetworkDataListener = this
        courseRequests!!.makeRequest(routeRequest = routeReq)
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            false
        } else {
            true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(mMap: GoogleMap) {
        //Grant permission for user location
        this.mMap = mMap
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        if (isLocationPermissionGranted())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    updateMapLocation(location)
                }
    }
    private fun updateMapLocation(location: Location?) {
        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    location?.latitude ?: 0.0,
                    location?.longitude ?: 0.0
                )
            )
        )

        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
    }
    override fun responseDone(response: Any, requestID: Int) {
        val stations = (response as RouteResponse).stations_on_route
        for(station in stations){
            // Creating a marker
            val markerOptions = MarkerOptions()
            // Setting the position for the marker
            val latLng = LatLng(station.coordinate.lat.toDouble()
                ,station.coordinate.long.toDouble())
            markerOptions.position(latLng)
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(station.title)
            mMap.addMarker(markerOptions)
        }
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


    override fun stopLoading() {

    }
}