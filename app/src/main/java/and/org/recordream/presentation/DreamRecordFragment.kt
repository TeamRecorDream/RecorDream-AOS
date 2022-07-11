package and.org.recordream.presentation

import and.org.recordream.databinding.FragmentDreamRecordBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class DreamRecordFragment : Fragment() {
    lateinit var binding: FragmentDreamRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDreamRecordBinding.inflate(inflater, container, false)

        return binding.root
    }
}