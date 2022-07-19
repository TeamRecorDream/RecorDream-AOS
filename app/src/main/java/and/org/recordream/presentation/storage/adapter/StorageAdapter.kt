package and.org.recordream.presentation.storage.adapter

import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.ItemStorageEmotionBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class StorageAdapter(private val itemClick: (MyEmotionData) -> Unit) :
    RecyclerView.Adapter<StorageAdapter.MyEmotionViewHolder>() {
    val myEmotionList = mutableListOf<MyEmotionData>()

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

    inner class MyEmotionViewHolder(
        private val binding: ItemStorageEmotionBinding,
        private val itemClick: (MyEmotionData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MyEmotionData) {

            with(binding) {
                storage = data
                ivStorageMyemotion.isSelected = data.switch // default category 부분
                root.setOnClickListener {
                    itemClick(data)

                    ivStorageMyemotion.isSelected = true

                    for (i in 0..7) {
                        if (i != adapterPosition) {
                            myEmotionList[i].switch = false
                        } else {
                            myEmotionList[adapterPosition].switch = true
                        }
                        Log.d("wdqdqdwqwqqwdq", "$adapterPosition")
                    }
                    notifyDataSetChanged()

                }
            }
        }
    }
}

//
//
//
//package and.org.recordream.presentation.storage.adapter
//
//import and.org.recordream.data.local.MyEmotionData
//import and.org.recordream.databinding.ItemStorageEmotionBinding
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
//
//class StorageAdapter :
//    RecyclerView.Adapter<StorageAdapter.MyEmotionViewHolder>() {
//    val myEmotionList = mutableListOf<MyEmotionData>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEmotionViewHolder {
//        val binding = ItemStorageEmotionBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
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
//    inner class MyEmotionViewHolder(
//        private val binding: ItemStorageEmotionBinding,
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun onBind(data: MyEmotionData) {
//
//            with(binding) {
//                storage = data
//
//                ivStorageMyemotion.isSelected = data.switch // default category 부분
//                itemView.setOnClickListener {
//
//                    ivStorageMyemotion.isSelected = true
//
//                    for (i in 0..7) {
//                        if (i != adapterPosition) {
//                            myEmotionList[i].switch = false
//                        } else {
//                            myEmotionList[adapterPosition].switch = true
//                        }
//                    }
//
//                    notifyDataSetChanged()
//
//                }
//            }
//        }
//    }
//}
//
