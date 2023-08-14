package com.team.recordream.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.team.recordream.data.entity.remote.response.ResponseHome
import com.team.recordream.databinding.FragmentHomeBinding
import com.team.recordream.presentation.detail.DetailActivity
import com.team.recordream.util.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), LifecycleObserver {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.initServer()
        homeViewModel.getUser()
        applyNickname(homeViewModel.homeRecords)
        initAdapterHomeCard()
        observeData()
        initRefresh()
    }

    private fun observeData() {
        homeViewModel.homeRecords.observe(viewLifecycleOwner) {
//            homeViewPagerAdapter.updateList(it)
        }

        homeViewModel.userName.observe(viewLifecycleOwner) {
            applyNickname(homeViewModel.homeRecords)
            binding.tvHomeHi1.text = "반가워요, ${homeViewModel.userName.value}님!"
            binding.tvHomeHiOff.text = "반가워요, ${homeViewModel.userName.value}님!"
        }
    }

    private fun initAdapterHomeCard() {
        homeViewPagerAdapter = HomeViewPagerAdapter { recordId ->
            val intent = DetailActivity.getIntent(requireContext(), recordId.id)
            startActivity(intent)
//            val intent = Intent(requireContext(), DocumentActivity::class.java)
//            intent.apply { it.id }
//            startActivity(intent)
        }
        binding.vpHome.adapter = homeViewPagerAdapter
        with(binding.vpHome) {
            adapter = homeViewPagerAdapter
            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val ratio: Double = 264 / 360.0 // 맨앞에 아이템이 차지하는 width
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 8 // 사이드로 밀어지는놈
            // 맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // 리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed
            offscreenPageLimit = 1
            setPadding(pagePadding, 0, pagePadding, 0) // 패딩 값 코드단에서 주기
            setPageTransformer(
                CompositePageTransformer().apply {
                    addTransformer(ZoomOutPageTransformer())
                    addTransformer { page, position ->
                        page.translationX = position * -(innerPadding)
                    }
                },
            )
        }
    }

    private fun initRefresh() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            observeData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun applyNickname(response: LiveData<List<ResponseHome.Record>>?) {
        if (response != null) {
            if (response.value?.size != null) {
                binding.tvHomeHi1.visibility = View.VISIBLE
                binding.tvHomeHi2.visibility = View.VISIBLE
                binding.tvHomeHiOff.visibility = View.INVISIBLE
                binding.tvHomeHiOff2.visibility = View.INVISIBLE
            } else {
                binding.tvHomeHi1.visibility = View.INVISIBLE
                binding.tvHomeHi2.visibility = View.INVISIBLE
                binding.tvHomeHiOff.visibility = View.VISIBLE
                binding.tvHomeHiOff2.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
