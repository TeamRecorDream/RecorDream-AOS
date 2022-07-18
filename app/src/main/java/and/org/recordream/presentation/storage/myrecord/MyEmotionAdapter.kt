package and.org.recordream.presentation.storage.myrecord

import and.org.recordream.data.local.MyEmotionData
import and.org.recordream.databinding.ItemStorageEmotionBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


//class MyEmotionAdapter(private val selected: (Int) -> Unit) :
//    RecyclerView.Adapter<MyEmotionAdapter.MyEmotionViewHolder>() {
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
//        holder.itemView.setOnClickListener {
//            // Log.d("ddddddddddddddd", "${holder.adapterPosition}")
//            (0..7).forEach {
//
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = myEmotionList.size
//
//    fun addMyEmotionList(myEmotionData: MyEmotionData) {
//        myEmotionList.add(myEmotionData)
//        notifyItemInserted(myEmotionList.size - 1)
//    }
//
//    inner class MyEmotionViewHolder(
//        private val binding: ItemStorageEmotionBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun onBind(data: MyEmotionData) {
//            with(binding) {
//                ivStorageMyemotion.isSelected = data.switch
//                storage = data
//                itemView.setOnClickListener {
//                    (0..7).forEach {
//                        ivStorageMyemotion.isSelected = false
//                        Log.d("포이치","foreach")
//                    }
//                    selected(adapterPosition)
//                    if (!ivStorageMyemotion.isSelected) ivStorageMyemotion.isSelected = true
//                    Log.d("조건","if통과")
//                }
//            }
//        }
//    }
//}


class MyEmotionAdapter(private val itemClick: (MyEmotionData) -> Unit) :
    RecyclerView.Adapter<MyEmotionAdapter.MyEmotionViewHolder>() {
    val myEmotionList = mutableListOf<MyEmotionData>()
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEmotionViewHolder {
        val binding = ItemStorageEmotionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyEmotionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MyEmotionViewHolder, position: Int) {
        var num:Int
        holder.onBind(myEmotionList[position])
        Log.d("d","${myEmotionList[0]}")


    }

    override fun getItemCount(): Int = myEmotionList.size

    fun addTmp(tmp: MyEmotionData) {
        myEmotionList.add(tmp)
        notifyItemInserted(myEmotionList.size - 1)
    }

    inner class MyEmotionViewHolder(
        private val binding: ItemStorageEmotionBinding,
        private val itemClick: (MyEmotionData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MyEmotionData) {

            with(binding) {

                storage = data

                ivStorageMyemotion.isSelected = data.switch

                itemView.setOnClickListener {
                    Log.d("포이치","${itemView.isSelected}")
                    ivStorageMyemotion.isSelected = true
                    Log.d("조건", "position $adapterPosition")
                    for(i in 0..7) {
                        Log.d("조건","불린 : ${myEmotionList[i].switch.toString()}")
                        //if(i == adapterPosition) ivStorageMyemotion.isSelected= true
                        if(i!=adapterPosition) {
                            myEmotionList[i].switch=false
                        }
                        else{
                            myEmotionList[adapterPosition].switch=true
                        }
                        Log.d("포이치","foreach")
                    }

                    notifyDataSetChanged()
                    //selected(adapterPosition)

                   // if (ivStorageMyemotion.isSelected) ivStorageMyemotion.isSelected = true
                    Log.d("조건","if통과")
                }

            }
        }
    }
}