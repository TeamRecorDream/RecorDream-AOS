package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.data.local.Record
import and.org.recordream.databinding.HomeCardItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class HomeViewPagerAdapter() :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {
    val homeCardList = mutableListOf<Record>()

    class PagerViewHolder(
        private val binding: HomeCardItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Record) {
            binding.records = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: HomeCardItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_card_item,
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(homeCardList[position])
    }

    override fun getItemCount(): Int {
        return homeCardList.size
    }
}
