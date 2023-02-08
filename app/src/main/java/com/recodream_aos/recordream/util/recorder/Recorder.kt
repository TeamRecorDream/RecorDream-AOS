package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.IOException
import java.lang.reflect.InvocationTargetException

class Recorder(
    val context: Context
) {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    private val recordingFilePath by lazy {
        "${context.externalCacheDir?.absolutePath}/audiorecordtest.3gp"
    }

    fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(recordingFilePath)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("LOG_TAG", "prepare() failed")
            }
        }
    }

    fun stopPlaying() {
        player?.release()
        player = null
    }

    fun startRecording() {
        recorder = MediaRecorder().apply {
            try {
                setAudioSource(MediaRecorder.AudioSource.MIC) // 사용마이크
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 인코딩
                setOutputFile(recordingFilePath) // 캐시에 저장
                prepare()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (
                e: InvocationTargetException
            ) {
                e.targetException.printStackTrace(); // getTargetException
            }
        }
        recorder?.start()
    }

    fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
    }

    fun recorderRelease() {
        recorder?.release()
        recorder = null
    }

    fun playerRelease() {
        player?.release()
        player = null
    }
}
