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

include(":frontend:ui")
include(":frontend:repository:di")
include(":frontend:repository:impl")

include(":backend:domain")
include(":backend:presenter:di")
include(":backend:presenter:public")
include(":backend:presenter:impl")
include(":backend:controller:di")
include(":backend:controller:impl")
include(":backend:use_case:impl")
include(":backend:use_case:public")
include(":backend:use_case:di")
include(":backend")

