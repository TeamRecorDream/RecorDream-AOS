package and.org.recordream.presentation.storage

import and.org.recordream.R
import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.FragmentStorageBinding
import and.org.recordream.presentation.storage.myrecord.MyEmotionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")
    private lateinit var myEmotionAdapter: MyEmotionAdapter

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

    private fun setUpRecyclerView() {
        binding.rvStorageMyemotion.apply {
            myEmotionAdapter = MyEmotionAdapter(::selectedItem)
            adapter = myEmotionAdapter
        }
        addItemList()
    }


    //    private fun getInfo() { // Adapter initialized
//        myEmotionAdapter = MyEmotionAdapter {
//
//            Log.d("CONST", "${it.emotion}")
//            when (it.emotion) { // CONST EMOTION 서버 저장
//                ALL -> context?.shortToast("안녕하소")
//                SMILE ->{
//                    it.emotion = R.drawable.logo
//                }
//            }
//
//        }
//    }
//
//    private fun initAdapter() {
//        binding.rvStorageMyemotion.adapter = myEmotionAdapter
//    }
//
    private fun selectedItem(position: Int) {
        val currentSelectedPostion= position
       // myEmotionList
    }

    private fun addItemList() {
        myEmotionAdapter.myEmotionList.addAll(
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
