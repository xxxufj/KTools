plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {

    //仅仅在编译的时候起作用，建议总是使用最新版本，值是一个API Level
    compileSdkVersion(vCompileSdkVersion)

    //构建工具的版本，在build-tools中的那些(aapt,dexdump,zipalign,apksigner)，一般是API-Level.x.x
    buildToolsVersion(vBuildToolsVersion)

    defaultConfig {
        minSdkVersion(vMinSdkVersion)
        targetSdkVersion(vTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        ndk {
            abiFilters("arm64-v8a")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(AndroidX.appcompat)
    implementation(AndroidX.constraintLayout)

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    testImplementation(junit)

    androidTestImplementation(AndroidX.testRunner)
    androidTestImplementation(AndroidX.espressoCore)

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.anko:anko:0.10.8")

}

repositories {
    jcenter()
    mavenCentral()
    google()
}
