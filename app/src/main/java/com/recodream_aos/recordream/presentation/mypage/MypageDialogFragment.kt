package com.recodream_aos.recordream.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recodream_aos.recordream.databinding.CustomMypageDialogBinding
import com.recodream_aos.recordream.presentation.login.LoginActivity

class MypageDialogFragment : Fragment() {
    private var _binding: CustomMypageDialogBinding? = null
    private val binding: CustomMypageDialogBinding
        get() = requireNotNull(binding) { "binding이 초기화되지 않았습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomMypageDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setOnClick() {
        binding.btnMypageDialogDelete.setOnClickListener { deleteAccount() }
    }

    private fun deleteAccount() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() {}
    }
}
