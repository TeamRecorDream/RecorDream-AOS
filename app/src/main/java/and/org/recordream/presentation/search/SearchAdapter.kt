package and.org.recordream.presentation.search

import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.ItemStorageListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class SearchAdapter(private val itemClick: (Record) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    val listRecords = mutableListOf<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemStorageListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(listRecords[position])
    }

    override fun getItemCount(): Int = listRecords.size

    class SearchViewHolder(
        private val binding: ItemStorageListBinding,
        private val itemClick: (Record) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Record) {
            with(binding) {
                recordlist = data

                itemView.setOnClickListener {
                    itemClick(data)
                }
            }
        }
    }
}
