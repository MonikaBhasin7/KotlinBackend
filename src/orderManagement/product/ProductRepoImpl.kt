package com.example.orderManagement.product

import com.example.base.BaseResponse
import com.example.base.DatabaseFactory
import com.example.base.Response
import com.example.orderManagement.Product
import com.example.orderManagement.Product_Table
import io.ktor.http.*

class ProductRepoImpl(): ProductRepo {

    override fun addProduct(productPayload: Product): Response {
        DatabaseFactory.getConnection().apply {
            val preparedStatement = prepareStatement("Insert into product_table (product_type_id,product_name,quantity, product_price, product_cost) VALUES (?,?,?,?,?)").apply {
                setLong(1, productPayload.productTypeId!!)
                setString(2, productPayload.productName!!)
                setInt(3, productPayload.quantity!!)
                setInt(4, productPayload.productPrice!!)
                setInt(5, productPayload.productCost!!)
            }
            preparedStatement.executeUpdate()
            close()
            return Response.SuccessWithData(BaseResponse("Done","Data Inserted Successfully"), HttpStatusCode.OK)
        }
    }

}