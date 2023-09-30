package com.monke.begit.ui.loginFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSignInBinding
import com.monke.begit.databinding.FragmentStartBinding
import javax.inject.Inject

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSignUpBtn()
    }

    private fun setupSignUpBtn() {
        binding?.btnSignUp?.setOnClickListener {
            it.findNavController().navigate(R.id.action_startFragment_to_signUpFragment)
        }
    }

}