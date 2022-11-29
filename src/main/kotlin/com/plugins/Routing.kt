package com.plugins

import com.controller.ApiController
import com.model.ApiModel
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("welcome to jooq integration with ktor framework")
        }
        post("/add/user") {
            ApiController(call).addNewUser()
        }
        get("/users") {
            val userList = ApiModel.getUsersList()
            call.respond(ResponseToClient(success = true, data = userList))
        }
    }


}

data class ResponseToClient(val success: Boolean, val msg: String? = null, val data: Any?)
