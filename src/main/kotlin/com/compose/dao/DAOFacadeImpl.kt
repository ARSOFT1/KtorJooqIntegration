package com.compose.dao

import com.compose.model.Article
import com.compose.model.Articles
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl :DAOFacade{

    private fun resultRowToArticle(row: ResultRow) = Article(id = row[Articles.id], title = row[Articles.title], body = row[Articles.body])

    override suspend fun allArticles(): List<Article> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticleById(id: Int): Article = DatabaseFactory.dbQuery {
        Articles
            .select { Articles.id eq id }
            .map(::resultRowToArticle)
            .single()
    }

    override suspend fun addNewArticle(title: String, body: String): Article {
        return DatabaseFactory.dbQuery {
            val insertStatement = Articles.insert {
                it[Articles.title] = title
                it[Articles.body] = body
            }
            insertStatement.resultedValues?.single()?.let(::resultRowToArticle)
        }
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean {
        return DatabaseFactory.dbQuery {
            Articles.update({ Articles.id eq id }) {
                it[Articles.title] = title
                it[Articles.body] = body
            } > 0
        }
    }

    override suspend fun deleteArticle(id: Int): Boolean {
        return DatabaseFactory.dbQuery {
            Articles.deleteWhere { Articles.id eq id } > 0
        }
    }
}

val exposeDao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allArticles().isEmpty()) {
            addNewArticle("The drive to develop!", "...it's what keeps me going.")
        }
    }
}