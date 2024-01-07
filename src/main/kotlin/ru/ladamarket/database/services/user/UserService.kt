package ru.ladamarket.database.services.user

import ru.ladamarket.modelRequest.auth.signUp.RegisterRequest
import ru.ladamarket.modelRequest.user.UserInfoUpdate
import ru.ladamarket.models.user.User
import ru.ladamarket.security.hash.SaltedHash

interface UserService {
    suspend fun create(request: RegisterRequest, saltedHash: SaltedHash)
    suspend fun read(id: Int): User?
    suspend fun readAll(): List<User>
    suspend fun readByEmail(email: String): User?
    suspend fun readIdByEmail(email: String): Int?
    suspend fun readByPhone(phone: String): User?
    suspend fun isEmailExist(email: String):Boolean
    suspend fun isPhoneExist(phone: String):Boolean
    suspend fun isAdmin(id: Int): Boolean
    suspend fun isManager(id: Int): Boolean
    suspend fun update(id: Int, request: UserInfoUpdate, saltedHash: SaltedHash)
}