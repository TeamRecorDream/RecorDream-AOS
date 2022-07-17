package and.org.recordream.presentation.mypage//package before.forget.feature.write

import and.org.recordream.databinding.FragmentMypageBottomSheetBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WriteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMypageBottomSheetBinding? = null
    private val binding get() = _binding ?: error("바인딩에 NULL 값이 들어갔어요!!")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBottomSheetBinding.inflate(inflater, container, false)
        amOrpmSettiing()

        return binding.root
    }

    private fun amOrpmSettiing() {
        var amOrpm = ""
        var min = 0
        val str = arrayOf<String>("AM", "PM")
        binding.npDatepickerAmorpm.maxValue = 0
        binding.npDatepickerAmorpm.maxValue = (str.size - 1)
        binding.npDatepickerAmorpm.displayedValues = str

        binding.npDatepickerAmorpm.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            amOrpm = str[i]
        }
    }
}

//        binding.btnWriteApply.visibility = View.GONE
//        binding.clWriteResetbtn.visibility = View.GONE
//
//        /*  writeBadFragment.setCallbackButtonClickListener {
//              dismiss()
//          }*/
//
//        initAdapter()
//        initTabLayout()
//        test()
//        initDialog()
//        writeGoodFragment.setCallbackButtonClickListener { oneLine ->
//            oneLineCallback?.invoke(oneLine)
//            Log.d("액비티티에서", "오긴온거니 흑")
//            dismiss()
//        }
//
//        return binding.root
//    }
//
//    private fun initAdapter() { // 뷰페이저 어댑터
//        val fragmentList = listOf(writeGoodFragment, writeBadFragment)
//        writeViewPagerAdapter = WriteViewPagerAdapter(this)
//        writeViewPagerAdapter.fragments.addAll(fragmentList)
//        binding.vpWriteMenu.adapter = writeViewPagerAdapter
//    }
//
//    private fun initTabLayout() { // 탭레이아웃
//        val menuList = listOf("좋았어요", "아쉬워요")
//        TabLayoutMediator(binding.tlWriteMenu, binding.vpWriteMenu) { tab, position ->
//            tab.text = menuList[position]
//        }.attach()
//    }
//
//    private fun test() {
//        binding.btnWriteApply.setOnClickListener {
//            val writeBottomSheetFragment = WriteBottomSheetFragment()
//            writeBottomSheetFragment.dismiss()
//            // 데이터전달
//        }
//    }
//
//    private fun initDialog() { // 바텀시트달기
//        val bottomSheetDialog = BottomSheetDialog(requireContext())
//        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }

