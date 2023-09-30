package com.monke.begit.ui.loginFeature.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.monke.begit.R
import com.monke.begit.databinding.FragmentEmployeeSignUpBinding

class EmployeeSignUpFragment : Fragment() {

    private var binding: FragmentEmployeeSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            it.findNavController().navigate(R.id.action_employeeSignUpFragment_to_mainFragment)
        }
    }


}