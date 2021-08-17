package com.example.orderManagement.product

import com.example.base.Response
import com.example.orderManagement.Product

interface ProductRepo {

    fun addProduct(productPayload: Product): Response
    fun getAllProducts(): Response
}