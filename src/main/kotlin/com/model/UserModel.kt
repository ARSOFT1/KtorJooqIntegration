package com.model

import com.Tables.USER
import com.enums.UserUserType
import com.helper.DbHelper
import com.tables.pojos.User

object UserModel {
    private val db = DbHelper.instance.db


    fun addNewUserToDb(name: String, email: String, password: String, userType: UserUserType): User? {
        return db.insertInto(USER)
            .set(USER.USER_NAME, name)
            .set(USER.USER_EMAIL, email)
            .set(USER.USER_PASSWORD, password)
            .set(USER.USER_TYPE, userType)
            .returning().fetchOne()?.into(User::class.java)
    }

    fun getUserWithEmailPassword(email:String, password: String): User? {
        return db.select().from(USER)
            .where(USER.USER_EMAIL.eq(email).and(USER.USER_PASSWORD.eq(password)))
            .fetchOne()?.into(User::class.java)
    }

    fun getUserWithId(userId:Int): User? {
       return db.select().from(USER).where(USER.USER_ID.eq(userId)).fetchOne()?.into(User::class.java)
    }
}