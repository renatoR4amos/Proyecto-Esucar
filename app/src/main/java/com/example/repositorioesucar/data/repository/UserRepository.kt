package com.example.repositorioesucar.data.repository

import com.example.repositorioesucar.data.local.UserDao
import com.example.repositorioesucar.data.local.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun login(correo: String, clave: String): UserEntity? = userDao.login(correo, clave)
    suspend fun registrar(user: UserEntity): Long = userDao.registrarUsuario(user)
    suspend fun obtenerPorId(id: Int): UserEntity? = userDao.obtenerPorId(id)
}