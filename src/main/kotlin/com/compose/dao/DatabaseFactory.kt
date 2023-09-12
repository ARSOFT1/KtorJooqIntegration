package com.compose.dao

import com.compose.model.Article
import com.compose.model.Articles
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*


object DatabaseFactory {
    fun init() {
        val driverClass = "org.h2.Driver"
        val jdbUrl = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbUrl, driverClass)
        //After obtaining the connection, all SQL statements should be placed inside a transaction
        transaction(database) {
            SchemaUtils.create(Articles)
        }
    }

    suspend fun <T> dbQuery(block: () -> T?): T =
        newSuspendedTransaction(Dispatchers.IO) { block()!! }
}