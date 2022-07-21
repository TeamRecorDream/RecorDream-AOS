package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.FragmentGalleryTypeBinding
import and.org.recordream.presentation.storage.adapter.GalleryTypeAdapter
import and.org.recordream.util.enqueueUtil
import android.os.Bundle
import android.util.Log
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
        initNetwork()
        return binding.root
    }
// 인텐트전달만 하고, 각 요소별로 넣기만하면 보관함끝

    private fun initNetwork() {
        val selectedEmotion = arguments?.getInt("emotion")

        Log.d("q2fsfqlnekhqhkvhklwvhwlkvhwv", "$selectedEmotion")

        selectedEmotion?.let { RecordreamClient.storageService.getMyRecord(it, 1) }?.enqueueUtil(
            onSuccess = {
                it.data?.let { it1 -> addItem(it1.records) }
                Log.d("******status******", "${it.status}")
            })
    }


    private fun initAdapter() {
        galleryTypeAdapter = GalleryTypeAdapter {

        }
        binding.rvStorageGallery.adapter = galleryTypeAdapter
        addItemList()
    }

    private fun addItem(data: List<Record>) {
        galleryTypeAdapter.galleryRecords = data as MutableList<Record>
        galleryTypeAdapter.notifyDataSetChanged()
    }

    private fun addItemList() {
        galleryTypeAdapter.galleryRecords.addAll(
            listOf<Record>(
                Record("wdwdddw", "12121", 2, 2, listOf(2, 4), "232")

            )
        )
    }
}
