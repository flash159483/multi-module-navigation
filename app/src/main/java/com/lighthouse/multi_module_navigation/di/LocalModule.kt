package com.lighthouse.multi_module_navigation.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.lighthouse.multi_module_navigation.di.annotation.DefaultPreference
import com.lighthouse.multi_module_navigation.di.annotation.EncryptedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.GeneralSecurityException
import java.security.KeyStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    @DefaultPreference
    fun provideShared(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
    }


    @Provides
    @Singleton
    @EncryptedPreference
    fun provideSharedEncrypted(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return try {
            createSharedPreference(context, masterKey)
        } catch (gsException: GeneralSecurityException) {
            deleteSharedPreference(context)
            createSharedPreference(context, masterKey)
        }
    }

    private fun createSharedPreference(context: Context, masterKey: MasterKey): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            context.packageName + "_secured_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private fun deleteSharedPreference(context: Context) {
        try {
            val check =
                context.deleteSharedPreferences(context.packageName + "_secured_preferences")

            clearSharedPreference(context)

            if (check) {
                Log.d("EncrytedSharedPref", "sharedPref deleted")
            } else {
                Log.d("EncrytedSharedPref", "sharedPref not exists")
            }

            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyStore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        } catch (e: Exception) {
            Log.d("EncrytedSharedPref", "Error occured while deleting sharedPref")
        }
    }

    private fun clearSharedPreference(context: Context) {
        context.getSharedPreferences(
            context.packageName + "_secured_preferences",
            Context.MODE_PRIVATE
        ).edit().clear().apply()
    }
}


