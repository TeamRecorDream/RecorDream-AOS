package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.ItemStorageEmotionBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyEmotionAdapter(private val itemClick: (MyEmotionData) -> Unit) :
    RecyclerView.Adapter<MyEmotionAdapter.MyEmotionViewHolder>() {
    val itemList = mutableListOf<MyEmotionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEmotionViewHolder {
        val binding = ItemStorageEmotionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyEmotionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MyEmotionViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class MyEmotionViewHolder(
        private val binding: ItemStorageEmotionBinding,
        private val itemClick: (MyEmotionData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MyEmotionData) {
            binding.follower = data
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }
}