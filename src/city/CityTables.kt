package com.example.city

import io.ktor.auth.*
import org.jetbrains.exposed.sql.Table

object City_Table : Table("city_table") {
    val id = long("id")
    val cityName = text("city_name")
    val area = integer("area")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

data class City(
    val id:Long,
    val cityName:String,
    val area:Int
): Principal