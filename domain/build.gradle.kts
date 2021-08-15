plugins {
    id(GradlePluginId.JAVA_LIBRARY)
    id(GradlePluginId.KOTLIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    implementation(AppDependencies.javaXInject)

    implementation(AppDependencies.kotlin)

}
