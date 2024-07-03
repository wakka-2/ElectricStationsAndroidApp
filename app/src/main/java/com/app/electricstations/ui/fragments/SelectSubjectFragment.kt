package com.app.electricstations.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.app.electricstations.R
import com.app.electricstations.requests.CourseRequests
import com.app.electricstations.util.LoadingButton


class SelectSubjectFragment : Fragment() {

    private var courseRequests: CourseRequests? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val lottieView = view.findViewById<LottieAnimationView>(R.id.animation_view)
        val loadingBtn = view.findViewById<LoadingButton>(R.id.btnCheckMap)
        if(arguments != null) {
            val canTravel = arguments?.getBoolean("canTravel")

            if(canTravel!!){
                lottieView.setAnimation(R.raw.success)
            }else{
                lottieView.setAnimation(R.raw.failure)
            }
        }
        loadingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SelectSubjectFragment_to_performance)
        }
    }
}