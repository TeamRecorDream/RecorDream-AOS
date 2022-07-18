package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.FragmentStorageBinding
import and.org.recordream.presentation.storage.adapter.StorageAdapter
import and.org.recordream.presentation.storage.myrecord.GalleryTypeFragment
import and.org.recordream.presentation.storage.myrecord.ListTypeFragment
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
//        context?.let { ContextCompat.getDrawable(it,R.drawable.logo) }
//            ?.let { binding.tvStorageMyemotion.background = it }
//        getInfo()
//        initAdapter()
//        addItemList()
        showMyRecord()
        setUpRecyclerView()

        return binding.root
    }


    private fun setUpRecyclerView() {
        binding.rvStorageMyemotion.apply {
            storageAdapter = StorageAdapter {

                Log.d("dddd", "$it.emotion")
                // it을 활용한 람다표현식, 고차함수 등 함수구현가능
                storageAdapter.notifyDataSetChanged()

            }
            adapter = storageAdapter
        }
        addItemList()
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

        childFragmentManager.beginTransaction().add(R.id.fcv_storage_myrecord, galleryTypeFragment)
            .commit()

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

    companion object {
        const val ALL = 2131165404
        const val SMILE = 2131165409
        const val SURPRISE = 2131165410
        const val LOVE = 2131165406
        const val SHY = 2131165408
        const val SAD = 2131165407
        const val ANGRY = 2131165405
        const val UNCLASSIFIED = 2131165411
    }
}
