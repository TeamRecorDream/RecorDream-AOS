package and.org.recordream.presentation.storage.adapter

import and.org.recordream.data.remote.response.Record
import and.org.recordream.databinding.ItemStorageGalleryBinding
import and.org.recordream.util.RecordreamMapping
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class GalleryTypeAdapter(
    private val itemClick: (Record) -> Unit
) :
    RecyclerView.Adapter<GalleryTypeAdapter.GalleryTypeViewHolder>() {
    var galleryRecords = mutableListOf<Record>()
    val recordreamMapping = RecordreamMapping()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryTypeViewHolder {
        val binding = ItemStorageGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GalleryTypeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: GalleryTypeViewHolder, position: Int) {
        holder.onBind(galleryRecords[position])
    }

    override fun getItemCount(): Int = galleryRecords.size

    class GalleryTypeViewHolder(
        private val binding: ItemStorageGalleryBinding,
        private val itemClick: (Record) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Record) {
            with(binding) {
                recordgallery = data
                val img = RecordreamMapping().emotionImgMapping(data.emotion)
                binding.ivStorageEmotiongallery.setBackgroundResource(img)
                itemView.setOnClickListener {
                    itemClick(data)

                }
            }
        }
    }
}
