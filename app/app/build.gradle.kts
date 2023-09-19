plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.project.testruut"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.project.testruut"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val core_ktx_version="1.9.0"
    val dagger_version = "2.44"
    val retrofit_version = "2.9.0"
    val room_version = "2.5.2"
    val csv_version = "5.8"
    val firebase_bom_version = "32.3.1"
    val firebase_auth_version= "22.0.0"
    val compose_bom_version = "2023.03.00"
    val accompanish_swiperefresh_version = "0.24.2-alpha"
    val activity_compose_version = "1.7.2"
    val lifecycle_version = "2.6.2"
    val livedata_runtime_version = "1.5.0"
    val hilt_navigation_compose_version = "1.0.0"
    val navigation_compose_version = "2.5.3"

    implementation("androidx.core:core-ktx:$core_ktx_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-compose:$activity_compose_version")
    implementation(platform("androidx.compose:compose-bom:$compose_bom_version"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$compose_bom_version"))
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanish_swiperefresh_version")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.runtime:runtime-livedata:$livedata_runtime_version")

    //Firebase Auth
    implementation("com.google.firebase:firebase-auth-ktx:$firebase_auth_version")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$dagger_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_navigation_compose_version")
    implementation("androidx.navigation:navigation-compose:$navigation_compose_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // OpenCSV
    implementation("com.opencsv:opencsv:$csv_version")

    //Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:$firebase_bom_version"))


}
