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

    implementation("org.jodconverter:jodconverter-remote:4.4.7")
    implementation("org.jodconverter:jodconverter-spring-boot-starter:4.4.7")
    //implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.converter.odt.odfdom:2.0.4")
    //implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.converter.docx.docx4j:2.0.4")
    //implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.converter.docx.xwpf:2.0.4")
    //implementation("fr.opensagres.xdocreport:fr.opensagres.xdocreport.document.docx:2.0.4")
    //implementation("fr.opensagres.xdocreport:xdocreport:2.0.4")
    //implementation("org.apache.poi:poi-ooxml:5.2.5")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
