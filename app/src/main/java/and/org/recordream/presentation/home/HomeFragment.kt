package and.org.recordream.presentation.home

import and.org.recordream.databinding.FragmentHomeBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.util.ZoomOutPageTransformer
import android.content.Intent
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.tvHomeHi.setOnClickListener {
            activity?.let {
                val intent = Intent(context, DetailActivity::class.java)
                startActivity(intent)
            }
        }

        initAdapterHomeCard()
        return binding.root
    }


    private fun initAdapterHomeCard() {
        homeViewPagerAdapter = HomeViewPagerAdapter()
        homeViewPagerAdapter.homeCardList.addAll(
            listOf(
                //Record("333", "2022/04/06 (일)", 3, 1,[1, 2, 3], "우와아앙아 테스트다!!!!!")
            )
        )

        with(binding.vpHome) {
            adapter = homeViewPagerAdapter
            val display = activity?.applicationContext?.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val ratio: Double = 264 / 360.0 // 맨앞에 아이템이 차지하는 width
            val pageWidth = ratio * deviceWidth!!
            val pagePadding = ((deviceWidth - pageWidth) / 2).toInt()
            val innerPadding = pagePadding / 8 // 사이드로 밀어지는놈
            //맨 위에서 더 이상 위로 스크롤할 영역이 없을 때 위로 땡겨지지 않도록
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            // 리사이클러뷰에서 현재 보고있는 아이템의 양쪽으로 지정한 숫자만큼의 아이템을 유지한다. 그 밖의 아이템들은 필요할 때 어댑터에서 만든다.
            // Set the number of pages that should be retained to either side of the currently visible page(s). Pages beyond this limit will be recreated from the adapter when needed
            offscreenPageLimit = 1
            setPadding(pagePadding, 0, pagePadding, 0) //패딩 값 코드단에서 주기
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(ZoomOutPageTransformer())
                addTransformer { page, position -> page.translationX = position * -(innerPadding) }
            })
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
//        // ViewPager의 Paging 방향은 Horizontal
//        binding.vpHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        // 관리하는 페이지 수. default = 1
//        binding.vpHome.offscreenPageLimit = 4
//        // item_view 간의 양 옆 여백을 상쇄할 값
//        val offsetBetweenPages =
//            resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
//        binding.vpHome.setPageTransformer { page, position ->
//            val myOffset = position * -(2 * offsetBetweenPages)
//            if (position < -1) {
//                page.translationX = -myOffset
//            } else if (position <= 1) {
//                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
