# KtorJooqIntegration

In this project we will learn how to connect with MySQL database using KTOR Framework.
We will use JOOQ plugin to generate database tables, Pojos, Daos etc.

Ktor is a framework to easily build connected applications – web applications, HTTP services, mobile and browser applications. Modern connected applications need to be asynchronous to provide the best experience to users, and Kotlin coroutines provide awesome facilities to do it in an easy and straightforward way.


# Jooq Plugin & Dependencies
id("nu.studer.jooq") version "7.1.1"

implementation("mysql:mysql-connector-java:8.0.30")

implementation("org.jooq:jooq")

jooqGenerator("mysql:mysql-connector-java:8.0.30")

implementation("com.zaxxer:HikariCP:5.0.1")


#  JDBC URL
   host : localhost, port : 3306, database : database_name
   
"jdbc:mysql://localhost:3306/database_name?serverTimezone=UTC&characterEncoding=utf8"


# Jooq Integration code

jooq {

    version.set("3.16.10")
    configurations {
    
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false)  // default (can be omitted)

            jooqConfiguration.apply {
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
                        inputSchema = "database_name"
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


