package and.org.recordream.presentation.home

import and.org.recordream.R
import and.org.recordream.data.local.TmpData
import and.org.recordream.databinding.ItemTmpBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeViewPagerAdapter() :
    RecyclerView.Adapter<HomeViewPagerAdapter.PagerViewHolder>() {
    val tmpList = mutableListOf<TmpData>()

    class PagerViewHolder(
        private val binding: ItemTmpBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TmpData) {
            binding.test = data
            Glide.with(binding.ivTmp)
                .load(data.img)
                .into(binding.ivTmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: ItemTmpBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tmp,
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(tmpList[position])
    }

    override fun getItemCount(): Int {
        return tmpList.size
    }
}
