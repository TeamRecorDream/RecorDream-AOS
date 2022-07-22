package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.data.remote.response.ResponseHomeRecord
import and.org.recordream.databinding.HomeCardItemBinding
import and.org.recordream.util.RecordreamMapping
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class HomeViewPagerAdapter :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {
    var homeCardList = mutableListOf<ResponseHomeRecord>()

    class PagerViewHolder(
        private val binding: HomeCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseHomeRecord) {
            binding.tvHomeDate.text = data.date
            binding.tvHomeCardTitle.text = data.title
            applyData(data)
        }

        val recorDreamMapping = RecordreamMapping()
        private fun applyData(response: ResponseHomeRecord) {
            val applyEmotion = response.let { recorDreamMapping.matchHomeEmotion(it.emotion) }
            val applyTextColor = response.let { recorDreamMapping.matchTextColor(it.dream_color) }
            val applyGenre = response.let { recorDreamMapping.genreMapping(it.genre) }
            val applyCardImage =
                response.let { recorDreamMapping.matchDetailColor(it.dream_color) }

//        context?.let { ContextCompat.getDrawable(it, R.drawable.logo) }
//          ?.let { binding.ivDetailDreamColor.background = it }
////        binding.ivDetailDreamColor = applyCardImage
//        binding.ivDetailDreamColor.background = applyEmotion.toDrawable()
            binding.clHomeCard.setBackgroundResource(applyCardImage)
            binding.ivHomeEmoticon.setBackgroundResource(applyEmotion)
            binding.tvHomeCardTitle.text = response.title
//
//        binding.ivProfile.setImageDrawable(applyEmotion)

            binding.tvHomeDate.text = response.date
            if (response.genre.size == 1) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
            }
            if (response.genre.size == 2) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
                binding.tvHomeGenre2.text = "#${applyGenre[1]}"
            }
            if (response.genre.size == 3) {
                binding.tvHomeGenre1.text = "#${applyGenre[0]}"
                binding.tvHomeGenre2.text = "#${applyGenre[1]}"
                binding.tvHomeGenre3.text = "#${applyGenre[2]}"
            }
            when (response.genre.size) {
                1 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.INVISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.tvHomeGenre1.visibility = View.VISIBLE
                    binding.tvHomeGenre2.visibility = View.VISIBLE
                    binding.tvHomeGenre3.visibility = View.VISIBLE
                }
            }
            binding.tvHomeGenre1.setTextColor(applyTextColor)
            binding.tvHomeGenre2.setTextColor(applyTextColor)
            binding.tvHomeGenre3.setTextColor(applyTextColor)
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
//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
//            ContextCompat.startActivity(holder.itemView.context, intent, null)
//        }
    }

    override fun getItemCount(): Int {
        return homeCardList.size
    }
}
