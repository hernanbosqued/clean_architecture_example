rootProject.name = "clean_architecture_example"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/")
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
include(":frontend:repository:public")
include(":frontend:use_case:auth:di")
include(":frontend:use_case:auth:impl")
include(":frontend:use_case:auth:public")

include(":backend:domain")
include(":backend:presenter:di")
include(":backend:presenter:public")
include(":backend:presenter:impl")
include(":backend:json_controller:di")
include(":backend:json_controller:impl")
include(":backend:db_controller:di")
include(":backend:db_controller:impl")
include(":backend:use_case:impl")
include(":backend:use_case:public")
include(":backend:use_case:di")
include(":backend")
