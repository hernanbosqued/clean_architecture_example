rootProject.name = "clean_architecture_example"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":ui")

include(":domain")

include(":presenter:di")
include(":presenter:public")
include(":presenter:impl")

include(":controller:di")
include(":controller:impl")

include(":use_case:impl")
include(":use_case:public")
include(":use_case:di")
include("backend")

