import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.parcelize")
    kotlin("kapt")
    // Hilt
    id("com.google.dagger.hilt.android")
    // Lint
    id("org.jlleitschuh.gradle.ktlint")
}


fun loadEnv(fileName: String): Properties = Properties().apply {
    val f = rootProject.file(fileName)
    if (f.exists()) f.inputStream().use { load(it) }
}

val envDev = loadEnv(".env")
val envStaging = loadEnv(".env.staging")
val envProd = loadEnv(".env.prod")

android {
    namespace = "com.newAndroid.newandroidjetpackcompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.newAndroid.newandroidjetpackcompose"
        minSdk = 26
        targetSdk = 36
        versionCode = envDev.getProperty("VERSION_CODE", "1").toInt()
        versionName = envDev.getProperty("VERSION_NAME", "1.0.0")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Default (dev env file) when no flavor chosen directly
        buildConfigField("String", "API_URL", "\"${envDev.getProperty("API_URL", "")}\"")
        buildConfigField("String", "APP_FLAVOR", "\"${envDev.getProperty("APP_FLAVOR", "dev")}\"")
        buildConfigField("String", "APP_NAME", "\"${envDev.getProperty("APP_NAME", "App")}\"")
        resValue("string", "app_name", envDev.getProperty("APP_NAME", "App"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "env"

    productFlavors {
        create("dev") {
            dimension = "env"
            versionCode =
                envDev.getProperty("VERSION_CODE", envDev.getProperty("VERSION", "1")).toInt()
            versionName = envDev.getProperty("VERSION_NAME", "1.0.0")
            buildConfigField("String", "API_URL", "\"${envDev.getProperty("API_URL", "")}\"")
            buildConfigField(
                "String",
                "APP_FLAVOR",
                "\"${envDev.getProperty("APP_FLAVOR", "dev")}\""
            )
            buildConfigField("String", "APP_NAME", "\"${envDev.getProperty("APP_NAME", "Dev")}\"")
            resValue("string", "app_name", envDev.getProperty("APP_NAME", "Dev"))
        }
        create("staging") {
            dimension = "env"
            versionCode =
                envStaging.getProperty("VERSION_CODE", envDev.getProperty("VERSION_CODE", "1"))
                    .toInt()
            versionName =
                envStaging.getProperty("VERSION_NAME", envDev.getProperty("VERSION_NAME", "1.0.0"))
            buildConfigField(
                "String",
                "API_URL",
                "\"${envStaging.getProperty("API_URL", envDev.getProperty("API_URL", ""))}\""
            )
            buildConfigField(
                "String",
                "APP_FLAVOR",
                "\"${envStaging.getProperty("APP_FLAVOR", "staging")}\""
            )
            buildConfigField(
                "String",
                "APP_NAME",
                "\"${envStaging.getProperty("APP_NAME", "Staging")}\""
            )
            resValue("string", "app_name", envStaging.getProperty("APP_NAME", "Staging"))
        }
        create("prod") {
            dimension = "env"
            versionCode =
                envProd.getProperty("VERSION_CODE", envDev.getProperty("VERSION_CODE", "1")).toInt()
            versionName =
                envProd.getProperty("VERSION_NAME", envDev.getProperty("VERSION_NAME", "1.0.0"))
            buildConfigField(
                "String",
                "API_URL",
                "\"${envProd.getProperty("API_URL", envDev.getProperty("API_URL", ""))}\""
            )
            buildConfigField(
                "String",
                "APP_FLAVOR",
                "\"${envProd.getProperty("APP_FLAVOR", "prod")}\""
            )
            buildConfigField("String", "APP_NAME", "\"${envProd.getProperty("APP_NAME", "App")}\"")
            resValue("string", "app_name", envProd.getProperty("APP_NAME", "App"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    // OkHttp3
    implementation(libs.okhttp)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

hilt {
    enableAggregatingTask = true
}

kapt {
    correctErrorTypes = true
}
// Lint
ktlint {
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
