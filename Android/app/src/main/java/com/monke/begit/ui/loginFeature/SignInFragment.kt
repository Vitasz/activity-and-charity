package com.monke.begit.ui.loginFeature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {


    private lateinit var viewModel: SignInViewModel

    private var binding: FragmentSignInBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }


}