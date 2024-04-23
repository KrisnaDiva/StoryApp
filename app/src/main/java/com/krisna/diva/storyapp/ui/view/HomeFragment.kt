package com.krisna.diva.storyapp.ui.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.storyapp.data.Result
import com.krisna.diva.storyapp.databinding.FragmentHomeBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.view.adapter.StoryAdapter
import com.krisna.diva.storyapp.ui.viewmodel.HomeViewModel
import com.krisna.diva.storyapp.utils.showLoading
import com.krisna.diva.storyapp.utils.showSnackBar
import com.krisna.diva.storyapp.utils.showToast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyAdapter = StoryAdapter()

        binding.rvStory.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = storyAdapter
        }

        viewModel.listStory.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressIndicator.showLoading(true)
                    }

                    is Result.Success -> {
                        binding.progressIndicator.showLoading(false)
                        val stories = result.data
                        storyAdapter.submitList(stories.listStory)
                    }

                    is Result.Error -> {
                        requireContext().showToast(result.error)
                        binding.progressIndicator.showLoading(false)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}