package com.recodream_aos.recordream.presentation.storagy.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.data.entity.local.StorageEmotionData
import com.recodream_aos.recordream.databinding.FragmentStorageBinding

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding ?: error("binding not init")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(layoutInflater, container, false)
        selectShowView()
        return binding.root
    }

    private fun selectShowView() {
        Log.d("StorageFragment", "selectShowView: ")
        childFragmentManager.beginTransaction().add(R.id.fc_storage_list, StoragyGridFragment())
        binding.ivStorageSelectList.setOnClickListener {
            changeFragment(StoragyListFragment())
            binding.ivStorageSelectGallery.isSelected = false
            binding.ivStorageSelectList.isSelected = true
        }
        binding.ivStorageSelectGallery.setOnClickListener {
            binding.ivStorageSelectGallery.isSelected = true
            binding.ivStorageSelectList.isSelected = false
            changeFragment(StoragyGridFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fc_storage_list, fragment)
            .commit()
    }

    private val storageEmotionList = listOf<StorageEmotionData>(
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_all,
            feelingText = ALL
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_joy,
            feelingText = JOY
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_sad,
            feelingText = SAD
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_scary,
            feelingText = SCARY
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_strange,
            feelingText = STRANGE
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_shy,
            feelingText = SHY
        ),
        StorageEmotionData(
            feeling = R.drawable.feeling_xs_blank,
            feelingText = BLANK
        ),
    )

    companion object {
        const val ALL = "전체"
        const val JOY = "기쁜"
        const val SAD = "슬픈"
        const val SCARY = "무서운"
        const val STRANGE = "이상한"
        const val SHY = "민망한"
        const val BLANK = "미설정"
    }
}
