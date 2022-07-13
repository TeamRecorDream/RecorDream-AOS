package and.org.recordream.presentation.home

import and.org.recordream.databinding.HomeCardItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomePagerRecyclerAdapter :
    RecyclerView.Adapter<HomePagerRecyclerAdapter.HomePagerViewHolder>() {
    private val homeList = mutableListOf<ResponseHomeItems.Records>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagerViewHolder {
        val binding =
            HomeCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePagerViewHolder, position: Int) {
        holder.onBind(homeList[position])
    }

    override fun getItemCount(): Int = homeList.size

    class HomePagerViewHolder(private val binding: HomeCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(recordItems: ResponseHomeItems.Records) {
            //binding.ivHomeEmoticon = recordItems.emotion
            binding.tvHomeDate.text = recordItems.date
            //binding.tvHomeHashtag.text = recordItems.genre
            binding.tvHomeCardTitle.text = recordItems.title
            //binding.clHomeCard.background = recordItems.dream_color

        }
    }
}
