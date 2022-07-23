package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.FragmentListTypeBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.presentation.storage.adapter.ListTypeAdapter
import and.org.recordream.util.enqueueUtil
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        initNetwork()
        initAdapter()
        return binding.root
    }

    private fun initNetwork() {
        val selectedEmotion = arguments?.getInt("emotion")

        Log.d("******selectedEmotion*******", "$selectedEmotion")
        val call = RecordreamClient.storageService.getMyListRecord(0)

        call.enqueueUtil(
            onSuccess = {
                it.data?.let { _it -> addItemList(_it.records) }
                Log.d("******ListTypeFragment_status******", "${it.status}")
            }, onError = {
                Log.d("******ListTypeFragment_status******", "$it")
            }
        )

    }


    private fun initAdapter() {
        listTypeAdapter = ListTypeAdapter {
            toDetailView(it.id)
        }
        binding.rvStorageList.adapter = listTypeAdapter
    }

    private fun addItemList(data: List<Record>) {
        listTypeAdapter.listRecords = data as MutableList<Record>
        listTypeAdapter.notifyDataSetChanged()
    }


    private fun toDetailView(id: String) { // DetatilActivity로 id값 intent 전달
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.apply {
            putExtra("id", id)
        }
        startActivity(intent)
    }

}