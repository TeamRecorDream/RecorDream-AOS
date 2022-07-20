package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.databinding.FragmentStorageBinding
import and.org.recordream.presentation.storage.adapter.StorageAdapter
import and.org.recordream.presentation.storage.myrecord.GalleryTypeFragment
import and.org.recordream.presentation.storage.myrecord.ListTypeFragment
import and.org.recordream.util.RecordreamMapping
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
    val recordreamMapping = RecordreamMapping()

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
        initNetwork()
        return binding.root
    }

    private fun initNetwork() {

        val call = RecordreamClient.storageService.getMyRecord(3, 1)

        call.enqueueUtil(
            onSuccess = {

                // val name = it.data?.get(0)
                Log.d("ddddddd", "${it.status}")
            })
    }

    private fun setUpRecyclerView() {
        binding.rvStorageMyemotion.apply {
            storageAdapter = StorageAdapter {
                val emotionInt = emotionMapping(it.emotion)
                //initNetwork(emotionInt)
                Log.d("dddd", "$it.emotion")
                // it을 활용한 람다표현식, 고차함수 등 함수구현가능
                //storageAdapter.notifyDataSetChanged()
            }
            adapter = storageAdapter
        }
        addItemList()
    }

    private fun emotionMapping(emotion: Int): Int {
        var emotionInt: Int = 7
        when (emotion) {
            2131165404 -> emotionInt = 0
            2131165409 -> emotionInt = 1
            2131165410 -> emotionInt = 2
            2131165406 -> emotionInt = 3
            2131165408 -> emotionInt = 4
            2131165407 -> emotionInt = 5
            2131165405 -> emotionInt = 6
            2131165411 -> emotionInt = 7
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
}
//
//package and.org.recordream.presentation.storage
//
//import and.org.recordream.R
//import and.org.recordream.data.local.MyEmotionData
//import and.org.recordream.data.remote.RecordreamClient
//import and.org.recordream.databinding.FragmentStorageBinding
//import and.org.recordream.presentation.storage.adapter.StorageAdapter
//import and.org.recordream.presentation.storage.myrecord.GalleryTypeFragment
//import and.org.recordream.presentation.storage.myrecord.ListTypeFragment
//import and.org.recordream.util.RecordreamMapping
//import and.org.recordream.util.enqueueUtil
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//
//class StorageFragment : Fragment() {
//    private var _binding: FragmentStorageBinding? = null
//    private val binding get() = _binding ?: error("Binding is not initialization")
//    private val storageAdapter by lazy { StorageAdapter() }
//    val recordreamMapping = RecordreamMapping()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
////        context?.let { ContextCompat.getDrawable(it,R.drawable.logo) }
////            ?.let { binding.tvStorageMyemotion.background = it }
////        getInfo()
////        initAdapter()
////        addItemList()
//        showMyRecord()
//        setUpRecyclerView()
//
//        return binding.root
//    }
//
//    private fun initNetwork(selectedEmotion: Int) {
//        val call = RecordreamClient.customRetrofit.getMyRecord(selectedEmotion)
//
//        call.enqueueUtil(
//            onSuccess = {
//                // val name = it.data?.get(0)
//                Log.d("ddddddd", "123123")
//            })
//    }
//
//    private fun setUpRecyclerView() {
//        binding.rvStorageMyemotion.adapter = storageAdapter
//        addItemList()
//    }
//
//    private fun emotionMapping(emotion: Int): Int {
//        var emotionInt: Int = 7
//        when (emotion) {
//            2131165404 -> emotionInt = 0
//            2131165409 -> emotionInt = 1
//            2131165410 -> emotionInt = 2
//            2131165406 -> emotionInt = 3
//            2131165408 -> emotionInt = 4
//            2131165407 -> emotionInt = 5
//            2131165405 -> emotionInt = 6
//            2131165411 -> emotionInt = 7
//        }
//        return emotionInt
//    }
//
//
//    private fun addItemList() { // 나의 감정 카테고리 데이터
//        storageAdapter.myEmotionList.addAll(
//            listOf<MyEmotionData>(
//                MyEmotionData(R.drawable.selector_storage_emotion_all, true),
//                MyEmotionData(R.drawable.selector_storage_emotion_smile, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_surprise, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_love, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_shy, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_sad, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_angry, false),
//                MyEmotionData(R.drawable.selector_storage_emotion_unclassified, false)
//            )
//        )
//    }
//
//    private fun showMyRecord() { // 갤러리형, 목록형 프래그먼트 관리
//        val listTypeFragment = ListTypeFragment()
//        val galleryTypeFragment = GalleryTypeFragment()
//
//        childFragmentManager.beginTransaction().add(R.id.fcv_storage_myrecord, galleryTypeFragment)
//            .commit()
//
//        with(binding) {
//            ivStorageGallery.isSelected = true
//
//            ivStorageGallery.setOnClickListener {
//                ivStorageGallery.isSelected = true
//                ivStorageList.isSelected = false
//
//                childFragmentManager.beginTransaction().replace(
//                    R.id.fcv_storage_myrecord,
//                    galleryTypeFragment
//                ).commit()
//            }
//
//            ivStorageList.setOnClickListener {
//                ivStorageList.isSelected = true
//                ivStorageGallery.isSelected = false
//
//                childFragmentManager.beginTransaction().replace(
//                    R.id.fcv_storage_myrecord,
//                    listTypeFragment
//                ).commit()
//            }
//        }
//    }
//}
//
