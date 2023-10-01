package com.monke.begit.ui.mainFeature.fundFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.begit.App
import com.monke.begit.databinding.FragmentSerchFundBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFundFragment : Fragment() {


    @Inject
    lateinit var factory: SearchFundViewModel.Factory

    private val viewModel: SearchFundViewModel by viewModels { factory }

    private var binding: FragmentSerchFundBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSerchFundBinding.inflate(inflater, container, false)
        (activity?.application as App).appComponent.inject(this)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FundRW(viewModel.funds.value)
        binding?.listFunds?.adapter = adapter
        binding?.listFunds?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        setupSearchEditText()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.funds.collect {
                    adapter.setItems(it)
                }
            }
        }
    }

    private fun setupSearchEditText() {
        val titleEditText = binding?.editTextFund
        titleEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.searchFund(it.toString()) }
            }
        })

    }



}