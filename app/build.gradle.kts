import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

//========================================
// Plugins Libraries
//========================================
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("deploygate")
    id("jacoco-android")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.gms.google-services")
}

//========================================
// Android Settings
//========================================
android {
    compileSdkVersion(28)
    buildToolsVersion = "28.0.3"

    defaultConfig {
        applicationId = "com.cotetsu.bison"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Lint Error 時でも ビルド処理を継続
    lintOptions {
        isAbortOnError = false
    }
}

//========================================
// Dependencies Implementation
//========================================
dependencies {
    // --------- Default Library --------------------
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    // --------- Firebase ---------------------------
    implementation("com.google.firebase:firebase-core:17.0.1")
    implementation("com.google.firebase:firebase-messaging:19.0.1")

    // --------- Dagger -----------------------------
    implementation("com.google.dagger:dagger-android:2.23.2")
    implementation("com.google.dagger:dagger-android-support:2.23.2")
    kapt("com.google.dagger:dagger-compiler:2.23.2")
    kapt("com.google.dagger:dagger-android-processor:2.23.2")

    // --------- Test -------------------------------
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

//========================================
// DeployGate Settings
//========================================
deploygate {
    // アプリ所有者 [ユーザー名] or [グループ名]
    userName = System.getenv("DEPLOYGATE_USER")
    // [ユーザー] or [グループ] の [API Key]
    token = System.getenv("DEPLOYGATE_TOKEN")
    // 追加設定（フレーバー毎）
    apks {
        // `debug` フレーバーに対応し `uploadDeployGateDebug` タスクで使用
        create("debug") {
            // 対象のファイルを設定
            sourceFile = file("${project.rootDir}/app/build/outputs/apk/debug/app-debug.apk")
            // 現在のコミットの git のハッシュ を使用（'git rev-parse --short HEAD'.execute([], project.rootDir).in.text.trim()）
            val hash =
                Runtime.getRuntime().exec("git rev-parse --short HEAD").inputStream.reader().use {
                    it.readText()
                }.trim()
            // ビルドのメッセージとして設定
            message = "debug build $hash"
            // `Git` コミットログ取得（val log = 'git log -n 5 --oneline | cut -c 9-'.execute().text）
            val log = Runtime.getRuntime().exec("git log -n 5 --oneline | cut -c 9-")
            // 配布ページを利用している場合、以下の設定で同時に更新可
            distributionKey = System.getenv("DEPLOYGATE_DISTRIBUTION_KEY")
            releaseNote = "$log"
        }
    }
}

//========================================
// Code Coverage
//========================================
jacocoAndroidUnitTestReport {
    csv.enabled(false)
    html.isEnabled
    xml.isEnabled
}

//========================================
// Ktlint Settings
//========================================
ktlint {
    version.set("0.22.0")
    android.set(true)
    reporters.set(setOf(ReporterType.PLAIN, ReporterType.CHECKSTYLE))
    ignoreFailures.set(true)
}
