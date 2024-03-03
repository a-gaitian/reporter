plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "io.gaitian"
version = "0.0.1-SNAPSHOT"

java {
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // TODO Version Catalog

    implementation("org.jodconverter:jodconverter-remote:4.4.7")
    implementation("org.jodconverter:jodconverter-spring-boot-starter:4.4.7")

    implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.template.velocity:2.0.4")
    implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.document.docx:2.0.4")
    implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.document:2.0.4")

    implementation("org.apache.poi:poi-ooxml:5.2.5")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.testcontainers:testcontainers:1.19.6")
    testImplementation("org.testcontainers:junit-jupiter:1.19.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
