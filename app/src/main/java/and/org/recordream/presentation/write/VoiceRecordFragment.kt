package and.org.recordream.presentation.write

import and.org.recordream.databinding.FragmentVoiceRecordBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class VoiceRecordFragment : Fragment() {
    private var _binding: FragmentVoiceRecordBinding? = null
    private val binding get() = _binding ?: error("바인딩에 NULL 값이 들어갔어요!!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoiceRecordBinding.inflate(inflater, container, false)
        return binding.root
    }
}
