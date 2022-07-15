package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.databinding.FragmentStorageBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)


//        context?.let { ContextCompat.getDrawable(it,R.drawable.logo) }
//            ?.let { binding.tvStorageMyemotion.background = it }


        showMyRecord()
        return binding.root
    }

    private fun showMyRecord() {
        // java.lang.IllegalArgumentException: Navigation action/destination
        // currentDestination 확인해보기
        binding.ivStorageList.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.galleryFragment)
                findNavController().navigate(
                    R.id.action_galleryFragment_to_listFragment
                )
        }
    }
}
