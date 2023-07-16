package com.recodream_aos.recordream.presentation.document2

import android.os.Bundle
import androidx.activity.viewModels
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityDocumentssBinding
import com.recodream_aos.recordream.presentation.document2.adapter.ContentAdapter
import com.recodream_aos.recordream.presentation.document2.model.DocumentUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentssActivity :
    BindingActivity<ActivityDocumentssBinding>(R.layout.activity_documentss) {
    private val contentAdapter: ContentAdapter by lazy { ContentAdapter() }
    private val documentViewModel: DocumentssViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvDocumentChip.adapter = contentAdapter
        contentAdapter.submitList(
            listOf(
                DocumentUiModel(1, "123123"),
                DocumentUiModel(2, "1159195974231984156219654"),
            ),
        )

//        TabLayoutMediator(binding.tlDocument, binding.vpDocumentContent) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()
    }
}
