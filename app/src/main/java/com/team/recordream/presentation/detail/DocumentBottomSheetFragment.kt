package com.team.recordream.presentation.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team.recordream.R
import com.team.recordream.databinding.FragmentDocumentBottomSheetBinding
import com.team.recordream.presentation.record.RecordActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DocumentBottomSheetFragment private constructor(
    private val detailViewModel: DetailViewModel,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentDocumentBottomSheetBinding? = null
    private val binding get() = _binding ?: error(R.string.error_basefragment)
    private lateinit var shareActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setDialogState()
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_document_bottom_sheet,
            container,
            false,
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setDialogState() {
        dialog?.let {
            val behavior = (it as BottomSheetDialog).behavior
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            it.setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }
        setEventOnClick()
        collectIsRemoved()
    }

    private fun collectIsRemoved() {
        collectWithLifecycle(detailViewModel.isRemoved) { isRemoved ->
            if (isRemoved) dismiss()
        }
    }

    private fun setEventOnClick() {
        binding.tvDocumentBottomDelete.setOnClickListener {
            DetailDeleteDialog(requireContext()).create(detailViewModel)
        }

        binding.btnDocumentBottomCancel.setOnClickListener {
            dismiss()
        }

        binding.tvDocumentBottomShare.setOnClickListener {
            shareInstagram()
        }

        binding.tvDocumentBottomEdit.setOnClickListener {
            navigateToEditView()
            dismiss()
        }
    }

    private fun navigateToEditView() {
        val intent = RecordActivity.getIntent(
            requireContext(),
            RecordActivity.EDIT_MODE,
            detailViewModel.recordId,
        )

        startActivity(intent)
    }

    private fun shareInstagram() {
        val rootView = requireActivity().window.decorView.rootView
        val bitmap = Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        rootView.draw(canvas)
        val imageUri = convertBitmapToImageUri(bitmap)
        if (imageUri != null) {
            val intent = Intent(INSTAGRAM_PATH).apply {
                putExtra(INSTAGRAM, SOURCE_KEY)
                setDataAndType(imageUri, MEDIA_TYPE_JPEG)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            try {
                shareActivityResultLauncher.launch(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "인스타그램 앱이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        dismiss()
    }

    private fun convertBitmapToImageUri(bitmap: Bitmap): Uri? {
        val tempFile = File(requireContext().cacheDir, IMAGE_FORMAT)
        var outputStream: FileOutputStream? = null

        runCatching {
            outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.flush() ?: throw IllegalArgumentException()
        }.onFailure {
            Log.e("error", it.message.toString())
        }

        outputStream?.close()

        return FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + URI_FORMAT,
            tempFile,
        )
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
        private const val INSTAGRAM = "INSTAGRAM"
        private const val SOURCE_KEY = "4432324493558166"
        private const val INSTAGRAM_PATH = "com.instagram.share.ADD_TO_STORY"
        private const val IMAGE_FORMAT = "temp_image.jpg"
        private const val MEDIA_TYPE_JPEG = "image/jpeg"
        private const val URI_FORMAT = ".fileprovider"

        fun from(detailViewModel: DetailViewModel): DocumentBottomSheetFragment =
            DocumentBottomSheetFragment(detailViewModel)
    }
}
