package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.databinding.HomeCardItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomePagerRecyclerAdapter :
    RecyclerView.Adapter<HomePagerRecyclerAdapter.HomePagerViewHolder>() {
    private val homeList = mutableListOf<ResponseHomeItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagerViewHolder {
        val binding = HomeCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePagerViewHolder, position: Int) {
        holder.onBind(homeList[position])
    }

    override fun getItemCount(): Int = homeList.size

    class HomePagerViewHolder(private val binding: HomeCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(homeItems: ResponseHomeItems, records: ResponseHomeItems.Records) {
            binding.ivHomeEmoticon. = records.emotion
            binding.tvHomeDate.text = records.date
            binding.tv
        }
    }
}
