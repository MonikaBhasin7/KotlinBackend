package com.example

import com.example.base.DatabaseFactory
import com.example.city.City
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

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
    install(ContentNegotiation) {
        gson {
        }
    }
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

//    DatabaseFactory.init()
//    install(ContentNegotiation) {
//        gson {  }
//    }
//
//    routing {
//        aboutCity()
//    }
}

fun Route.aboutCity(): Route {
    var conn: Connection? = null
    var stmt: Statement? = null
    var resultset: ResultSet? = null
    val cityRoute = route("/city") {
        get {

            launch {
//                transaction(Database.connect(DatabaseFactory.hikari())) {
////                    launch {
////                        var count = 0
////                        val resultSet = TransactionManager.current().connection
////                            .prepareStatement("Select * from city_table;", false).executeQuery()
////                        while(resultSet.next()) {
////                            count += 1
////                        }
////                        call.respondText("Hello")
////                    }
//
//                    exec("Select * from city_table;") {
//                        launch {
//                            try {
//                                while(it.next()) {
////                                    call.respondText("${it.getInt("id")}")
//                                }
//                                call.respondText("Hello world")
//                            } catch (e: Exception) {}
//                            finally {
//
//                            }
//                        }
//                    }
//                }
                //call.respondText("Hello world")

//                transaction {
//                    launch {
//                        var result = ""
//                        transaction {
//                            TransactionManager.current().exec("Select id from city_table") { rs ->
//                                launch {
//                                    while (rs.next()) {
//                                        result += rs.getLong("id").toString()
//                                    }
//                                    withContext(Dispatchers.Main) {
//                                        call.respondText("Hello world ${result}")
//                                    }
//                                }
//
//                            }
//                        }
//
//
//
//                    }
//                }


                val conn = DriverManager.getConnection(System.getenv("DATABASE_URL"))
                var prepareStatement = conn.prepareStatement("Select id from city_table")
                var result = ""
                prepareStatement.executeQuery().apply {
                    while(next()) {
                        result += getInt("id").toString()
                    }
                    close()
                    call.respondText("Hello world $result")
                }
//                prepareStatement.close()
//                conn.commit()
//                transaction {
//                            TransactionManager.current().exec("Select id from city_table") { rs ->
//                                launch {
//                                    while (rs.next()) {
//                                        result += rs.getLong("id").toString()
//                                    }
//                                    withContext(Dispatchers.Main) {
//                                        call.respondText("Hello world ${result}")
//                                    }
//                                }
//                            }
//                }


            }

        }

        get("/{name}") {

        }

        get("/{id}") {

        }

//        post("/addCity") {
//            val cityPayload = try {
//                call.receive<City>()
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.BadRequest,"Missing some Response.")
//                return@post
//            }
//
//            try {
//                CoroutineScope(Dispatchers.IO).launch {
//
//                    DatabaseFactory.dbQuery {
//                        City_Table.insert { ct ->
//                            ct[id] = cityPayload.id
//                            ct[cityName] = cityPayload.cityName
//                            ct[area] = cityPayload.area
//                        }
//                    }
//                    call.respond(HttpStatusCode.OK,"done")
//                }
//            } catch (e: java.lang.Exception){
//                call.respond(HttpStatusCode.Conflict,"Some Problem Occurred!")
//            }
//        }




        post("/addCity") {
            val cityPayload = try {
                call.receive<City>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest,"Missing some Response.")
                return@post
            }

//            try {
                val conn = DriverManager.getConnection(System.getenv("DATABASE_URL"))
                cityPayload.let {
                    //conn.createStatement().execute("Insert into city_table (id,city_name,area) VALUES (?,?,?)")
                    var prepareStatement = conn.prepareStatement("Insert into city_table (id,city_name,area) VALUES (?,?,?)").apply {
                        setLong(1, 15)
                        setString(2, it.cityName)
                        setInt(3, it.area)
                    }
                    prepareStatement.executeUpdate()
                    call.respond(HttpStatusCode.OK,"done")
//                prepareStatement.close()
                conn.close()
                }

//            } catch (e: java.lang.Exception){
//                call.respond(HttpStatusCode.Conflict,"Some Problem Occurred!")
//            }
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

