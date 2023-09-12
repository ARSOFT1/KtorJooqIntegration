package com.compose.model

import org.jetbrains.exposed.sql.Table

data class Article(val id:Int, val title:String, val body:String)

object Articles : Table(){
    val id = integer("id").autoIncrement()
    val title = varchar("title", 120)
    val body = varchar("body", 1120)

    override val primaryKey = PrimaryKey(id)
}