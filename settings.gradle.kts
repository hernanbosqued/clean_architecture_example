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

include(":frontend:viewmodel:auth:di")
include(":frontend:viewmodel:auth:impl")
include(":frontend:viewmodel:auth:public")

include(":frontend:viewmodel:task:di")
include(":frontend:viewmodel:task:impl")
include(":frontend:viewmodel:task:public")

include(":frontend:repository:di")
include(":frontend:repository:impl")
include(":frontend:repository:public")

include(":frontend:use_case:auth:di")
include(":frontend:use_case:auth:impl")
include(":frontend:use_case:auth:public")

include(":frontend:use_case:task:di")
include(":frontend:use_case:task:impl")
include(":frontend:use_case:task:public")

include(":domain")

include(":backend:presenter:di")
include(":backend:presenter:public")
include(":backend:presenter:impl")

include(":backend:auth_api_gateway:google:di")
include(":backend:auth_api_gateway:google:impl")

include(":backend:db_controller:json:di")
include(":backend:db_controller:json:impl")

include(":backend:db_controller:sqlite:di")
include(":backend:db_controller:sqlite:impl")

include(":backend:use_case:db:impl")
include(":backend:use_case:db:public")
include(":backend:use_case:db:di")

include(":backend:use_case:auth:impl")
include(":backend:use_case:auth:public")
include(":backend:use_case:auth:di")

include(":backend")
