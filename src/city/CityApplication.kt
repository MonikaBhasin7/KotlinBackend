package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.city() {
    install(ContentNegotiation) {
        gson {  }
    }

//    routing {
//        aboutCity()
//    }
}

//fun Route.aboutCity(): Route {
//    val cityRoute = route("/city") {
//        get {
//
//        }
//
//        get("/{name}") {
//
//        }
//
//        get("/{id}") {
//
//        }
//
//        post("/addCity") {
//            val cityPayload = try {
//               call.receive<City_Table>()
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.BadRequest,"Missing some Response.")
//                return@post
//            }
//
//            try {
//                CoroutineScope(Dispatchers.IO).launch {
//                    dbQuery {
//                        City_Table.insert { ct->
//                            ct[City_Table.id] = cityPayload.id
//                            ct[City_Table.cityName] = cityPayload.cityName
//                            ct[City_Table.area] = cityPayload.area
//                        }
//                    }
//                    withContext(Dispatchers.Main) {
//                        call.respond(HttpStatusCode.OK,"done")
//                    }
//                }
//            } catch (e: java.lang.Exception){
//                call.respond(HttpStatusCode.Conflict,"Some Problem Occurred!")
//            }
//        }
//    }
//    return cityRoute
//}