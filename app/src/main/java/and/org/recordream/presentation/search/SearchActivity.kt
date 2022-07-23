package and.org.recordream.presentation.search

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.ActivitySearchBinding
import and.org.recordream.presentation.detail.DetailActivity
import and.org.recordream.util.enqueueUtil
import android.content.Intent
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
        setContentView(binding.root)


        initAdapter()
        clickListener()

    }

    private fun clickListener() {
        with(binding) {
            ivSearchBackbtn.setOnClickListener {
                finish()
            }

            ivSearchSearchbtn.setOnClickListener {
                val keyword = etSearchEnter.text.toString()
                initNetwork(keyword)
            }
        }

    }

    private fun initNetwork(search: String) {
        val keyword: String = search

        val call = RecordreamClient.searchService.getMyRecord(keyword)

        call.enqueueUtil(
            onSuccess = {
                Log.d("******Status******", "${it.status}")

                binding.tvSearchTotal.text = it.data?.recordTotal.toString()

                it.data?.let { _it -> addItemList(_it.records) }
                if (it.data!!.recordTotal != 0) {
                    visibility()
                }
            }
        )
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter {
            toDetailView(it.id)
        }
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun addItemList(data: List<Record>) {
        searchAdapter.listRecords = data as MutableList<Record>
        searchAdapter.notifyDataSetChanged()
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

    private fun toDetailView(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.apply {
            putExtra("id", id)
        }
        startActivity(intent)
    }


}