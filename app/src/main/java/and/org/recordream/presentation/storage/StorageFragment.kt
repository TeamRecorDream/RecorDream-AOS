package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.databinding.FragmentStorageBinding
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)


        context?.let { ContextCompat.getDrawable(it,R.drawable.logo) }
            ?.let { binding.tvStorageMyemotion.background = it }


        return binding.root
    }
}
