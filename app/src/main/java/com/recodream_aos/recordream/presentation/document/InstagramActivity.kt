package com.recodream_aos.recordream.presentation.document

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.recodream_aos.recordream.R
import com.recodream_aos.recordream.base.BindingActivity
import com.recodream_aos.recordream.databinding.ActivityInstagramBinding
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class InstagramActivity : BindingActivity<ActivityInstagramBinding>(R.layout.activity_instagram) {
    private var imgUri: Uri? = null
    private var instaFeedUri: Uri? = null
    private var fromCamera = false
    private val instaShareActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        instaFeedUri?.let {
            contentResolver.delete(it, null, null)
        }
        if (fromCamera) {
            imgUri?.let { contentResolver.delete(it, null, null) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding.tvDocumentInstaDate.text = intent.getStringExtra("date")
//        binding.ivDocumentInstaIcon.setImageDrawable(intent.getStringExtra("emotion"))
        binding.tvDocumentInstaTitle.text = intent.getStringExtra("title")
        binding.tvDocumentInstaGenre1.text = intent.getStringExtra("genre1")
        binding.tvDocumentInstaGenre2.text = intent.getStringExtra("genre2")
        binding.tvDocumentInstaGenre3.text = intent.getStringExtra("genre3")
        binding.tvDocumentInstaContent.text = intent.getStringExtra("diary")
    }

    private fun initViewModel() {
//        binding.viewModel = DocumentViewModel()
        binding.lifecycleOwner = this
    }

    fun instaShareBtn(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)

            shareInsta(bgUri, viewUri)
        } else {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)

            shareInsta(bgUri, viewUri)
        }
    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBackgroundBitmap(): Bitmap {
        // 기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val backgroundBitmap = Bitmap.createBitmap(
            backgroundWidth,
            backgroundHeight,
            Bitmap.Config.ARGB_8888
        ) // 비트맵 생성
        val canvas = Canvas(backgroundBitmap) // 캔버스에 비트맵을 Mapping.

        val bgColor =
            binding.vDocumentInstaTopGroup.background // 뷰모델의 if(bgColor != null) {

        if (bgColor != null) {
            // 캔버스에 현재 설정된 배경화면색으로 칠한다.
        }

        return backgroundBitmap
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY)
        view.draw(canvas)
        return bitmap
    }

    private fun drawViewBitmap(): Bitmap {
        val cardImage = binding.vDocumentInstaTopGroup

        val margin = resources.displayMetrics.density * 20

        val width = cardImage.width

        val height = (cardImage.height + margin).toInt()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val imageViewBitmap =
            Bitmap.createBitmap(cardImage.width, cardImage.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        cardImage.draw(imageViewCanvas)
        /*imageViewCanvas를 통해서 imageView를 그린다.
         *이 때 스케치북은 imageViewBitmap이므로 imageViewBitmap에 imageView가 그려진다.
         */

        val imageViewLeft = ((width - cardImage.width) / 2).toFloat()

        canvas.drawBitmap(imageViewBitmap, imageViewLeft, (0).toFloat(), null)

        return bitmap
    }

    // 이미지를 캐시에 저장하는 메서드. Android 버전과 상관 없다.
    private fun getImageUri(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val cachePath = "$cacheDir/file"
        val dir = File(cachePath)

        if (dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            // 0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            // 파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(
                applicationContext,
                arrayOf(fileItem.toString()),
                null,
                null
            )

            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(
            applicationContext,
            "com.recodream_aos.recordream.fileprovider",
            fileItem
        )
    }

    private fun moveToDocument() {
        startActivity(
            Intent(this, DocumentActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
//                putExtra(FROM_WHERE, FROM_CERTIFY_ACTIVITY)
            }
        )
    }

    private fun shareInsta(bgUri: Uri?, viewUri: Uri?) {
        val sourceApplication = "4432324493558166"
        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        val backgroundAssetUri = Uri.parse("app/src/main/res/drawable/home_background.xml")
        val stickerAssetUri: Uri = Uri.parse("app/src/main/res/layout/activity_instagram.xml")
// Define image asset URI

        intent.putExtra("source_application", sourceApplication)
        intent.putExtra("interactive_asset_uri", stickerAssetUri)
        intent.type = "image/jpeg"
        intent.setDataAndType(backgroundAssetUri, "image/jpeg")

        grantUriPermission(
            "com.instagram.android",
            stickerAssetUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        grantUriPermission(
            "com.instagram.android",
            backgroundAssetUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        try {
            instaShareActivityLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@InstagramActivity, "Instagram 앱이 없습니다.", Toast.LENGTH_SHORT).show()
            moveToDocument()
            Timber.tag("Insta_Share").d("Instagram 앱이 없습니다.")
        }
    }

    // 이미지를 캐시에 저장하는 메서드. Android 버전과 상관 없다.
    private fun saveImageAtCacheDir(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val cachePath = "$cacheDir/file"
        val dir = File(cachePath)

        if (dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            // 0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            // 파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(
                applicationContext,
                arrayOf(fileItem.toString()),
                null,
                null
            )

            // sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(
            applicationContext,
            "com.khs.instagramshareexampleproject.fileprovider",
            fileItem
        )
    }
}
