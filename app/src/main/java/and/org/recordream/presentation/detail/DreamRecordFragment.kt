package and.org.recordream.presentation.detail

import and.org.recordream.data.remote.RecordreamClient
import and.org.recordream.data.remote.response.ResponseDetailDreamRecord
import and.org.recordream.databinding.FragmentDreamRecordBinding
import and.org.recordream.util.enqueueUtil
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class DreamRecordFragment : Fragment() {
    private var _binding: FragmentDreamRecordBinding? = null
    private val binding get() = _binding!!
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playIv: ImageView

    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDreamRecordBinding.inflate(inflater, container, false)
        playIv = binding.ivDeatailRecordPlay
        initNetwork()
        initPlayer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun initNetwork() {
//        val requestDetail = RequestDetailDreamRecord(
//            recordId = "62d16e7fe8b4508dbca5ead6"
//        )
        val recordId = "62d7b6f19669f53b6c72a89f"
        Log.d("dddddddddd", "wddddddddd123123ddddd")
//        val call = RecordreamClient.recorDreamServicee.getDetailRecord(recordId)
//
//        call.enqueueUtil(
//            onSuccess = {
//                Log.d("dddddddddd", "${it.status}")
////
//                val data = it.data
//                applyData(data)
//            },
//            onError = {
//                Log.d("dddddddddd", "$it")
//            }
//        )
    }

    private fun applyData(response: ResponseDetailDreamRecord?) {

        binding.tvDetailTabRecord.text = response?.content
//        binding.tvDetailVoiceTime.text = response!!.voice.url.length
        if (response?.voice != null) {
            binding.ivDeatailRecordPlay.visibility = View.VISIBLE
            binding.ivDetailRecordIcon.visibility = View.VISIBLE
            binding.ivDetailRectangleVoice.visibility = View.VISIBLE
            binding.tvDetailVoiceTime.visibility = View.VISIBLE
        } else {
            binding.ivDeatailRecordPlay.visibility = View.GONE
            binding.ivDetailRecordIcon.visibility = View.GONE
            binding.ivDetailRectangleVoice.visibility = View.GONE
            binding.tvDetailVoiceTime.visibility = View.GONE

        }

    }

    private fun initPlayer() {
        // on below line we are
        // initializing our media player
        mediaPlayer = MediaPlayer()
        playIv.setOnClickListener {

            // on below line we are creating a variable for our audio url
            var audioUrl =
                "https://recordream-sample.s3.ap-northeast-2.amazonaws.com/1657893969535_test.wav"

            // on below line we are setting audio stream
            // type as stream music on below line.
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            // on below line we are running a try
            // and catch block for our media player.
            try {
                // on below line we are setting audio
                // source as audio url on below line.
                mediaPlayer.setDataSource(audioUrl)

                // on below line we are
                // preparing our media player.
                mediaPlayer.prepare()

                // on below line we are
                // starting our media player.
                mediaPlayer.start()

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }
        }

    }

    private fun observeData() {
        viewModel.detailResponse.observe(viewLifecycleOwner) {
            applyData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}