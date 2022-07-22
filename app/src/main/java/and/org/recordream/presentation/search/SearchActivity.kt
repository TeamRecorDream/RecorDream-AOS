package and.org.recordream.presentation.search

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.databinding.ActivitySearchBinding
import and.org.recordream.util.enqueueUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)


        binding.ivSearchSearchbtn.setOnClickListener {
            val keyword = binding.etSearchEnter.text.toString()
            initNetwork(keyword)
        }
        setContentView(binding.root)
        visibility()
        binding.ivSearchSearchbtn.setOnClickListener {
            val keyword = binding.etSearchEnter.text.toString()
            initNetwork(keyword)
        }
    }

    private fun initNetwork(search: String) {
        val keyword: String = search

        val call = RecordreamClient.searchService.getMyRecord(keyword)

        call.enqueueUtil(
            onSuccess = {
                Log.d("******Status******", "${it.status}")
                binding.tvSearchTotal.text = it.data?.recordTotal.toString()
                if (it.data!!.recordTotal != 0) {
                    visibility()
                }
            }
        )
    }

    private fun setRecyclerView() {

    }

    private fun visibility() {
        with(binding) {
            tvSearchRecordtxt.visibility = View.VISIBLE
            viSearchBar2.visibility = View.VISIBLE
            clSearchResultfield.visibility = View.VISIBLE
            tvSearchNone.visibility = View.INVISIBLE
            ivSearchLogo.visibility = View.INVISIBLE
        }
    }


}