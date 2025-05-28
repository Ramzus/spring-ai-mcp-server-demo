plugins {
    id("java")
    kotlin("jvm") version "2.1.20" apply false
    id("org.springframework.boot") version "3.4.5" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "com.adeo"
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/milestone")
            name = "Spring Milestones"
        }
    }
}

subprojects {
    apply(plugin = "java") // Apply Java plugin for Java sub-project configurations

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

