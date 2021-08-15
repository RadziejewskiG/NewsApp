
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
}

dependencies {

    implementation(project(ModuleDependency.DOMAIN))

    implementation(AppDependencies.kotlin)

    implementation(AppDependencies.navigation)

    implementation(AppDependencies.viewModel)

    implementation(AppDependencies.paging)

    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltAndroidCodegen)

    implementation(AppDependencies.room)
    kapt(AppDependencies.roomCodegen)

    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)

}
