package com.compose.dao

import com.compose.model.*

interface DAOFacade {
    suspend fun allArticles(): List<Article>
    suspend fun getArticleById(id: Int): Article?
    suspend fun addNewArticle(title: String, body: String): Article?
    suspend fun editArticle(id: Int, title: String, body: String): Boolean
    suspend fun deleteArticle(id: Int): Boolean
}