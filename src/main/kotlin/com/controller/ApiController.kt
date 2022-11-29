package com.controller

import com.enums.UserUserType
import com.model.ApiModel
import com.plugins.ResponseToClient
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ApiController(private val call: ApplicationCall) {

    suspend fun addNewUser() {
        val param = call.receiveParameters()
        val name = param["name"] ?: error("user name required!")
        val email = param["email"] ?: error("user email required!")
        val password = param["password"] ?: error("user password required!")
        val type = param["type"] ?: error("uer type required!")

        val response = ApiModel.addNewUser(name, email, password, UserUserType.valueOf(type))
        call.respond(ResponseToClient(success = true, msg = "", data = response))
    }
}