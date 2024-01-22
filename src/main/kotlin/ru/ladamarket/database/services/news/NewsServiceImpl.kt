package ru.ladamarket.database.services.news

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.news.NewsServiceImpl.NewsTable.authorId
import ru.ladamarket.database.services.news.NewsServiceImpl.NewsTable.content
import ru.ladamarket.database.services.news.NewsServiceImpl.NewsTable.timestamp
import ru.ladamarket.database.services.news.NewsServiceImpl.NewsTable.title
import ru.ladamarket.database.services.user.UserServiceImpl
import ru.ladamarket.modelRequest.news.NewsRequest
import ru.ladamarket.models.news.News
import java.time.LocalDate

class NewsServiceImpl(database: Database) : NewsService {

    object NewsTable : IntIdTable("news") {
        val title = varchar("title", 250)
        val content = text("content")
        val authorId = reference("author_id", UserServiceImpl.UserTable.id)
        val timestamp = date("timestamp")
    }

    init {
        transaction(database) {
            SchemaUtils.create(NewsTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    override suspend fun read(id: Int): News? {
        return dbQuery {
            NewsTable
                .select { NewsTable.id eq id }
                .singleOrNull()
                ?.let { ResultRowToNews(it) }
        }
    }

    override suspend fun readAll(): List<News> {
        return dbQuery {
            NewsTable
                .selectAll()
                .map { ResultRowToNews(it) }
        }
    }

    override suspend fun create(news: NewsRequest, id: Int) {
        dbQuery {
            NewsTable.insert {
                it[title] = news.title
                it[content] = news.content
                it[authorId] = id
                it[timestamp] = LocalDate.now()
            }
        }
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            NewsTable.deleteWhere {
                NewsTable.id eq id
            }
        }
    }

    private fun ResultRowToNews(row: ResultRow) = News(
        id = row[NewsTable.id].value,
        date = row[timestamp],
        authorId = row[authorId].value,
        title = row[title],
        content = row[content]
    )
}