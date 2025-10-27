plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 33 // Mantido em 33, como você pediu

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 33 // Mantido em 33
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        // Mantida a sua versão do compilador.
        // Ela é compatível com o Compose BoM que eu escolhi (2023.06.01)
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Mantido (compatível com SDK 33)
    implementation("androidx.core:core-ktx:1.10.1")

    // Mantido (compatível com SDK 33)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Mantido (compatível com SDK 33)
    implementation("androidx.activity:activity-compose:1.7.2")

    // Mantido (compatível com SDK 33)
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.espresso.core)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

    // Mantido
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Mantido
    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    // Room Database
    // Mantido (compatível com SDK 33)
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Mantido
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // ❗️❗️ MUDANÇA: Força ESTREITAMENTE a versão 1.3.0 do emoji2
    implementation("androidx.emoji2:emoji2") {
        version {
            strictly("1.3.0")
        }
    }
}