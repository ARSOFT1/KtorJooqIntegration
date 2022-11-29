package com.model

import com.Tables.POSTS
import com.Tables.USER
import com.enums.PostsPostPriority
import com.enums.UserUserType
import com.helper.DbHelper
import com.tables.pojos.Posts
import com.tables.pojos.User

object ApiModel {

    private val db = DbHelper.instance.db

    fun addNewPost(userId: Int, title: String, priority: PostsPostPriority, writerUserId: Int): Posts? {
        return db.insertInto(POSTS)
            .set(POSTS.SEO_USER_ID, userId)
            .set(POSTS.POST_TITLE, title)
            .set(POSTS.WRITER_USER_ID, writerUserId)
            .returning().fetchOne()?.into(Posts::class.java)
    }

    fun addNewUser(username: String, email: String, password: String, userType: UserUserType): User? {
        return db.insertInto(USER)
            .set(USER.USER_NAME, username)
            .set(USER.USER_EMAIL, email)
            .set(USER.USER_PASSWORD, password)
            .set(USER.USER_TYPE, userType)
            .returning().fetchOne()?.into(User::class.java)
    }

    fun getUsersList(): MutableList<User> {
        return db.select().from(USER).fetchInto(User::class.java)
    }
}