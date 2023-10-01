package com.monke.begit.ui.loginFeature.signUp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.monke.begit.App
import com.monke.begit.R
import com.monke.begit.databinding.FragmentSupervisorSignUpBinding
import com.monke.begit.domain.exceptions.IncorrectPasswordException
import com.monke.begit.domain.exceptions.NoUserFoundException
import com.monke.begit.ui.uiModels.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SupervisorSignUpFragment : Fragment() {


    @Inject
    lateinit var factory: SupervisorSignUpViewModel.Factory
    private val viewModel: SupervisorSignUpViewModel by viewModels { factory }

    private var binding: FragmentSupervisorSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupervisorSignUpBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            viewModel.signUp()
        }

        setupNameEditText()
        setupPasswordEditText()
        setupSurnameEditText()
        setupRepeatedPasswordEditText()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        // В случае ошибки показывает toast
                        is UiState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                state.exception.message,
                                Toast.LENGTH_SHORT).show()
                        }
                        // В случае загрузки показывает диалог с прогрессом
                        UiState.Loading -> {
                            //showLoadingDialog()
                        }
                        // В случае успеха переходит на следующий фрагмет
                        is UiState.Success -> {
                            view.findNavController()
                                .navigate(R.id.action_supervisorSignUpFragment_to_mainFragment)
                        }
                        // При отсутствующем состоянии ничего не делает
                        null -> {}
                    }
                }
            }
        }
    }

    private fun setupNameEditText() {
        binding?.inputName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.name = text.toString() }
            }
        })
    }

    private fun setupSurnameEditText() {
        binding?.inputSurname?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.surname = text.toString() }
            }
        })
    }

    private fun setupPasswordEditText() {
        binding?.inputPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.password = text.toString() }
            }
        })
    }

    private fun setupRepeatedPasswordEditText() {
        binding?.inputRepeatedPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.repeatedPassword = text.toString() }
            }
        })
    }



}