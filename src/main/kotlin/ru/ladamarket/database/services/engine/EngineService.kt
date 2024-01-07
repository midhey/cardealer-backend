package ru.ladamarket.database.services.engine

import ru.ladamarket.models.engine.Engine

interface EngineService {
    suspend fun read(id: Short): Engine?
    suspend fun readAll(): List<Engine>
    suspend fun isEngineExist(id: Short): Boolean
}