package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.R
import and.org.recordream.data.remote.response.ResponseRecords
import and.org.recordream.databinding.FragmentGalleryTypeBinding
import and.org.recordream.databinding.FragmentListTypeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GalleryTypeFragment : Fragment() {
    private var _binding: FragmentGalleryTypeBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")
    private lateinit var galleryTypeAdapter: GalleryTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryTypeBinding.inflate(layoutInflater, container, false)

        initAdapter()
        return binding.root
    }

    private fun initAdapter(){
        galleryTypeAdapter = GalleryTypeAdapter {  }
        binding.rvStorageGallery.adapter = galleryTypeAdapter
        addItemList()
    }
    private fun addItemList(){
        galleryTypeAdapter.galleryRecords.addAll(
            listOf<ResponseRecords>(
                ResponseRecords(["22"])
            )
        )
    }

}
