package com.example.repositorioesucar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgresoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarProgreso(progreso: ProgresoEntity)

    @Query("SELECT * FROM progreso_estudio WHERE usuarioId = :usuarioId")
    fun obtenerProgresoPorUsuario(usuarioId: Int): Flow<List<ProgresoEntity>>

    @Query("SELECT COUNT(*) FROM progreso_estudio WHERE usuarioId = :usuarioId AND completado = 1")
    suspend fun contarCompletados(usuarioId: Int): Int
}