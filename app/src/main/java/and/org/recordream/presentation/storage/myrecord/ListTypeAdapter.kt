package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.remote.response.ResponseRecords
import and.org.recordream.databinding.ItemStorageListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListTypeAdapter(private val itemClick: (ResponseRecords) -> Unit) :
    RecyclerView.Adapter<ListTypeAdapter.ListTypeViewHolder>() {
    val listRecords = mutableListOf<ResponseRecords>()

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
        private val itemClick: (ResponseRecords) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseRecords) {
            with(binding) {
                recordList = data
                itemClick(data)
                itemView.setOnClickListener {

                }
            }
        }
    }
}
