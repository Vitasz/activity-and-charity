package com.monke.begit.ui.loginFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.begit.R

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

}