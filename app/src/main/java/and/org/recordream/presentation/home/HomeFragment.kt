package and.org.recordream.presentation.home

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.ResponseHomeItems
import and.org.recordream.data.remote.response.ResponseHomeRecord
import and.org.recordream.databinding.FragmentHomeBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.util.RecordreamMapping
import and.org.recordream.util.ZoomOutPageTransformer
import and.org.recordream.util.enqueueUtil
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer

class HomeFragment : Fragment(), LifecycleObserver {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private val binding get() = _binding!!
    private val recorDreamMapping = RecordreamMapping()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

//        binding.tvHomeHi.setOnClickListener {
//            activity?.let {
//                val intent = Intent(context, DetailActivity::class.java)
//                startActivity(intent)
//            }
//        }

        initAdapterHomeCard()
        initNetwork()
        return binding.root
    }


    private fun initAdapterHomeCard() {
        homeViewPagerAdapter = HomeViewPagerAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("id", it)
            }
            startActivity(intent)
        }
        binding.vpHome.adapter = homeViewPagerAdapter
//        homeViewPagerAdapter.homeCardList.addAll(
//            listOf<ResponseHomeRecord>(
//
//            )
//        )

        with(binding.vpHome) {
            adapter = homeViewPagerAdapter
            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val ratio: Double = 264 / 360.0 // ????????? ???????????? ???????????? width
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 8 // ???????????? ???????????????
            //??? ????????? ??? ?????? ?????? ???????????? ????????? ?????? ??? ?????? ???????????? ?????????
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // ???????????????????????? ?????? ???????????? ???????????? ???????????? ????????? ??????????????? ???????????? ????????????. ??? ?????? ??????????????? ????????? ??? ??????????????? ?????????.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed
            offscreenPageLimit = 1
            setPadding(pagePadding, 0, pagePadding, 0) //?????? ??? ??????????????? ??????
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(ZoomOutPageTransformer())
                addTransformer { page, position -> page.translationX = position * -(innerPadding) }
            })
        }
    }

    private fun initNetwork() {//        val requestDetail = RequestDetailDreamRecord(
//            recordId = "62d16e7fe8b4508dbca5ead6"
//        )
        val recordId = "62d7b6f19669f53b6c72a89f"
//        Log.d("dddddddddd", "wddddddddd123123ddddd")
        val call = RecordreamClient.homeService.getHomeRecord()

        call.enqueueUtil(
            onSuccess = {
                Log.d("??????????????????, status", "${it.status}")

                val data = it.data
                val recordData =
                    applyNickname(data)
                it.data?.let { data ->
                    addHomeCardList(data.records)
                    Log.d("???????????????", "${data.records}")

                }
            },
            onError = {
                Log.d("ddddddd1234", "$it")
            }
        )
    }

    private fun applyNickname(response: ResponseHomeItems?) {
        if (response != null) {
            if (response.records.size != null) {
                binding.tvHomeHi.visibility = View.VISIBLE
                binding.tvHomeHi2.visibility = View.VISIBLE
                binding.tvHomeOffHi.visibility = View.INVISIBLE
                binding.tvHomeOffHi2.visibility = View.INVISIBLE
                if (response != null) {
                    binding.tvHomeHi.text = "????????????, ${response.nickname}???!"
                }
            } else {
                binding.tvHomeHi.visibility = View.INVISIBLE
                binding.tvHomeHi2.visibility = View.INVISIBLE
                binding.tvHomeOffHi.visibility = View.VISIBLE
                binding.tvHomeOffHi2.visibility = View.VISIBLE
                if (response != null) {
                    binding.tvHomeOffHi.text = "????????????, ${response.nickname}???!"
                }
            }
        }
    }

    private fun addHomeCardList(data: List<ResponseHomeRecord>) {
        (binding.vpHome.adapter as HomeViewPagerAdapter).updateList(data.toMutableList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        activity?.lifecycle?.removeObserver(this)
//        homePagerRecyclerAdapter = HomePagerRecyclerAdapter()
//        binding.vpHome.adapter = homePagerRecyclerAdapter
//        var homeList = mutableListOf<ResponseHomeItems.Records>(
//
//        )
//
//        // RecyclerView.Adapter<ViewHolder>()
//        // ViewPager??? Paging ????????? Horizontal
//        binding.vpHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        // ???????????? ????????? ???. default = 1
//        binding.vpHome.offscreenPageLimit = 4
//        // item_view ?????? ??? ??? ????????? ????????? ???
//        val offsetBetweenPages =
//            resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
//        binding.vpHome.setPageTransformer { page, position ->
//            val myOffset = position * -(2 * offsetBetweenPages)
//            if (position < -1) {
//                page.translationX = -myOffset
//            } else if (position <= 1) {
//                // Paging ??? Y??? Animation ???????????? ?????? ????????? ??????
//                val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
//                page.translationX = myOffset
//                page.scaleY = scaleFactor
//                page.alpha = scaleFactor
//            } else {
//                page.alpha = 0f
//                page.translationX = myOffset
//            }
//        }
//
//        binding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                Log.e("ViewPagerFragment", "Page ${position + 1}")
//            }
//        })
//    }
