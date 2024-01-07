package ru.ladamarket.database.services.color

import ru.ladamarket.modelRequest.color.colorRequest
import ru.ladamarket.models.color.Color

interface ColorService {
    suspend fun colorAdd(request: colorRequest)
    suspend fun colorDelete(code: Short)
    suspend fun colorUpdate(request: colorRequest)
    suspend fun read(code: Short): Color?
    suspend fun readAll(): List<Color>
    suspend fun isColorExist(code: Short):Boolean
}