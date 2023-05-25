import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

val protobufVersion by extra("3.17.3")
val protobufPluginVersion by extra("0.8.14")
val grpcVersion by extra("1.40.1")

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
	id("com.google.protobuf") version "0.8.17"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("net.devh:grpc-server-spring-boot-starter:2.12.0.RELEASE")

	implementation("io.grpc:grpc-protobuf:${grpcVersion}")
	implementation("io.grpc:grpc-stub:1.40.1")
	implementation("io.grpc:grpc-kotlin-stub:1.1.0")
	compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5") // Java 9+ compatibility - Do NOT update to 2.0.0
	implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
	implementation("net.devh:grpc-client-spring-boot-starter:2.12.0.RELEASE")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

	implementation("com.google.code.gson:gson:2.10.1")


//	jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	if (JavaVersion.current().isJava9Compatible) {
		implementation("javax.annotation:javax.annotation-api:+")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging.showStandardStreams = true
}

sourceSets {
	test {
		java.srcDirs.add(File("src/test/kotlin"))
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:${protobufVersion}"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.1.0:jdk7@jar"
		}
	}

	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.builtins {
				java {}
				kotlin {}
			}
			it.plugins {
				id("kotlin")
				id("grpc")
				id("grpckt")
			}
		}
	}
}