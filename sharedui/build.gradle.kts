
plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_PARCELIZE)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT)
}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK)

    defaultConfig {
        minSdkVersion(AppConfig.MIN_SDK)
        targetSdkVersion(AppConfig.TARGET_SDK)
        buildToolsVersion(AppConfig.BUILD_TOOLS_VERSION)

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures.apply {
        viewBinding = true
    }

}

dependencies {

    api(project(ModuleDependency.PRESENTATION))

    api(AppDependencies.kotlin)
    api(AppDependencies.coroutinesAndroid)

    api(AppDependencies.androidLibraries)
    api(AppDependencies.ui)

    api(AppDependencies.navigation)

    api(AppDependencies.lottie)

    api(AppDependencies.paging)

    api(AppDependencies.viewModel)

    api(AppDependencies.glide)
    kapt(AppDependencies.glideCodegen)

    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltAndroidCodegen)

    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)

}
