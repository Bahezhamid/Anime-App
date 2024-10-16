plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.animeapp"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.animeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.material.icons.extended)

    //matrial3
    implementation (libs.androidx.material)
    implementation (libs.material3)

    // datastore
    implementation(libs.androidx.datastore.preferences)


    // Room components
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    androidTestImplementation (libs.androidx.room.testing)

    // Lifecycle components
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.common.java8)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    // Kotlin components
    implementation (libs.kotlin.stdlib.jdk7)
    api (libs.kotlinx.coroutines.core)
    api (libs.kotlinx.coroutines.android)

    implementation (libs.gson)

    //retrofit
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation ("com.google.code.gson:gson:2.8.7")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.tkotlinx:kotlinx-coroutines-test:1.7.3")

    //notification 
    implementation (libs.androidx.core.ktx.v190)

    //google sign in
    implementation ("com.google.android.gms:play-services-auth:20.4.1")

    // Local Unit Tests
    implementation(libs.androidx.core)
    testImplementation(libs.junit.v413)
    testImplementation(libs.hamcrest.all)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.core)
    testImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")
    testImplementation (libs.junit)
    // Instrumented Unit Tests
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.dexmaker.mockito)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.core.testing.v210)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit.v111)
    androidTestImplementation(libs.androidx.espresso.core.v320)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")
    androidTestImplementation (libs.junit)

}