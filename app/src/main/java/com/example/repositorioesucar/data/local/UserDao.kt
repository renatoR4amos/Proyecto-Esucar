package com.example.repositorioesucar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarUsuario(user: UserEntity): Long

    @Query("SELECT * FROM usuarios WHERE LOWER(TRIM(correo)) = LOWER(TRIM(:correo)) AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): UserEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): UserEntity?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodosLosUsuarios(): List<UserEntity>
}
