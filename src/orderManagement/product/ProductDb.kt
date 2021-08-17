package com.example.orderManagement

import io.ktor.auth.*
import org.jetbrains.exposed.sql.Table

object Product_Table : Table("product_table") {
    private val productId = long("product_id").autoIncrement()
    val productTypeId = long("product_type_id")
    val productName = text("product_name")
    val quantity = integer("quantity")
    val productPrice = integer("product_price")
    val productCost = integer("product_cost")
    override val primaryKey: PrimaryKey = PrimaryKey(productId)
}

data class Product(
    val productId:Long? = null,
    var productTypeId:Long? = null,
    var productName:String? = null,
    var quantity:Int? = null,
    var productPrice:Int? = null,
    var productCost:Int? = null
): Principal {
    fun checkAllFields(): String {
        if(productTypeId == null) return "Product Type Id is empty"
        if(productName == null) return "Product Name Id is empty"
        if(quantity == null) return "Product Quantity Id is empty"
        if(productPrice == null) return "Product Price Id is empty"
        if(productCost == null) return "Product Cost Id is empty"
        return ""
    }
}