plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
    id ("dagger.hilt.android.plugin")
    id("kotlin-parcelize")


}

android {
    namespace = "com.example.habitstracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habitstracker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.habitstracker.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //Kapt scehma directory path when created
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }

        // Ensure schema directory creation if doesn't exist
        tasks.register("createSchemaDir") {
            doLast {
                val schemaDir = File("$projectDir/schemas")
                if (!schemaDir.exists())
                    schemaDir.mkdirs()
            }
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            dependsOn("createSchemaDir")
        }
    }

    //Stage of app (alpha, beta, release...)
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("profile") {
            initWith(getByName("debug"))
            isDebuggable = false
            isProfileable = true
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

/*    testOptions {
        unitTests.isIncludeAndroidResources = true
        execution "ANDROIDX_TEST_ORCHESTRATOR"
    }*/
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
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.room.ktx)
    implementation(libs.kotlin.serialization.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // Актуальна версія


    //implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.junit.junit)

    kapt(libs.androidx.room.compiler)
    kapt(libs.hilt.android.compiler)
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.51.1")


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.51.1")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}