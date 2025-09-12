plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.eliascoelho911.efood"
version = "0.0.1.SNAPSHOT"
description = "Service Registry"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
	}
}

repositories {
	mavenCentral()
}


dependencies {
	implementation(libs.kotlin.reflect)
	implementation(libs.spring.cloud.starter.netflix.eureka.server)
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
		freeCompilerArgs.addAll(".Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
