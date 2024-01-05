package ru.ladamarket.models.user

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object UserService : IntIdTable("user") {
    private val surname = UserService.varchar("surname", 40)
    private val name = UserService.varchar("name", 40)
    private val patronymic = UserService.varchar("patronymic", 40)
    private val phone = UserService.char("phone", 10)
    private val email = UserService.varchar("email", 60)
    private val hash = UserService.varchar("hash", 100)
    private val salt = UserService.varchar("salt", 100)
    private val avatar = UserService.varchar("avatar", 500)
    private val role = UserService.varchar("role", 15)

    fun insert(userDTO: UserDTO) {
        transaction {
            val userId = UserService.insertAndGetId {
                it[surname] = userDTO.surname
                it[name] = userDTO.name
                it[patronymic] = userDTO.patronymic
                it[phone] = userDTO.phone
                it[email] = userDTO.email
                it[hash] = userDTO.hash
                it[salt] = userDTO.salt
                it[avatar] = userDTO.avatar
                it[role] = userDTO.role
            }
        }
    }

    fun removeUser(id: Int) {
        transaction {
            UserService.deleteWhere { UserService.id.eq(id) }
        }
    }

    private fun resultRowToUserDto(row: ResultRow) = UserDTO(
        surname = row[surname],
        name = row[name],
        patronymic = row[patronymic],
        phone = row[phone],
        email = row[email],
        hash = row[hash],
        salt = row[salt],
        avatar = row[avatar],
        role = row[role],
    )
    suspend fun deleteAllUsers(){
        newSuspendedTransaction(Dispatchers.IO) {
            UserService.deleteWhere { email neq "admin@nintel.ru" }
        }
    }

    fun fetchUserById(id: Int): UserDTO? {
        return try {
            transaction {
                UserService.select { UserService.id.eq(id) }.singleOrNull()?.let { user ->
                    resultRowToUserDto(user)
                } ?: kotlin.run { null }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun fetchUserByPhone(phone: String): UserDTO? {
        return try {
            transaction {
                UserService.select { UserService.phone.eq(phone) }.singleOrNull()?.let { user ->
                    resultRowToUserDto(user)
                } ?: kotlin.run { null }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchUserByEmail(email: String): UserDTO? {
        return try {
            transaction {
                UserService.select { UserService.email.eq(email) }.singleOrNull()?.let { user ->
                    resultRowToUserDto(user)
                } ?: kotlin.run { null }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getUserIdByEmail(email: String): Int? {
        return try {
            transaction {
                UserService.select { UserService.email.eq(email) }.singleOrNull()?.get(UserService.id)?.value
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}