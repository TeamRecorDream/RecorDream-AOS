package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.databinding.HomeCardItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class HomePagerRecyclerAdapter :
    RecyclerView.Adapter<HomePagerRecyclerAdapter.HomePagerViewHolder>() {
    private val homeList = mutableListOf<ResponseHomeItems.Records>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagerViewHolder {
        return HomePagerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.home_card_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomePagerViewHolder, position: Int) {
        val homeItem = homeList[position]
        holder.apply {
            onBind(homeItem)
            //homeView.tag = homeItem
        }
    }

    override fun getItemCount(): Int = homeList.size

    class HomePagerViewHolder(private val binding: HomeCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(homeItem: ResponseHomeItems.Records) {
            binding.apply {
                records = homeItem
            }

        }
    }
}
