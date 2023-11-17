package com.lighthouse.data.local

interface LocalPreferenceDataSource {
    fun <T> save(key: String, value: T, isEncrypted: Boolean)

    fun <T> getValue(key: String, defaultValue: T, isEncrypted: Boolean): T
}