package com.example.base

import com.example.city.City_Table
import com.example.orderManagement.Product_Table
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

object DatabaseFactory {

    lateinit var db: Database

    fun init() {
//        if(DatabaseFactory::db.isInitialized) {
//            return db
//        } else {
//            db = Database.connect(hikari())
//            transaction {
//                SchemaUtils.create(City_Table)
//                SchemaUtils.create(Product_Table)
//            }
//            return db
//        }
        Database.connect(hikari())
        transaction {

            SchemaUtils.create(City_Table)
            SchemaUtils.create(Product_Table)
        }
    }

    fun hikari(): HikariDataSource {
        val config = HikariConfig()
        //getting the env variables which we have created.
        config.driverClassName = System.getenv("JDBC_DRIVER") // 1
        config.jdbcUrl = System.getenv("DATABASE_URL") // 2
        //config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        val user = System.getenv("DB_USER") // 3
        if (user != null) {
            config.username = user
        }
        val password = System.getenv("DB_PASSWORD") // 4
        if (password != null) {
            config.password = password
        }
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(lambda: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                lambda()
            }
        }

    fun getConnection(): Connection {
        return DriverManager.getConnection(System.getenv("DATABASE_URL"))
    }

    fun prepareStatement(conn: Connection, query: String): PreparedStatement {
        return conn.prepareStatement(query)
    }
}