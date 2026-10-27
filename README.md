# Repositorio Móvil ESUCAR - Proyecto DAM

Aplicación móvil desarrollada en **Kotlin** con **Jetpack Compose** y **Room (SQLite)** para la Escuela de Suboficiales de Carabineros de Chile (ESUCAR), orientada a la organización de recursos pedagógicos, seguimiento del progreso académico y comunicación directa entre alumnos y docentes.

---

## Información del Proyecto

* **Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles
* **Evaluación:** Evaluación Parcial 2 (Encargo - Ponderación 8%)
* **Institución:** Duoc UC
* **Integrantes:**
  * Renato Ramos

---

## Funcionalidades Implementadas

### 1. Autenticación y Gestión de Usuarios (Room SQLite Offline)
* Registro e inicio de sesión funcional con persistencia local en base de datos SQLite con Room (`esucar_database`).
* Control estricto de roles: separación de acceso para **Estudiantes** y **Docentes**.
* Usuario maestro precargado para pruebas de evaluación:
  * **Correo:** `admin@esucar.cl`
  * **Contraseña:** `admin123`
  * **Rol:** Docente (acceso a panel administrativo y bandeja de consultas).
* Normalización insensible a mayúsculas/minúsculas y validaciones de formato de correo y longitud de contraseña.

### 2. Portal del Estudiante
* **Navegación por Barra Inferior Persistente:** Conmutación directa y fluida entre las 3 pestañas principales (*Materias*, *Progreso* y *Consultas*) sin perder el contexto ni requerir el botón de volver.
* **Catálogo Académico:** Estructuración de las 24 asignaturas del plan de estudios de ESUCAR organizadas en 4 semestres académicos.
* **Detalle de Asignaturas y Materiales:** Visualización de unidades temáticas con opción de descarga de guías y presentaciones para modo offline.
* **Tablero de Progreso:** Indicador visual (`ProgressBarEstudio`) que refleja el avance acumulado de lecturas estudiadas.
* **Consultas al Docente:** Formulario reactivo validado con registro persistente en Room. El estudiante puede ver el historial de sus consultas enviadas con el estado *"Recibido por Docente"*.

### 3. Portal del Docente y Panel de Administración
* Visualización de las cátedras asignadas al profesor.
* **Bandeja de Consultas de Alumnos:** Lista en tiempo real las consultas que los estudiantes registran en la base de datos Room, mostrando remitente, fecha, asunto y detalle.
* Botón de respuesta con notificación y mensaje reglamentario: *"Para una futura actualización"*.
* **Panel de Administración (`AdminScreen`):** Vista de supervisión que consulta la tabla `usuarios` de Room y despliega todos los registros en SQLite.

### 4. Recursos Nativos del Dispositivo (IE 2.4.1)
* **Recurso Nativo 1 (Galería de Imágenes):** Selector nativo (`ActivityResultContracts.GetContent`) para cambiar la fotografía de perfil del usuario.
* **Recurso Nativo 2 (Notificaciones del Sistema):** Sistema de notificaciones locales (`NotificationManager` y `NotificationCompat`) con canal configurado y permiso `POST_NOTIFICATIONS` para avisos al despachar consultas o descargar materiales de estudio.

### 5. Formularios y Animaciones Funcionales (IE 2.1.2 y IE 2.2.2)
* Validación visual por campo con activación de bordes de error (`isError`), íconos de advertencia y mensajes de ayuda (`supportingText`).
* Animaciones de interfaz con `AnimatedVisibility` para aparición fluida de mensajes y `animateContentSize()` en transiciones entre pestañas y tarjetas.

---

## Arquitectura de Software

El proyecto sigue el patrón de arquitectura **MVVM (Model - View - ViewModel)**:

```
app/src/main/java/com/example/repositorioesucar/
├── data/
│   ├── local/              # Persistencia Room (AppDatabase, DAOs y Entidades)
│   └── repository/         # Repositorios de datos
├── model/                  # Modelos de dominio y mock de las 24 asignaturas
├── navigation/             # Rutas tipo-seguras (Screen.kt) y NavHost (AppNavigation.kt)
├── ui/
│   ├── components/         # Componentes nativos (NotificationHelper, ImagenInteligente, Barra de progreso)
│   ├── screens/            # Pantallas composables para Alumnos, Docentes y Admin
│   └── theme/              # Tema y colores institucionales de Carabineros (Material 3)
├── viewmodel/              # ViewModels reactivos con StateFlow
└── MainActivity.kt         # Punto de entrada de la aplicación
```

---

## Pasos para Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/renatoR4amos/Proyecto-Esucar.git
   ```
2. **Abrir en Android Studio:**
   * Seleccionar `File` -> `Open`.
   * Seleccionar la carpeta raíz del proyecto clonado (`Proyecto-Esucar`).
3. **Sincronizar Gradle:**
   * Esperar a que Android Studio descargue las dependencias y compile el proyecto.
4. **Ejecutar la aplicación:**
   * Seleccionar un emulador configurado (API 24+) o conectar un dispositivo Android físico.
   * Presionar el botón **Run ('app')** (Shift + F10).
