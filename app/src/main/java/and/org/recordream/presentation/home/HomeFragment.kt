package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.databinding.FragmentHomeBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class HomeFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homePagerRecyclerAdapter: HomePagerRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.lifecycle?.removeObserver(this)
        homePagerRecyclerAdapter = HomePagerRecyclerAdapter()
        binding.vpHome.adapter = homePagerRecyclerAdapter
        var homeList = mutableListOf<ResponseHomeItems.Records>(

        )
        // RecyclerView.Adapter<ViewHolder>()
        // ViewPager의 Paging 방향은 Horizontal
        binding.vpHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 관리하는 페이지 수. default = 1
        binding.vpHome.offscreenPageLimit = 4
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages =
            resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        binding.vpHome.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

        binding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position + 1}")
            }
        })
    }
}
