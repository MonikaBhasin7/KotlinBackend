package com.example.orderManagement.product

import com.example.base.DatabaseFactory
import com.example.base.Response
import com.example.city.City
import com.example.orderManagement.Product
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

lateinit var productRepoImpl: ProductRepoImpl
fun Application.product() {
    productRepoImpl = ProductRepoImpl()
    DatabaseFactory.init()
    routing {
        product()
    }
}

fun Route.product() : Route {
    val route = route("/product") {
        addProduct()
        getProduct()
        getAllProducts()
        addProductType()
        getProductTypes()
    }
    return route
}

fun Route.addProduct() : Route {
    val route = route("/addProduct") {
        post {
            val productPayload = try {
                call.receive<Product>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest,"Missing some Response.")
                return@post
            }
            productPayload.checkAllFields().let { s ->
                if(s.isNotEmpty()) {
                    call.respond(HttpStatusCode.ExpectationFailed,s)
                    return@post
                } else {
                    productRepoImpl.addProduct(productPayload).let {
                        when(it) {
                            is Response.SuccessWithMessage -> {
                                call.respond(it.httpStatusCode, it.message)
                            }
                            is Response.SuccessWithData<*> -> {
                                call.respond(it.httpStatusCode, it.data as Product)
                            }
                            is Response.ErrorWithMessage -> {
                                call.respond(it.httpStatusCode, it.message)
                            }
                        }
                    }
                }
            }
        }
    }
    return route
}
fun Route.getProduct() : Route {
    val route = route("/getProduct") {

    }
    return route
}
fun Route.getAllProducts() : Route {
    val route = route("/addProduct") {

    }
    return route
}
fun Route.addProductType() : Route {
    val route = route("/addProduct") {

    }
    return route
}
fun Route.getProductTypes() : Route {
    val route = route("/addProduct") {

    }
    return route
}