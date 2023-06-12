package com.recodream_aos.recordream.util.recorder // ktlint-disable package-name

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException

object Recorder {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    private var filePath = ""
    private lateinit var temp: Context

    fun init(context: Context) {
        temp = context
        filePath = "${context.externalCacheDir?.absolutePath}/audiorecordtest.3gp"
    }

    fun getRecordingFile(): File {
        return File(temp.externalCacheDir, filePath)
    }

    fun startPlaying() {
        Log.d("녹음시작이용", "녹음시작이용")
        player = MediaPlayer().apply {
            try {
                setDataSource(filePath)
                prepare()
                start()
                Log.d("녹음시작이용2", "녹음시작이용")
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
        Log.d("녹음시작이용", "녹음시작이용")
        recorder = MediaRecorder().apply {
            try {
                setAudioSource(MediaRecorder.AudioSource.MIC) // 사용마이크
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 인코딩
                setOutputFile(filePath) // 캐시에 저장
                prepare()
                Log.d("녹음시작이용2", "녹음시작이용")
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
