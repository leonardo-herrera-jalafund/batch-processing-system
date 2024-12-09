plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("junit:junit:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}
