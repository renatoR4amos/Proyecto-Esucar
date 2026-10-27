package com.example.repositorioesucar.model

object AsignaturaMock {
    val listaAsignaturas: List<Asignatura> = listOf(
        // SEMESTRE 1 (6 Asignaturas)
        Asignatura(1, "ADM-101", "Administración Policial I", 1, "Administración", generarUnidades(1)),
        Asignatura(2, "DER-101", "Derecho Procesal Penal I", 1, "Jurídica", generarUnidades(2)),
        Asignatura(3, "ETI-101", "Ética y Doctrina Institucional", 1, "Doctrinaria", generarUnidades(3)),
        Asignatura(4, "LID-101", "Liderazgo Operativo", 1, "Operativa", generarUnidades(4)),
        Asignatura(5, "MET-101", "Metodología de la Investigación Policial", 1, "Investigación", generarUnidades(5)),
        Asignatura(6, "TIC-101", "Tecnologías de la Información Policial", 1, "Tecnológica", generarUnidades(6)),

        // SEMESTRE 2 (6 Asignaturas)
        Asignatura(7, "ADM-201", "Administración de Recursos Logísticos", 2, "Administración", generarUnidades(7)),
        Asignatura(8, "DER-201", "Derechos Humanos Aplicados a la Función", 2, "Jurídica", generarUnidades(8)),
        Asignatura(9, "OPE-201", "Tácticas Policiales de Intervención", 2, "Operativa", generarUnidades(9)),
        Asignatura(10, "CRI-201", "Criminología y Control del Delito", 2, "Investigación", generarUnidades(10)),
        Asignatura(11, "SEG-201", "Seguridad Pública y Comunitaria", 2, "Operativa", generarUnidades(11)),
        Asignatura(12, "COM-201", "Comunicación Estratégica Institucional", 2, "Doctrinaria", generarUnidades(12)),

        // SEMESTRE 3 (6 Asignaturas)
        Asignatura(13, "PER-301", "Gestión de Personal Policial", 3, "Administración", generarUnidades(13)),
        Asignatura(14, "DER-301", "Derecho Administrativo Policial", 3, "Jurídica", generarUnidades(14)),
        Asignatura(15, "INT-301", "Inteligencia Policial Básica", 3, "Investigación", generarUnidades(15)),
        Asignatura(16, "PRO-301", "Procedimientos Policiales Complejos", 3, "Operativa", generarUnidades(16)),
        Asignatura(17, "TRA-301", "Investigación de Accidentes de Tránsito", 3, "Técnica", generarUnidades(17)),
        Asignatura(18, "ANA-301", "Análisis Criminal y Georreferenciación", 3, "Tecnológica", generarUnidades(18)),

        // SEMESTRE 4 (6 Asignaturas)
        Asignatura(19, "PLA-401", "Planificación Operativa y Mando", 4, "Operativa", generarUnidades(19)),
        Asignatura(20, "AUD-401", "Control de Gestión y Auditoría Policial", 4, "Administración", generarUnidades(20)),
        Asignatura(21, "CRI-401", "Criminalística de Campo", 4, "Investigación", generarUnidades(21)),
        Asignatura(22, "GES-401", "Gestión del Riesgo y Desastres", 4, "Operativa", generarUnidades(22)),
        Asignatura(23, "RES-401", "Resolución de Conflictos y Negociación", 4, "Doctrinaria", generarUnidades(23)),
        Asignatura(24, "PRO-401", "Seminario de Integración Profesional", 4, "Investigación", generarUnidades(24))
    )

    private fun generarUnidades(asigId: Int): List<UnidadTematica> {
        return listOf(
            UnidadTematica(
                id = asigId * 10 + 1,
                nombre = "Unidad 1: Fundamentos y Conceptos Clave",
                descripcion = "Introducción doctrinaria y bases metodológicas de la asignatura.",
                materiales = listOf(
                    MaterialEstudio(asigId * 100 + 1, asigId, "Guía de Lectura Oficial", "PDF"),
                    MaterialEstudio(asigId * 100 + 2, asigId, "Presentación Magistral U1", "GUIA")
                )
            ),
            UnidadTematica(
                id = asigId * 10 + 2,
                nombre = "Unidad 2: Procedimientos Prácticos y Normativa",
                descripcion = "Aplicación operativa y estudio de casos reales en servicio.",
                materiales = listOf(
                    MaterialEstudio(asigId * 100 + 3, asigId, "Manual de Procedimientos", "PDF"),
                    MaterialEstudio(asigId * 100 + 4, asigId, "Cápsula Audiovisual Demostrativa", "VIDEO")
                )
            )
        )
    }
}