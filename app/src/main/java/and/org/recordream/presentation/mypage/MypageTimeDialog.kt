package and.org.recordream.presentation.mypage

import and.org.recordream.R
import and.org.recordream.databinding.FragmentMypageTimedialogBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class mypage_timedialog : Fragment() {
    private var _binding: FragmentMypageTimedialogBinding? = null
    private val binding get() = _binding ?: error("binding이 연결되지 않음")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageTimedialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    private fun numberpickerClick(){
        binding.timeDatepicker.minValue = 0
        binding.minuteDatepicker.maxValue = 12
    }


}