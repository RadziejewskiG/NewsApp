
plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT)
}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK)

    defaultConfig {
        applicationId = AppConfig.ID_APP

        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        minSdkVersion(AppConfig.MIN_SDK)
        targetSdkVersion(AppConfig.TARGET_SDK)
        buildToolsVersion(AppConfig.BUILD_TOOLS_VERSION)

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    // All modules must be accessible for Hilt in the :app module to satisfy it's dependency graph.
    // These are not provided by the other modules implemented here(:sharedui -> :presentation(shared - api()), :uimatches, :uinews)
    // so they must be implemented separately.
    implementation(project(ModuleDependency.DATA))
    implementation(project(ModuleDependency.DOMAIN))

    // Needed for global navigation graph
    implementation(project(ModuleDependency.UI_MATCHES))
    implementation(project(ModuleDependency.UI_NEWS))

    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltAndroidCodegen)

    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)

}
