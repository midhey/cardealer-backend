package ru.ladamarket.database.services.carServices.body

import ru.ladamarket.models.carModels.Body

interface BodyService {
    suspend fun read(id:Int): Body?
    suspend fun readAll(): List<Body>
    suspend fun isBodyExist(id: Int): Boolean
}