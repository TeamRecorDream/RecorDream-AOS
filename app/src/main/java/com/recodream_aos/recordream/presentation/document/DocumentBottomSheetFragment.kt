package com.recodream_aos.recordream.presentation.document

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.databinding.FragmentDocumentBottomSheetBinding
import com.recodream_aos.recordream.databinding.InstagramShareTemplateBinding
import com.recodream_aos.recordream.util.CustomDialog

class DocumentBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDocumentBottomSheetBinding
    private lateinit var shareBinding: InstagramShareTemplateBinding
    private lateinit var dialogDelete: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_document_bottom_sheet,
            container,
            false
        )
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

    private fun clickShare() {
        binding.tvDocumentBottomShare.setOnClickListener {
            initViewModel()
            setUpLifeCycleOwner()
        }
    }

    //     인스타 공유하기 버튼 눌렀을 때 컴스텀 템플릿에 데이터 반영해서 인스타 앱에서 띄우기 위해 필요한 함수들
    private fun initViewModel() {
        shareBinding.viewModel = ViewModelProvider(this).get(DocumentViewModel::class.java)
    }

    private fun setUpLifeCycleOwner() {
        binding.lifecycleOwner = this
        /*
        * LiveData에서는 LifeCycleOwner만 지정해주면
        * invalidateAll() 메서드를호출하지 않아도
        * DataBinding에서 ViewModel의 값 변동을 감지하고 View Update를 해준다.
        * */
    }

//    fun instaShare(bgUri: Uri?, viewUri: Uri?) {
// // Define image asset URI
//        val stickerAssetUri = Uri.parse(viewUri.toString())
//        val sourceApplication = "com.recordream_aos"
//
// // Instantiate implicit intent with ADD_TO_STORY action,
// // sticker asset, and background colors
//        val intent = Intent("com.instagram.share.ADD_TO_STORY")
//        intent.putExtra("source_application", sourceApplication)
//
//        intent.type = "image/png"
//        intent.setDataAndType(bgUri, "image/png");
//        intent.putExtra("interactive_asset_uri", stickerAssetUri)
//
// // Instantiate activity and verify it will resolve implicit intent
//        grantUriPermission(
//            "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//        )
//
//        grantUriPermission(
//            "com.instagram.android", bgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//        )
//
//        try {
//            this.startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            Toast.makeText(applicationContext, "인스타그램 앱이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//        }
//    }
// }
//
// // 화면에 나타난 View를 Bitmap에 그릴 용도.
// private fun drawBackgroundBitmap(): Bitmap {
//    //기기 해상도를 가져옴.
//    val backgroundWidth = resources.displayMetrics.widthPixels
//    val backgroundHeight = resources.displayMetrics.heightPixels
//
//    val backgroundBitmap =
//        Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
//    val canvas = Canvas(backgroundBitmap) // 캔버스에 비트맵을 Mapping.
//
//    val bgColor = binding.viewModel?.background?.value // 뷰모델의 현재 설정된 배경색을 가져온다.
//    if (bgColor != null) {
//        val color = ContextCompat.getColor(baseContext, bgColor)
//        canvas.drawColor(color) // 캔버스에 현재 설정된 배경화면색으로 칠한다.
//    }
//
//    return backgroundBitmap
// }
//
// private fun drawViewBitmap(): Bitmap {
//    val imageView = sharebinding.iv
//    val textView = sharebinding.tv
//
//    val margin = resources.displayMetrics.density * 20
//
//    val width = if (imageView.width > textView.width) {
//        imageView.width
//    } else {
//        textView.width
//    }
//
//    val height = (imageView.height + textView.height + margin).toInt()
//
//    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//    val canvas = Canvas(bitmap)
//
//    val imageViewBitmap =
//        Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
//    val imageViewCanvas = Canvas(imageViewBitmap)
//    imageView.draw(imageViewCanvas)
//    /*imageViewCanvas를 통해서 imageView를 그린다.
//     *이 때 스케치북은 imageViewBitmap이므로 imageViewBitmap에 imageView가 그려진다.
//     */
//
//    val imageViewLeft = ((width - imageView.width) / 2).toFloat()
//
//    canvas.drawBitmap(imageViewBitmap, imageViewLeft, (0).toFloat(), null)
//
//    //아래는 TextView. 위에 ImageView와 같은 로직으로 비트맵으로 만든 후 캔버스에 그려준다.
//    if (textView.length() > 0) {
//        //textView가 공백이 아닐때만
//        val textViewBitmap =
//            Bitmap.createBitmap(textView.width, textView.height, Bitmap.Config.ARGB_8888)
//        val textViewCanvas = Canvas(textViewBitmap)
//        textView.draw(textViewCanvas)
//
//        val textViewLeft = ((width - textView.width) / 2).toFloat()
//        val textViewTop = imageView.height + margin
//
//        canvas.drawBitmap(textViewBitmap, textViewLeft, textViewTop, null)
//    }
//
//    return bitmap
// }
//
// private fun saveImageAtCacheDir(bitmap: Bitmap): Uri? {
//    val fileName = System.currentTimeMillis().toString() + ".png"
//    val cachePath = "$cacheDir/file"
//    val dir = File(cachePath)
//
//    if (dir.exists().not()) {
//        dir.mkdirs() // 폴더 없을경우 폴더 생성
//    }
//
//    val fileItem = File("$dir/$fileName")
//    try {
//        fileItem.createNewFile()
//        //0KB 파일 생성.
//
//        val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림
//
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//        //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.
//
//        fos.close() // 파일 아웃풋 스트림 객체 close
//
//        MediaScannerConnection.scanFile(
//            applicationContext,
//            arrayOf(fileItem.toString()),
//            null,
//            null
//        )
//
//        //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
//        // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
//    } catch (e: FileNotFoundException) {
//        e.printStackTrace()
//    } catch (e: IOException) {
//        e.printStackTrace()
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return FileProvider.getUriForFile(
//        applicationContext,
//        "com.khs.instagramshareexampleproject.fileprovider",
//        fileItem
//    )
// }
// }

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
            dismiss()
        }
    }
}
