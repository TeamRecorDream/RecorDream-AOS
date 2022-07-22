package and.org.recordream.presentation.storage.adapter

import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.ItemStorageListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListTypeAdapter(private val itemClick: (Record) -> Unit) :
    RecyclerView.Adapter<ListTypeAdapter.ListTypeViewHolder>() {
    val listRecords = mutableListOf<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTypeViewHolder {
        val binding = ItemStorageListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListTypeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ListTypeViewHolder, position: Int) {
        holder.onBind(listRecords[position])
    }

    override fun getItemCount(): Int = listRecords.size

    class ListTypeViewHolder(
        private val binding: ItemStorageListBinding,
        private val itemClick: (Record) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Record) {
            with(binding) {
                recordlist = data
                itemClick(data)
                itemView.setOnClickListener {

                }
            }
        }
    }
}
