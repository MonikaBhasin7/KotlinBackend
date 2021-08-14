package com.example

import com.example.Repo.DatabaseFactory
import com.example.city.City
import com.example.city.City_Table
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
//    install(Sessions) {
//        cookie<MySession>("MY_SESSION") {
//            cookie.extensions["SameSite"] = "lax"
//        }
//    }
//
//    install(Authentication) {
//    }
//
//    install(ContentNegotiation) {
//        gson {
//        }
//    }
//
//    routing {
//        get("/") {
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }
//        route("/monika") {
//            //localhost:8080/monika
//            post {
//                val body = call.receive<String>()
//                call.respond("body")
//            }
//            get("/{id}") {
//                val id = call.parameters["id"]
//                call.respond("$id")
//            }
//        }
//    }
//
//    routing {
//        getNews()
//    }

    DatabaseFactory.init()
    install(ContentNegotiation) {
        gson {  }
    }

    routing {
        aboutCity()
    }
}

fun Route.aboutCity(): Route {
    val cityRoute = route("/city") {
        get {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/{name}") {

        }

        get("/{id}") {

        }

        post("/addCity") {
            val cityPayload = try {
                call.receive<City>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest,"Missing some Response.")
                return@post
            }

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    DatabaseFactory.dbQuery {
                        City_Table.insert { ct ->
                            ct[id] = cityPayload.id
                            ct[cityName] = cityPayload.cityName
                            ct[area] = cityPayload.area
                        }
                    }
                    call.respond(HttpStatusCode.OK,"done")
                }
            } catch (e: java.lang.Exception){
                call.respond(HttpStatusCode.Conflict,"Some Problem Occurred!")
            }
        }
    }
    return cityRoute
}

fun Route.getNews() : Route {
    val route = route("") {

    }
    return route
}


data class MySession(val count: Int = 0)

