package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.databinding.FragmentStorageBinding
import and.org.recordream.presentation.storage.adapter.StorageAdapter
import and.org.recordream.presentation.storage.myrecord.GalleryTypeFragment
import and.org.recordream.presentation.storage.myrecord.ListTypeFragment
import and.org.recordream.util.enqueueUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")
    private lateinit var storageAdapter: StorageAdapter
    private var emotionNum = 0 // 전역으로 이모션넘버


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
        initNetwork(0)
        showMyRecord()
        setUpRecyclerView()

        return binding.root
    }


    private fun initNetwork(selected: Int) {

        val call = RecordreamClient.storageService.getMyRecord(selected, 1)

        call.enqueueUtil(
            onSuccess = {
                binding.tvStorageCount.text = it.data?.recordTotal.toString()
                Log.d("******status******", "${it.status}")
            })
    }

    private fun setUpRecyclerView() {
        binding.rvStorageMyemotion.apply {
            storageAdapter = StorageAdapter {
                Log.d("******emotion******", "${it.emotion}")
                val emotionInt = emotionMapping(it.emotion)
                Log.d("******emotionInt******", "$emotionInt")
                initNetwork(emotionInt)

                emotionNum = emotionInt
            }
            adapter = storageAdapter
        }
        addItemList()
    }

    private fun emotionMapping(emotion: Int): Int { // 카테고리별 값 mapping
        var emotionInt: Int = 0
        when (emotion) {
            2131165486 -> emotionInt = 0
            2131165491 -> emotionInt = 1
            2131165492 -> emotionInt = 2
            2131165488 -> emotionInt = 3
            2131165490 -> emotionInt = 4
            2131165489 -> emotionInt = 5
            2131165487 -> emotionInt = 6
            2131165493 -> emotionInt = 7
        }
        return emotionInt
    }

    private fun addItemList() { // 나의 감정 카테고리 데이터
        storageAdapter.myEmotionList.addAll(
            listOf<MyEmotionData>(
                MyEmotionData(R.drawable.selector_storage_emotion_all, true),
                MyEmotionData(R.drawable.selector_storage_emotion_smile, false),
                MyEmotionData(R.drawable.selector_storage_emotion_surprise, false),
                MyEmotionData(R.drawable.selector_storage_emotion_love, false),
                MyEmotionData(R.drawable.selector_storage_emotion_shy, false),
                MyEmotionData(R.drawable.selector_storage_emotion_sad, false),
                MyEmotionData(R.drawable.selector_storage_emotion_angry, false),
                MyEmotionData(R.drawable.selector_storage_emotion_unclassified, false)
            )
        )
    }


    private fun showMyRecord() { // 갤러리형, 목록형 프래그먼트 관리
        val listTypeFragment = ListTypeFragment()
        val galleryTypeFragment = GalleryTypeFragment()

        childFragmentManager.beginTransaction() // 데이터전달 fragment to fragment, bundle
            .add(R.id.fcv_storage_myrecord, galleryTypeFragment.apply {
                arguments = Bundle().apply {
                    putInt("emotion", emotionNum)
                }
            }).commit()

        with(binding) {
            ivStorageGallery.isSelected = true

            ivStorageGallery.setOnClickListener {
                ivStorageGallery.isSelected = true
                ivStorageList.isSelected = false

                childFragmentManager.beginTransaction().replace(
                    R.id.fcv_storage_myrecord,
                    galleryTypeFragment
                ).commit()
            }

            ivStorageList.setOnClickListener {
                ivStorageList.isSelected = true
                ivStorageGallery.isSelected = false

                childFragmentManager.beginTransaction().replace(
                    R.id.fcv_storage_myrecord,
                    listTypeFragment
                ).commit()
            }
        }
    }
}

