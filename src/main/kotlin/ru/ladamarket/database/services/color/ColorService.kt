package ru.ladamarket.database.services.color

import ru.ladamarket.modelRequest.color.ColorRequest
import ru.ladamarket.models.color.Color

interface ColorService {
    suspend fun colorAdd(request: ColorRequest)
    suspend fun colorDelete(code: Short)
    suspend fun colorUpdate(request: ColorRequest)
    suspend fun read(code: Short): Color?
    suspend fun readAll(): List<Color>
    suspend fun isColorExist(code: Short):Boolean
}