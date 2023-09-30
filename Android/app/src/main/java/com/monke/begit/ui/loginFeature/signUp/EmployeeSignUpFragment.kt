package com.monke.begit.ui.loginFeature.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.begit.R

class EmployeeSignUpFragment : Fragment() {

    companion object {
        fun newInstance() = EmployeeSignUpFragment()
    }

    private lateinit var viewModel: EmployeeSignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmployeeSignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}