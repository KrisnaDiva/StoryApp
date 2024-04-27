package com.krisna.diva.storyapp.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.storyapp.R
import com.krisna.diva.storyapp.data.ResultState
import com.krisna.diva.storyapp.databinding.FragmentHomeBinding
import com.krisna.diva.storyapp.ui.ViewModelFactory
import com.krisna.diva.storyapp.ui.view.adapter.StoryAdapter
import com.krisna.diva.storyapp.ui.viewmodel.HomeViewModel
import com.krisna.diva.storyapp.util.NetworkUtils
import com.krisna.diva.storyapp.util.showLoading
import com.krisna.diva.storyapp.util.showToast

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

        storyAdapter = StoryAdapter { story, optionsCompat ->
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_STORY, story)
            }, optionsCompat.toBundle())
        }

        binding.rvStory.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = storyAdapter
        }
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.no_internet)
                .setMessage(R.string.no_internet_description)
                .setPositiveButton(R.string.ok) { _, _ ->
                    requireActivity().finish()
                }
                .show()
        } else {
            viewModel.listStory.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is ResultState.Success -> {
                            binding.progressIndicator.showLoading(false)
                            val stories = result.data
                            storyAdapter.submitList(stories)
                        }

                        is ResultState.Error -> {
                            requireContext().showToast(result.error)
                            binding.progressIndicator.showLoading(false)
                        }

                        is ResultState.Empty -> {
                            requireContext().showToast(R.string.result_empty.toString())
                            binding.progressIndicator.showLoading(false)
                        }
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