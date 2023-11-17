package com.lighthouse.multi_module_navigation.di.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Test

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultPreference

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EncryptedPreference