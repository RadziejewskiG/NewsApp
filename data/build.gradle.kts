
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

        buildConfigField("String", "BASE_URL", "\"https://mobile-recruitment.footballco.cloud/\"")
        buildConfigField("String", "DB_NAME", "\"news-db\"")
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

    // Fake matches API - it is an implementation so it belongs to the data layer.
    // This module also contains MatchesSocketFake class which imports android.app.Activity(so it doesn't belong to the data layer)
    // but they(MatchesApiFake, MatchesSocketFake) are in the same file which I'm not allowed to edit.
    api(project(ModuleDependency.RECRUITMENT))

    implementation(project(ModuleDependency.DOMAIN))

    api(AppDependencies.networking)

    api(AppDependencies.room)
    kapt(AppDependencies.roomCodegen)

    api(AppDependencies.moshi)
    kapt(AppDependencies.moshiCodegen)

    implementation(AppDependencies.paging)

    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltAndroidCodegen)

    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)

}
