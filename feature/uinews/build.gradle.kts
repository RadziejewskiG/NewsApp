
plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
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

    implementation(project(ModuleDependency.SHARED_UI))

    implementation(AppDependencies.swipeToRefreshLayout)

    implementation(AppDependencies.shimmer)

    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltAndroidCodegen)

    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)

}
