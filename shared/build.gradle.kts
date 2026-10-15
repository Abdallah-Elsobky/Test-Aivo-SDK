import java.util.Properties
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

abstract class GenerateBuildConfigTask : DefaultTask() {
    @get:InputFile
    @get:Optional
    abstract val localPropertiesFile: RegularFileProperty

    @get:OutputFile
    abstract val outputConfigFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val props = Properties()
        val propFile = localPropertiesFile.orNull?.asFile
        if (propFile != null && propFile.exists()) {
            propFile.inputStream().use { props.load(it) }
        }

        val weatherKey = props.getProperty("WEATHER_API_KEY") ?: ""
        val ollamaKey = props.getProperty("OLLAMA_API_KEY") ?: ""

        val dest = outputConfigFile.get().asFile
        dest.parentFile.mkdirs()
        dest.writeText(
            """
            package com.example.test_sdk.config

            /**
             * Build configuration properties loaded from local.properties.
             *
             * Automatically managed and kept in sync with local.properties via Gradle.
             */
            object BuildConfig {
                const val WEATHER_API_KEY: String = "$weatherKey"
                const val OLLAMA_API_KEY: String = "$ollamaKey"
            }
            """.trimIndent() + "\n"
        )
    }
}

val generateBuildConfig = tasks.register<GenerateBuildConfigTask>("generateBuildConfig") {
    localPropertiesFile.set(rootProject.layout.projectDirectory.file("local.properties"))
    outputConfigFile.set(layout.projectDirectory.file("src/commonMain/kotlin/com/example/test_sdk/config/BuildConfig.kt"))
}

tasks.matching { it.name.startsWith("compile") }.configureEach {
    dependsOn(generateBuildConfig)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    android {
       namespace = "com.example.test_sdk.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Aivo SDK & Networking
            implementation(libs.bundles.aivo.full)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}