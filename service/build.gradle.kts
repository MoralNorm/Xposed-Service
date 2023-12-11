plugins {
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "io.github.libxposed.service"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        buildConfig = false
        resValues = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(project(":interface"))
    compileOnly("androidx.annotation:annotation:1.7.0")
}

afterEvaluate {
    publishing {
        publications {
            release(MavenPublication) {
                from(components.getByName("release"))        // 表示发布 release（jitpack 都不会使用到）
                groupId = "com.fan.libxposed" // 这个是依赖库的组 id
                artifactId = "service"       // 依赖库的名称（jitpack 都不会使用到）
                version = "100-1.0.0" //
            }
        }
    }
}

signing {
    val signingKey = findProperty("signingKey") as String?
    val signingPassword = findProperty("signingPassword") as String?
    if (!signingKey.isNullOrBlank() && !signingPassword.isNullOrBlank()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

afterEvaluate {
    publishing {
        publications {
            release(MavenPublication) {
                from components.release         // 表示发布 release（jitpack 都不会使用到）
                groupId = 'com.fan.libxposed' // 这个是依赖库的组 id
                artifactId = 'service'       // 依赖库的名称（jitpack 都不会使用到）
                version = "100-1.0.0" //
            }
        }
    }
}
