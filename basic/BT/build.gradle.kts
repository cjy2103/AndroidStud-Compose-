// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
//    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false

}