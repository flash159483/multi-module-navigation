plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("javax.inject:javax.inject:1")
}