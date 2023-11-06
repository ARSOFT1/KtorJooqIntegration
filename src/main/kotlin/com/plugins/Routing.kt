package com.plugins

import com.compose.dao.exposeDao
import com.controller.Response
import com.controller.UserController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(issuer: String, audience: String, secret: String) {

    routing {

        post("/user/add") {
            UserController(call).addNewUser()
        }

        // Today we will fetch user information with user id
        // First of all we will create end point rout
        get("user/{id}") {
            UserController(call).getUserById()
        }

        route("article") {
            get {
                call.respond(Response(success = true, data = mapOf("" to exposeDao.allArticles())))
            }
        }


    }


}

