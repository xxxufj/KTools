plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    
    //仅仅在编译的时候起作用，建议总是使用最新版本，值是一个API Level
    compileSdkVersion(vCompileSdkVersion)

    //构建工具的版本，在build-tools中的那些(aapt,dexdump,zipalign,apksigner)，一般是API-Level.x.x
    buildToolsVersion(vBuildToolsVersion)
    
    ndkVersion = vNdkVersion

    defaultConfig {
        applicationId = "com.jiangkang.ktools"
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
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    configurations.all {
        resolutionStrategy.force("com.google.code.findbugs:jsr305:3.0.1")
        exclude("com.google.guava", "listenablefuture")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main").res.srcDirs(
                "src/main/res/layout/activity",
                "src/main/res/layout/fragment",
                "src/main/res/layout/widget",
                "src/main/res/layout/item",
                "src/main/res"
        )
    }

    testOptions {
        unitTests {

        }
    }

    dexOptions {
        javaMaxHeapSize = "4g"
    }

    lintOptions {

        lintConfig = file("$rootDir/quality/lint/lint.xml")

        //关闭检查指定的Issue Id
        disable("TypographyFractions", "TypographyQuotes")

        //打开指定的Issue的检查
        enable("RtlHardcoded", "RtlCompat", "RtlEnabled")

        //仅仅只检查这些的子集，其他的不检查，这个选项会覆盖上面的disable，enable配置
        check("NewApi", "InlinedApi")

        //如果设置为true，则会关闭lint的分析进度
        isQuiet = false

        //如果设置为true(默认)，如果发现错误就停止构建
        isAbortOnError = false

        //如果设置为true，则只报告error
        isIgnoreWarnings = false
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
    
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(AndroidX.recyclerView)
    androidTestImplementation(AndroidX.espressoCore) {
        exclude("com.android.support", "support-annotations")
    }

    androidTestImplementation(AndroidX.annotation)

    androidTestImplementation("org.hamcrest:hamcrest-library:2.1")
    // Optional -- UI testing with UI Automator
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    testImplementation(junit)
    testImplementation("org.mockito:mockito-core:3.3.3")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:1.5.1")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:1.5.1")

    debugImplementation("com.amitshekhar.android:debug-db:1.0.0")
    debugImplementation("com.facebook.sonar:sonar:0.6.13") {
        exclude("android.arch.lifecycle", "runtime")
    }

    implementation("com.jakewharton:butterknife:10.2.0")
    kapt("com.jakewharton:butterknife-compiler:10.2.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation(AndroidX.appcompat)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.cardView)
    implementation(AndroidX.constraintLayout)
    implementation(material)
    retrofit()
    debugImplementation("com.readystatesoftware.chuck:library:1.1.0")
    releaseImplementation("com.readystatesoftware.chuck:library-no-op:1.1.0")
    implementation("com.google.dagger:dagger-android:2.24")
    implementation("com.google.dagger:dagger-android-support:2.24")
    kapt("com.google.dagger:dagger-android-processor:2.24")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")
    okHttp()
    implementation("androidx.lifecycle:lifecycle-runtime:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-rxjava2:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    implementation(kotlin("stdlib-jdk7"))
    implementation("org.jetbrains.anko:anko:0.10.8")
    implementation(Kotlin.coroutines)
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("org.greenrobot:eventbus:3.1.1")
    implementation("com.github.anrwatchdog:anrwatchdog:1.4.0")
    implementation("androidx.navigation:navigation-fragment:2.3.0")
    implementation("androidx.navigation:navigation-ui:2.3.0")

    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")
    implementation(project(":widget"))
    implementation(project(":requests"))
    implementation(project(":annotations"))
    implementation(project(":hack"))
    implementation(project(":tools"))
    implementation(project(":kdownloader"))
    implementation(project(":hybrid"))
    implementation(project(":storage"))
    kapt(project (":compiler"))
    implementation(project(":jetpack"))
    implementation(project(":anko"))
    implementation(project(":image"))
    lintChecks(project (":klint"))
    implementation(project(":design"))
    implementation(project(":container"))
}

configurations.all {
    resolutionStrategy.eachDependency {
        val group = this.requested.group
        val name = this.requested.name
        val version = this.requested.version
        println("$group:$name:$version")
    }
}

task("copy", Copy::class) {
    from("build/outputs/apk/debug/")
    into("build/outputs/apk/")
    include("**/*")
    delete("build/outputs/apk/debug/*")
}


repositories {
    jcenter()
    mavenCentral()
    google()
}