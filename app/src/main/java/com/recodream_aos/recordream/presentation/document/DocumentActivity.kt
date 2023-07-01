package com.recodream_aos.recordream.presentation.document

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.recodream_aos.recordream.data.entity.remote.response.ResponseDocumentDreamRecord
import com.recodream_aos.recordream.databinding.ActivityDocumentBinding
import com.recodream_aos.recordream.util.CustomDialog
import com.recodream_aos.recordream.util.RecordreamMapping

class DocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentBinding

    // 더보기 바텀시트, 삭제 확인창 변수
    private val documentBottomSheetFragment = DocumentBottomSheetFragment()
    private lateinit var dialog: CustomDialog

    // 선택한 카드 정보 반영해서 불러오기 위해 변수 설정
    private val recordreamMapping = RecordreamMapping()

    private val viewModel by viewModels<DocumentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        나중에 수정해서 추가 예정
        val recordId = intent.getStringExtra("id")?: error("need record Id")

        initAdapter()
        initTabLayout()
        initCloseButton()
        initNetwork(recordId)
        observeData()

        binding.ivDotsMore.setOnClickListener { createBottom() }
    }

    private fun createBottom() {
        documentBottomSheetFragment.show(supportFragmentManager, documentBottomSheetFragment.tag)
    }

    private fun initAdapter() {
        val documentViewPagerAdapter = DocumentViewPagerAdapter(this)
        binding.vpDocument.adapter = documentViewPagerAdapter
    }

    // 탭 이름 적용 없이 인디케이터만 움직이게 수정 예정
    private fun initTabLayout() {
        val tabLabel = listOf("나의 꿈 기록", "노트")

        TabLayoutMediator(binding.tlDocument, binding.vpDocument) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()
    }

    private fun initCloseButton() {
        binding.ivDocumentClose.setOnClickListener {
            finish()
        }
    }

    private fun initNetwork(id:String){
        viewModel.getData(id)
    }

    private fun applyData(response: ResponseDocumentDreamRecord) {
    }

 private fun observeData(){
     viewModel.documentResponse.observe(this){
         applyData(it)
     }
 }
}