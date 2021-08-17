package com.example.orderManagement.product

import com.example.base.BaseResponse
import com.example.base.DatabaseFactory
import com.example.base.Response
import com.example.orderManagement.Product
import com.example.orderManagement.Product_Table
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

class ProductRepoImpl(): ProductRepo {

    override fun addProduct(productPayload: Product): Response {
        DatabaseFactory.getConnection().apply {
            prepareStatement("Insert into product_table (product_type_id,product_name,quantity, product_price, product_cost) VALUES (?,?,?,?,?)").apply {
                setLong(1, productPayload.productTypeId!!)
                setString(2, productPayload.productName!!)
                setInt(3, productPayload.quantity!!)
                setInt(4, productPayload.productPrice!!)
                setInt(5, productPayload.productCost!!)
            }.executeUpdate()
            close()
            return Response.SuccessWithData(BaseResponse("Done","Data Inserted Successfully"), HttpStatusCode.OK)
        }
    }

    override fun getAllProducts(): Response {
        try {
            DatabaseFactory.getConnection().apply {
                prepareStatement("Select * from product_table").executeQuery().apply {
                    var productList: MutableList<Product> = mutableListOf()
                    while (next()) {
                        productList.add(Product().apply {
                            productTypeId = getLong("product_type_id")
                            productName = getString("product_name")
                            quantity = getInt("quantity")
                            productPrice = getInt("product_price")
                            productCost = getInt("product_cost")
                        })
                    }
                    close()
                    return Response.SuccessWithData(BaseResponse(productList, "done"), HttpStatusCode.OK)
                }
            }
        } catch (e: SQLException) {
            return Response.ErrorWithMessage(e.localizedMessage, HttpStatusCode.NotFound)
        } catch (e: Exception) {
            return Response.ErrorWithMessage("Error while Fetching Data", HttpStatusCode.NotFound)
        }


    }

}