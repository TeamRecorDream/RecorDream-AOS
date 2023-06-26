package com.recodream_aos.recordream.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.recodream_aos.recordream.data.entity.remote.response.ResponseHome
import com.recodream_aos.recordream.databinding.FragmentHomeBinding
import com.recodream_aos.recordream.presentation.document.DocumentActivity
import com.recodream_aos.recordream.util.RecordreamMapping
import com.recodream_aos.recordream.util.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), LifecycleObserver {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private val binding get() = _binding!!
    private val recorDreamMapping = RecordreamMapping()
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
        initAdapterHomeCard()
        observeData()
    }

    private fun observeData() {
        homeViewModel.homeRecords.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.updateList(it)
        }
    }

    private fun initAdapterHomeCard() {
        homeViewPagerAdapter = HomeViewPagerAdapter {
            val intent = Intent(requireContext(), DocumentActivity::class.java).apply {
                putExtra("id", it)
            }
            startActivity(intent)
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

//    private fun initNetwork() {
//        val recordId = RetrofitModule.providesRetrofit()
//        //        Log.d("dddddddddd", "wddddddddd123123ddddd")
//        val call = HomeService.getHomeRecord()
//
//        call.enqueueUtil(
//            onSuccess = {
//                Log.d("홈프래그먼트, status", "${it.status}")
//
//                val data = it.data
//                val recordData =
//                    applyNickname(data)
//                it.data?.let { data ->
//                    addHomeCardList(data.records)
//                    Log.d("데이터체크", "${data.records}")
//                }
//            },
//            onError = {
//                Log.d("ddddddd1234", "$it")
//            }
//        )
//    }

    private fun applyNickname(response: ResponseHome?) {
        if (response != null) {
            if (response.records.size != null) {
                binding.tvHomeHi1.visibility = View.VISIBLE
                binding.tvHomeHi2.visibility = View.VISIBLE
                binding.tvHomeHiOff.visibility = View.INVISIBLE
                binding.tvHomeHiOff2.visibility = View.INVISIBLE
                if (response != null) {
                    binding.tvHomeHi1.text = "반가워요, ${response.nickname}님!"
                }
            } else {
                binding.tvHomeHi1.visibility = View.INVISIBLE
                binding.tvHomeHi2.visibility = View.INVISIBLE
                binding.tvHomeHiOff.visibility = View.VISIBLE
                binding.tvHomeHiOff2.visibility = View.VISIBLE
                if (response != null) {
                    binding.tvHomeHiOff.text = "반가워요, ${response.nickname}님!"
                }
            }
        }
    }

    private fun addHomeCardList(data: List<ResponseHome.Record>) {
        (binding.vpHome.adapter as HomeViewPagerAdapter).updateList(data.toMutableList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
