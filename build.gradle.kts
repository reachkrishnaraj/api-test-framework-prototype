plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"
val allureVersion = "2.25.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    implementation(platform("org.junit:junit-bom:5.9.1"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")
    implementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    implementation("io.qameta.allure:allure-rest-assured")
    implementation("org.hamcrest:hamcrest:2.2")
    implementation("com.opencsv:opencsv:5.9")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
    implementation("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.junit.jupiter:junit-jupiter-params")
    implementation("org.junit-pioneer:junit-pioneer:2.2.0")
    implementation("io.rest-assured:rest-assured:5.4.0")
    implementation("io.rest-assured:json-schema-validator:5.4.0");


}

tasks.test {
    useJUnitPlatform()
}