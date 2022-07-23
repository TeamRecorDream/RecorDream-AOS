package and.org.recordream.presentation.detail

import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.databinding.FragmentNoteBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class NoteFragment : Fragment() {
    lateinit var binding: FragmentNoteBinding
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        initNetwork()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun initNetwork() {
//        val requestDetail = RequestDetailDreamRecord(
//            recordId = "62d16e7fe8b4508dbca5ead6"
//        )
        val recordId = "62d7b6f19669f53b6c72a89f"
        Log.d("dddddddddd", "wddddddddd123123ddddd")
//        val call = RecordreamClient.recorDreamServicee.getDetailRecord(recordId)
//
//        call.enqueueUtil(
//            onSuccess = {
//                Log.d("dddddddddd", "${it.status}")
////
//                val data = it.data
//                applyData(data)
//            },
//            onError = {
//                Log.d("dddddddddd", "$it")
//            }
//        )
    }

    private fun applyData(response: ResponseDetailDreamRecord?) {
        binding.tvDetailNote.text = response?.note
    }

    private fun observeData() {
        viewModel.detailResponse.observe(viewLifecycleOwner) {
            applyData(it)
        }
    }
}