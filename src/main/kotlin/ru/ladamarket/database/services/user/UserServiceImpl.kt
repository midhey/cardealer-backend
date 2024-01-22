package ru.ladamarket.database.services.user

import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.avatar
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.email
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.hash
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.name
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.patronymic
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.phone
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.role
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.salt
import ru.ladamarket.database.services.user.UserServiceImpl.UserTable.surname
import ru.ladamarket.modelRequest.auth.signUp.RegisterRequest
import ru.ladamarket.modelRequest.user.UserInfoUpdate
import ru.ladamarket.models.user.User
import ru.ladamarket.models.user.UserDTO
import ru.ladamarket.security.hash.SaltedHash

class UserServiceImpl(database: Database) : UserService {
    object UserTable : IntIdTable("users") {
        val surname = UserTable.varchar("surname", 40)
        val name = UserTable.varchar("name", 40)
        val patronymic = UserTable.varchar("patronymic", 40).nullable()
        val phone = UserTable.char("phone", 10)
        val email = UserTable.varchar("email", 60)
        val hash = UserTable.varchar("hash", 100)
        val salt = UserTable.varchar("salt", 100)
        val avatar = UserTable.varchar("avatar", 500).nullable()
        val role = UserTable.varchar("role", 15).default("user")
    }

    init {
        transaction(database) {
            //Создание таблицы Users
            SchemaUtils.create(UserTable)

            //Добавление администратора
//            UserTable.insert {
//                it[UserTable.id] = 1
//                it[surname] = "Admin"
//                it[name] = "Admin"
//                it[phone] = "admin_phone"
//                it[email] = "admin@example.com"
//                it[hash] = "admin_hash"
//                it[salt] = "admin_salt"
//                it[role] = "admin"
//            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    override suspend fun create(request: RegisterRequest, saltedHash: SaltedHash) {
        dbQuery {
            UserTable.insert {
                it[surname] = request.surname
                it[name] = request.name
                it[patronymic] = request.patronymic
                it[phone] = request.phone
                it[email] = request.email
                it[hash] = saltedHash.hash
                it[salt] = saltedHash.salt
                it[avatar] = request.avatar
                it[role] = "user"
            }
        }
    }

    override suspend fun read(id: Int): User? {
        return dbQuery {
            UserTable.select { UserTable.id eq id }.singleOrNull()?.let { resultRowToUser(it) }
        }
    }

    override suspend fun readAll(): List<User> {
        return dbQuery {
            UserTable.selectAll().map { resultRowToUser(it) }
        }
    }

    override suspend fun readByEmail(email: String): User? {
        return dbQuery {
            UserTable.select { UserTable.email eq email }.singleOrNull()?.let { resultRowToUser(it) }
        }
    }

    override suspend fun readIdByEmail(email: String): Int? {
        return dbQuery {
            UserTable.select { UserTable.email eq email }
                .singleOrNull()
                ?.let { it[UserTable.id].value }
        }
    }

    override suspend fun readByPhone(phone: String): User? {
        return dbQuery {
            UserTable.select { UserTable.phone eq phone }.singleOrNull()?.let { resultRowToUser(it) }
        }
    }

    override suspend fun readFullName(id: Int): String? {
        return dbQuery {
            val user = UserTable
                .slice(UserTable.surname, UserTable.name)
                .select { UserTable.id eq id }
                .singleOrNull()

            user?.let {
                val fullName = "${it[UserTable.surname]} ${it[UserTable.name]}"
                fullName
            }
        }
    }

    override suspend fun isEmailExist(email: String): Boolean {
        return dbQuery {
            UserTable.select { UserTable.email eq email }.count() > 0
        }
    }


    override suspend fun isPhoneExist(phone: String): Boolean {
        return dbQuery {
            UserTable.select { UserTable.phone eq phone }.count() > 0
        }
    }

    override suspend fun isAdmin(id: Int): Boolean {
        return dbQuery {
            val user = UserTable.select { UserTable.id eq id }.singleOrNull()?.let { resultRowToUser(it) }
            user?.role!!.toLowerCasePreservingASCIIRules() == "admin"
        }
    }

    override suspend fun isManager(id: Int): Boolean {
        return dbQuery {
            val user = UserTable.select { UserTable.id eq id }.singleOrNull()?.let { resultRowToUser(it) }
            user?.role!!.toLowerCasePreservingASCIIRules() == "manager"
        }
    }

    override suspend fun update(id: Int, request: UserInfoUpdate, saltedHash: SaltedHash) {
        dbQuery {
            if (UserTable.select(where = { UserTable.id eq id }) != null) {
                UserTable.update(where = { UserTable.id eq id }) {
                    if (!request.surname.isNullOrBlank()) it[surname] = request.surname
                    if (!request.name.isNullOrBlank()) it[name] = request.name
                    if (!request.patronymic.isNullOrBlank()) it[patronymic] = request.patronymic
                    if (!request.email.isNullOrBlank()) it[email] = request.email
                    if (!request.phone.isNullOrBlank()) it[phone] = request.phone
                    if (!request.hash.isNullOrBlank()) {
                        it[hash] = saltedHash.hash
                        it[salt] = saltedHash.salt
                    }
                    if (!request.avatar.isNullOrBlank()) it[avatar] = request.avatar
                }
            }
        }
    }

    private fun resultRowToUserDTO(row: ResultRow) = UserDTO(
        surname = row[surname],
        name = row[name],
        patronymic = row[patronymic] ?: "",
        phone = row[phone],
        email = row[email],
        avatar = row[avatar] ?: ""
    )

    private fun resultRowToUser(row: ResultRow) = User(
        surname = row[surname],
        name = row[name],
        patronymic = row[patronymic] ?: "",
        phone = row[phone],
        email = row[email],
        hash = row[hash],
        salt = row[salt],
        avatar = row[avatar] ?: "",
        role = row[role]
    )
}