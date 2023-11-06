package com.controller

import com.enums.UserUserType
import com.model.UserModel
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*


class UserController(private val call: ApplicationCall) {

    suspend fun addNewUser() {
        val param = call.receiveParameters()
        val name = param["name"] ?: error("user name required!")
        val email = param["email"] ?: error("user email required!")
        val password = param["password"] ?: error("user password required!")
        val userType = param["type"]?.let { UserUserType.valueOf(it) } ?: error("user type required!")

        val response = UserModel.addNewUserToDb(name, email, password, userType)

        call.respond(Response(success = true, data = response))
    }

    suspend fun getUserById(){
        val userId = call.parameters["id"]?.toIntOrNull() ?: error(message = "enter valid user id")
        val user = UserModel.getUserWithId(userId)

        if (user != null) {
            call.respond(Response(success = true, data = user))
        }else{
            call.respond(message = "user record not found!")
        }
    }

}

data class Response(val success: Boolean, val data: Any?)