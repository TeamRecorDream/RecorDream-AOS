package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.FragmentGalleryTypeBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.presentation.storage.adapter.GalleryTypeAdapter
import and.org.recordream.util.enqueueUtil
import android.content.Intent
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
    // private var oneLineCallback: ((List<String>) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryTypeBinding.inflate(layoutInflater, container, false)

        binding.textView.setOnClickListener {
            initNetwork()
//            StorageFragment().setCallbackButtonClickListener {
//                Log.d("123123123123321213312", "$it")


            // }
        }
        //initNetwork()
        initAdapter()


        return binding.root
    }


    private fun initNetwork() {
        val selectedEmotion = arguments?.getInt("emotion")

        Log.d("q2fsfqlnekhqhkvhklwvhwlkvhwv", "$selectedEmotion")

        selectedEmotion?.let { RecordreamClient.storageService.getMyRecord(it, 1) }?.enqueueUtil(
            onSuccess = {
                it.data?.let { _it ->
                    addItemList(_it.records)
                }
                val a = it.data?.records[].genre[]
                Log.d("******status******", "${it.status}")

            })
    }


    private fun initAdapter() {
        galleryTypeAdapter = GalleryTypeAdapter {
            toDetailView(it.id)
        }
        binding.rvStorageGallery.adapter = galleryTypeAdapter

    }

    private fun addItemList(data: List<Record>) {
        data.galleryTypeAdapter.galleryRecords = data as MutableList<Record>
        galleryTypeAdapter.notifyDataSetChanged()
    }

    private fun toDetailView(id: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.apply {
            putExtra("id", id)
        }
        startActivity(intent)
    }

    private fun genreTag() {

    }


}
