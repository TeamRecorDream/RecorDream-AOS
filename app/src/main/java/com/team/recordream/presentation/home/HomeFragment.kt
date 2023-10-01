package com.team.recordream.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.team.recordream.R
import com.team.recordream.databinding.FragmentHomeBinding
import com.team.recordream.presentation.common.BindingFragment
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.presentation.home.adapter.HomeAdapter
import com.team.recordream.presentation.home.model.UserRecords
import com.team.recordream.util.UiState
import com.team.recordream.util.ZoomOutPageTransformer
import com.team.recordream.util.makeSnackBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(::navigateToDetailView) }
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapterHomeCard()
        setupBinding()
        observeState()
        observeNickName()
    }

    private val focusOnFirstItemLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result here if needed (e.g., result from another activity)

        // Focus on the first item of the ViewPager2
        binding.vpHome.setCurrentItem(0, false) // Scroll to the first item and disable smooth scroll
    }


    private fun observeState() {
        homeViewModel.userRecords.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.updateHome()
        Log.d("why??", "뭐야")
        //        맨앞 카드로 설정
//        binding.vpHome.setCurrentItem(0, false)
    }

    private fun observeNickName() {
        homeViewModel.userName.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.lvStorageLottieLoading.pauseAnimation()
                    binding.lvStorageLottieLoading.visibility = View.INVISIBLE
                    binding.clLoadingBackground.visibility = View.INVISIBLE
                    if (homeViewModel.isRecordEmpty.value == true) {
                        binding.tvHomeUserNameNoneData.text =
                            getString(R.string.tv_main_welcoming, state.data)
                        binding.tvHomeUserNameNoneData.visibility = View.VISIBLE
                        binding.tvHomeUserName.visibility = View.INVISIBLE
                    } else {
                        binding.tvHomeUserName.visibility = View.VISIBLE
                        binding.tvHomeUserNameNoneData.visibility = View.INVISIBLE
                        binding.tvHomeUserName.text =
                            getString(R.string.tv_main_welcoming, state.data)
                    }
                }

                is UiState.Failure -> {
                    view?.makeSnackBar(R.string.network_error)
                }

                is UiState.Loading -> {
                    binding.lvStorageLottieLoading.playAnimation()
                    binding.clLoadingBackground.visibility = View.VISIBLE
                    binding.lvStorageLottieLoading.visibility = View.VISIBLE
                }

                is UiState.Empty -> {}
            }
        }
    }

    private fun initAdapterHomeCard() {
        with(binding.vpHome) {
            adapter = homeAdapter
            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val ratio: Double = 264 / 360.0 // 맨앞에 아이템이 차지하는 width
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 8 // 사이드로 밀어지는놈
            // 맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // 리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            offscreenPageLimit = 1
            setPadding(pagePadding, 0, pagePadding, 0) // 패딩 값 코드단에서 주기
            setPageTransformer(
                CompositePageTransformer().apply {
                    addTransformer(ZoomOutPageTransformer())
                    addTransformer { page, position ->
                        page.translationX = position * -(innerPadding)
                    }
                }
            )
        }
    }

    private fun navigateToDetailView(userRecord: UserRecords) {
        startActivity(DetailActivity.getIntent(requireContext(), userRecord.id))
    }
}
