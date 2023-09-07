package com.team.recordream.util.recorder

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import java.io.File
import java.lang.reflect.InvocationTargetException

class Recorder(private val context: Context) {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val filePath: String by lazy { "${context.externalCacheDir?.absolutePath}/audioRecord.3gp" }

    fun getRecordingFile(): File {
        return File(filePath)
    }

    fun getDuration(file: String): Int {
        var time: Int

        MediaPlayer().apply {
            setDataSource(file)
            prepare()
            time = duration
        }

        return time
    }

    fun startPlaying(file: String?) {
        val recordingFile = file ?: filePath

        player = MediaPlayer().apply {
            runCatching {
                setDataSource(recordingFile)
                prepare()
                start()
            }.onFailure { throw IllegalStateException() }
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
                setOutputFile(filePath) // 캐시에 저장
                prepare()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (
                e: InvocationTargetException,
            ) {
                e.targetException.printStackTrace() // getTargetException
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
