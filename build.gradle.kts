buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath(kotlin("gradle-plugin", version = "1.3.41"))
        classpath("com.deploygate:gradle:2.0.1")
        classpath("com.dicedmelon.gradle:jacoco-android:0.1.4")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:8.2.0")
        classpath("com.google.gms:google-services:4.2.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
