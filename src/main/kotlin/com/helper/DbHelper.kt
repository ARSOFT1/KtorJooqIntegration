package com.helper

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import java.sql.SQLException

class DbHelper {

    companion object {
        val instance by lazy { DbHelper() }
    }

    val db: DSLContext
    private val mysqlDataSource = buildDataSource()

    init {
        try {
            db = DSL.using(mysqlDataSource, SQLDialect.MYSQL)
        } catch (e: Exception) {
            throw SQLException("Database connection routes.error")
            println("DBHelper: dbInitError: ${e.message}")
        }
    }

    private fun buildDataSource(): HikariDataSource {
        val config = HikariConfig()
        // host : localhost, port : 3306, database : animefleek_db
        config.jdbcUrl = "jdbc:mysql://localhost:3306/animefleek_db?serverTimezone=UTC&characterEncoding=utf8"
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.username = "root"
        config.password = ""
        config.maximumPoolSize = 12

        return HikariDataSource(config)

    }

    fun getDaoConfiguration(): Configuration {
        return DefaultConfiguration().set(mysqlDataSource).set(SQLDialect.MYSQL)
    }
}