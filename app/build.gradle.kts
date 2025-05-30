plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.foodorderingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodorderingapp"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material3:material3:1.3.1")
    dependencies {
        // ViewModel for Jetpack Compose
        implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")

        // Compose dependencies
        implementation ("androidx.compose.ui:ui:1.5.0")
        implementation ("androidx.activity:activity-compose:1.7.0")
    }
    implementation("io.coil-kt:coil-compose:2.6.0")

    dependencies {
        implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    }


    implementation("androidx.compose.foundation:foundation:1.6.0")
    implementation("androidx.datastore:datastore-preferences:1.1.3")
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("io.coil-kt:coil-gif:2.6.0")

    implementation(libs.firebase.storage.ktx)
    implementation("com.google.code.gson:gson:2.10.1")

    implementation ("com.razorpay:checkout:1.6.41")

}

kapt {
    correctErrorTypes = true
}