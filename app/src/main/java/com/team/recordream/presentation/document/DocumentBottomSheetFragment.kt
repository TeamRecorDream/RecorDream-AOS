package com.team.recordream.presentation.document

import android.app.Activity
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
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.team.recordream.R
import com.team.recordream.databinding.FragmentDocumentBottomSheetBinding
import com.team.recordream.domain.repository.DocumentRepository
import com.team.recordream.presentation.MainActivity
import com.team.recordream.util.customview.CustomDialog
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class DocumentBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDocumentBottomSheetBinding
    private lateinit var dialogDelete: CustomDialog
    private lateinit var shareActivityResultLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var documentRepository: DocumentRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_document_bottom_sheet,
            container,
            false
        )

        shareActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                }
            }

        initDialog()
        clickShare()
        clickEvent()
        clickCancel()
        return binding.root
    }

    private fun initDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun captureView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapAsImage(bitmap: Bitmap): Uri? {
        val tempFile = File(requireContext().cacheDir, "temp_image.jpg")
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }

        return FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".fileprovider",
            tempFile
        )
    }

    private fun clickShare() {
        binding.tvDocumentBottomShare.setOnClickListener {
            val documentActivityView = requireActivity().window.decorView.rootView
            val bitmap = captureView(documentActivityView)

            val imageUri = saveBitmapAsImage(bitmap)

            if (imageUri != null) {
                val intent = Intent("com.instagram.share.ADD_TO_STORY")
                val sourceApplication = "4432324493558166"
                intent.putExtra("source_application", sourceApplication)
                intent.setDataAndType(imageUri, MEDIA_TYPE_JPEG)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                shareActivityResultLauncher.launch(intent)
            }
            Firebase.analytics.logEvent(CLICK_SHARE_INSTAGRAM, bundleOf())
            setFragmentResult(INSTA_DIALOG, bundleOf(SHARE_MODE to SHARE))
            dismiss()
        }
    }

    private fun clickEvent() {
        binding.tvDocumentBottomDelete.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        binding.tvDocumentBottomDelete.setOnClickListener {
            dialogDelete = CustomDialog(requireActivity())
            dialogDelete.showDeleteDialog(R.layout.document_delete_dialog)
        }
    }

    private fun clickCancel() {
        binding.btnDocumentBottomCancel.setOnClickListener {
            val recordId = arguments?.getString(RECORD_ID_KEY)

            if (recordId != null) {
                lifecycleScope.launch {
                    try {
                        val deleteResponse = documentRepository.deleteDetailRecord(recordId)
                        dismiss()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("DocumentBottomSheet", "Error deleting record: ${e.message}")
                    }
                }
            } else {
                Toast.makeText(requireContext(), "기록 데이터가 없습니다", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }

    companion object {
        const val INSTA_DIALOG = "InstaDialog"
        const val SHARE_MODE = "ShareMode"
        const val SHARE = true
        const val CLICK_SHARE_INSTAGRAM = "click_SHARE_INSTARGRAM"
        private const val MEDIA_TYPE_JPEG = "image/jpeg"
        const val RECORD_ID_KEY = "RECORD_ID"
    }
}
