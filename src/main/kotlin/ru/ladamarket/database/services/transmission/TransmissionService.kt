package ru.ladamarket.database.services.transmission

import ru.ladamarket.models.carModels.Transmission

interface TransmissionService {
    suspend fun read(id: Int): Transmission?
    suspend fun readAll(): List<Transmission>
}