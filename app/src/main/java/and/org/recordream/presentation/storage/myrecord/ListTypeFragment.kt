package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.FragmentListTypeBinding
import and.org.recordream.presentation.storage.adapter.ListTypeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class ListTypeFragment : Fragment() {
    private var _binding: FragmentListTypeBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialization")
    private lateinit var listTypeAdapter: ListTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListTypeBinding.inflate(layoutInflater, container, false)

        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        listTypeAdapter = ListTypeAdapter { }
        binding.rvStorageList.adapter = listTypeAdapter
        addItemList()
    }

    private fun addItemList() {
        listTypeAdapter.listRecords.addAll(
            listOf<Record>(
                Record("wdwdddw", "12121", 2, 2, listOf(2, 4), "오늘 밤새서 붙일 예정 ^^,,,")
            )
        )
    }

}