package com.recodream_aos.recordream.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Singleton

@Singleton
object RecordreamSharedPreference {
    private lateinit var recordreamStorage: SharedPreferences
    private const val DEFAULT_VALUE = ""

    fun init(context: Context) {
        recordreamStorage = EncryptedSharedPreferences.create(
            "encryptedShared",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    fun setValue(key: String, value: String) {
        recordreamStorage.edit {
            putString(key, value)
            apply()
        }
    }

    fun getValue(key: String): String? = recordreamStorage.getString(key, DEFAULT_VALUE)
}
