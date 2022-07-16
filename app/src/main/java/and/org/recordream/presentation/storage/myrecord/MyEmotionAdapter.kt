package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.R
import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.ItemStorageEmotionBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


//class MyEmotionAdapter : RecyclerView.Adapter<MyEmotionAdapter.MyEmotionViewHolder>() {
//
//    val myEmotionList = mutableListOf<MyEmotionData>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEmotionViewHolder {
//        val binding = ItemStorageEmotionBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        )
//        return MyEmotionViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyEmotionViewHolder, position: Int) {
//        holder.onBind(myEmotionList[position])
//    }
//
//    override fun getItemCount(): Int = myEmotionList.size
//
//    class MyEmotionViewHolder(
//        private val binding: ItemStorageEmotionBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun onBind(data: MyEmotionData) {
//            with(binding) {
//                ivStorageMyemotion.isSelected = false
//                storage = data
//                itemView.setOnClickListener {
//                    if (!ivStorageMyemotion.isSelected) ivStorageMyemotion.isSelected = true
//                }
//            }
//        }
//    }
//}
//

class MyEmotionAdapter(private val itemClick: (MyEmotionData) -> Unit) :
    RecyclerView.Adapter<MyEmotionAdapter.MyEmotionViewHolder>() {
    val myEmotionList = mutableListOf<MyEmotionData>()

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEmotionViewHolder {
        val binding = ItemStorageEmotionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyEmotionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MyEmotionViewHolder, position: Int) {
        holder.onBind(myEmotionList[position])

    }

    override fun getItemCount(): Int = myEmotionList.size

    class MyEmotionViewHolder(
        private val binding: ItemStorageEmotionBinding,
        private val itemClick: (MyEmotionData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MyEmotionData) {

            with(binding) {
                ivStorageMyemotion.isSelected = false
                storage = data

                itemView.setOnClickListener {
                    var tmp : Int
                    itemClick(data)
                    when (tmp){

                    }

                }
            }
        }
    }
}