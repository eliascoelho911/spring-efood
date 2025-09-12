plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.eliascoelho911.efood"
version = "0.0.1.SNAPSHOT"
description = "Api gateway microservice"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.cloud.starter.gateway)
	implementation(libs.kotlin.reflect)
	implementation(libs.spring.cloud.starter.netflix.eureka.client)
	developmentOnly(libs.spring.boot.devtools)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.dependencies.get().toString())
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
    enabled = false
    archiveClassifier = ""
}

tasks.bootJar {
    archiveFileName.set("efood-api-gateway.jar")
}
