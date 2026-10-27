package com.example.repositorioesucar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsultaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarConsulta(consulta: ConsultaEntity): Long

    @Query("SELECT * FROM consultas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<ConsultaEntity>>

    @Query("SELECT * FROM consultas WHERE LOWER(TRIM(correoAlumno)) = LOWER(TRIM(:correo)) ORDER BY id DESC")
    fun obtenerPorAlumno(correo: String): Flow<List<ConsultaEntity>>
}
