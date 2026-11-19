plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.legacy.kapt)
}

android {
    namespace = "com.example.studentmanagement"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.studentmanagement"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // ANDROID
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // FRAGMENT
    implementation("androidx.fragment:fragment-ktx:1.8.3")

    // RECYCLERVIEW
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // CARDVIEW
    implementation("androidx.cardview:cardview:1.0.0")

    // VIEWMODEL
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // LIVEDATA
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    // ROOM DATABASE
    val roomVersion = "2.8.5"

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // LOGGING
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // TEST
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}