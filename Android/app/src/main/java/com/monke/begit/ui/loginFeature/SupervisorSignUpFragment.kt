package com.monke.begit.ui.loginFeature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSupervisorSignUpBinding

class SupervisorSignUpFragment : Fragment() {


    private lateinit var viewModel: SupervisorSignUpViewModel
    private var binding: FragmentSupervisorSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_supervisor_sign_up, container, false)
    }

}