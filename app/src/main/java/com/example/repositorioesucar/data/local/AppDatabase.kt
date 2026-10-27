package com.example.repositorioesucar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, ProgresoEntity::class, ConsultaEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun progresoDao(): ProgresoDao
    abstract fun consultaDao(): ConsultaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "esucar_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                // Usuario administrador por defecto
                                INSTANCE?.userDao()?.registrarUsuario(
                                    UserEntity(
                                        nombre = "Administrador Sistema",
                                        correo = "admin@esucar.cl",
                                        clave = "admin123",
                                        rol = "DOCENTE"
                                    )
                                )
                                // Consulta inicial de ejemplo
                                INSTANCE?.consultaDao()?.insertarConsulta(
                                    ConsultaEntity(
                                        alumnoNombre = "Sargento Juan Pérez",
                                        correoAlumno = "alumno@esucar.cl",
                                        asunto = "Unidad 2: Procedimientos Policiales",
                                        mensaje = "Profesor, ¿el acta de incautación se debe registrar en el sistema digital en el turno de noche?",
                                        fecha = "Hoy"
                                    )
                                )
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
