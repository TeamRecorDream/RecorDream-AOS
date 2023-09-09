package com.team.recordream.presentation.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.team.recordream.R
import com.team.recordream.databinding.FragmentDetailBinding
import com.team.recordream.presentation.detail.adapter.ContentAdapter
import com.team.recordream.presentation.detail.adapter.GenreTagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailBottomSheetFragment private constructor(
    private val recordId: String,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding ?: error(R.string.error_basefragment)
    private val detailViewModel: DetailViewModel by viewModels()
    private val genreTagAdapter: GenreTagAdapter by lazy { GenreTagAdapter() }
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        // dismiss될 때, 프래그먼트 인스턴스 확인
    }

    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupView() {
        detailViewModel.updateDetailRecord(recordId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        collectGenre()
        collectIsRemoved()
        attachAdapter()
        setEventOnClick()
    }

    private fun setupBinding() {
        binding.vm = detailViewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            this.setOnShowListener { dialog ->
                val bottomSheetDialog =
                    (dialog as BottomSheetDialog).also { setCanceledOnTouchOutside(false) }

                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.let {
                        val behaviour = BottomSheetBehavior.from(it)
                        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                        setupFullHeight(it)
                    }
            }
        }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun collectGenre() {
        collectWithLifecycle(detailViewModel.tags) { genre ->
            genreTagAdapter.submitList(genre)
        }
    }

    private fun collectIsRemoved() {
        collectWithLifecycle(detailViewModel.isRemoved) { isRemoved ->
            if (isRemoved) dismiss()
        }
    }

    private fun attachAdapter() {
        binding.rvDocumentChip.adapter = genreTagAdapter
        binding.rvDocumentChip.setHasFixedSize(true)

        binding.vpDocumentContent.adapter = contentAdapter
        contentAdapter.fragments.addAll(
            listOf(
                DreamRecordFragment.from(detailViewModel),
                NoteFragment.from(detailViewModel),
            ),
        )

        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { _, _ -> }.attach()
    }

    private fun setEventOnClick() {
        binding.ivDocumentMore.setOnClickListener { showMoreDialog() }
        binding.ivDocumentClose.setOnClickListener { dismiss() }
    }

    private fun showMoreDialog() {
        val documentBottomSheetFragment = DocumentBottomSheetFragment.from(detailViewModel)
        documentBottomSheetFragment.show(childFragmentManager, documentBottomSheetFragment.tag)
    }

    private inline fun <T> collectWithLifecycle(
        flow: Flow<T>,
        crossinline action: (T) -> Unit,
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { value ->
                    action(value)
                }
            }
        }
    }

    companion object {
        fun from(id: String): DetailBottomSheetFragment = DetailBottomSheetFragment(id)
    }
}
