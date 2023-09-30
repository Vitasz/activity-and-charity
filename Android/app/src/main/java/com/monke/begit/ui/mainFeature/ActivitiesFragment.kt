package com.monke.begit.ui.mainFeature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.begit.R

class ActivitiesFragment : Fragment() {

    companion object {
        fun newInstance() = ActivitiesFragment()
    }

    private lateinit var viewModel: ActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}