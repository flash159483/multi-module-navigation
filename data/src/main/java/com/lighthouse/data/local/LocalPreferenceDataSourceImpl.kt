package com.lighthouse.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val encryptedSharedPreferences: SharedPreferences
) : LocalPreferenceDataSource {
    private val gson = Gson()

    override fun <T> save(key: String, value: T, isEncrypted: Boolean) {
        val preference = if (isEncrypted) encryptedSharedPreferences else sharedPreferences

        when (value) {
            is String -> preference.edit {
                putString(key, value)
            }

            is Boolean -> preference.edit {
                putBoolean(key, value)
            }

            is Int -> preference.edit {
                putInt(key, value)
            }

            is List<*> -> {
                val gson = GsonBuilder().create()
                val jsonArray = gson.toJsonTree(value).asJsonArray
                preference.edit {
                    putString(key, jsonArray.toString())
                }
            }

            else ->
                throw IllegalArgumentException("Type not supported ${this.javaClass.simpleName}")
        }
    }

    override fun <T> getValue(key: String, defaultValue: T, isEncrypted: Boolean): T {
        val preference = if (isEncrypted) encryptedSharedPreferences else sharedPreferences
        return when (defaultValue) {
            is String -> preference.getString(key, "") as T
            is Boolean -> preference.getBoolean(key, false) as T
            is Int -> preference.getLong(key, 0) as T
            is List<*> -> preference.getString(key, "") as T
            else -> {
                throw IllegalArgumentException("Type not supported")
            }
        }
    }

}