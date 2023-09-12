package com.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.compose.dao.exposeDao
import com.controller.Response
import com.controller.UserController
import com.tables.pojos.User
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting(issuer: String, audience: String, secret: String) {

    routing {

        post("/login") {
            val user = call.receive<User>()

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.userName)
                .withClaim("userId", user.userId)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))
        }

        authenticate("auth-jwt") {
            get("/hello") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val userId = principal.payload.getClaim("userId").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username$userId! Token is expired at $expiresAt ms.")
            }
        }

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

