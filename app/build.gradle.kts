import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    kotlin("plugin.serialization")
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    namespace = "com.app.electricstations"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.teacheasy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }


    sourceSets {
        getByName("main") {
            java.srcDir("build/generated/source/navigation-args")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.4.0")
    implementation ("com.squareup.retrofit2:retrofit:2.7.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation ("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation ("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("de.hdodenhof:circleimageview:3.1.0")
// KTX for the Maps SDK for Android library
    implementation ("com.google.maps.android:maps-ktx:5.1.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")

    //for animated images
    implementation ("com.airbnb.android:lottie:6.4.1")

}