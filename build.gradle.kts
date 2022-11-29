val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    id("nu.studer.jooq") version "7.1.1"
}

group = "com"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    //dependencies related to database
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.jooq:jooq")
    jooqGenerator("mysql:mysql-connector-java:8.0.30")
    implementation("com.zaxxer:HikariCP:5.0.1")


    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

jooq {
    version.set("3.16.10")
    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false)  // default (can be omitted)

            jooqConfiguration.apply {
                //logging = Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3306?serverTimezone=UTC"
                    user = "root"
                    password = ""
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "animefleek_db"
                        includes = ".*"
                        isDateAsTimestamp = false
                    }
                    generate.apply {
                        isDaos = true
                        isPojos = true
                        isRelations = true
                        isPojosEqualsAndHashCode = true
                    }
                    target.apply {
                        packageName = "com"
                        //directory = "build/generated-src/jooq/main"  // default (can be omitted)
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}