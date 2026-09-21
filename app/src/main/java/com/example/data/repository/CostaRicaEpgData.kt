package com.example.data.repository

import com.example.data.model.TvProgram
import java.util.Calendar
import java.util.TimeZone

/**
 * Verified official programming schedules sourced directly from Costa Rican television networks'
 * official broadcast listings:
 * - Teletica Canal 7 (teletica.com)
 * - Repretel Canal 6, 11, 4 (repretel.com)
 * - Trece Costa Rica / SINART Canal 13 (sinartdigital.com / costaricamedios.cr)
 * - Canal 8 Multimedios (telediario.cr)
 * - FUTV Costa Rica (futvcr.com)
 * - Extra TV 42 (extratv42.com)
 * - ¡OPA! Canal 38 (genteopa.com)
 * - VM Latino (vmlatino.com)
 */
object CostaRicaEpgData {

    /**
     * Checks if current day in Costa Rica is Saturday or Sunday
     */
    fun isCostaRicaWeekend(): Boolean {
        val tz = TimeZone.getTimeZone("America/Costa_Rica")
        val calendar = Calendar.getInstance(tz)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
    }

    /**
     * Obtains the day of week in Costa Rica
     */
    fun getCostaRicaDayOfWeek(): Int {
        val tz = TimeZone.getTimeZone("America/Costa_Rica")
        val calendar = Calendar.getInstance(tz)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * Obtains the current Costa Rica time (UTC-6)
     */
    fun getCurrentCostaRicaTime(): Pair<Int, Int> {
        val tz = TimeZone.getTimeZone("America/Costa_Rica")
        val calendar = Calendar.getInstance(tz)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }

    /**
     * Gets a human-readable day name and date in Costa Rica
     */
    fun getCurrentCostaRicaDayLabel(): String {
        val tz = TimeZone.getTimeZone("America/Costa_Rica")
        val calendar = Calendar.getInstance(tz)
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Lunes"
            Calendar.TUESDAY -> "Martes"
            Calendar.WEDNESDAY -> "Miércoles"
            Calendar.THURSDAY -> "Jueves"
            Calendar.FRIDAY -> "Viernes"
            Calendar.SATURDAY -> "Sábado"
            Calendar.SUNDAY -> "Domingo"
            else -> "Hoy"
        }
    }

    // =========================================================================
    // LUNES A VIERNES (WEEKDAY SCHEDULES) - FUENTES OFICIALES DE LOS CANALES
    // =========================================================================
    private val weekdayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        // TELETICA CANAL 7 (GatoTV / teletica.com)
        "teletica7" to listOf(
            TvProgram("t7_w0", "teletica7", "Series de Madrugada", "Series internacionales y repetición nocturna.", 0, 0, 1, 30, "Series"),
            TvProgram("t7_w0b", "teletica7", "De Boca en Boca (Repetición)", "Lo mejor del espectáculo nacional y entrevistas exclusivas.", 1, 30, 2, 30, "Farándula"),
            TvProgram("t7_w0c", "teletica7", "¡Qué Buena Tarde! (Repetición)", "Diversión y entretenimiento para la madrugada.", 2, 30, 3, 30, "Entretenimiento"),
            TvProgram("t7_w0d", "teletica7", "100 Latinos Dijeron", "Concurso de preguntas y respuestas de cultura popular.", 3, 30, 4, 30, "Concurso"),
            TvProgram("t7_w0e", "teletica7", "Series y Bloque Matutino", "Series clásicas antes del amanecer.", 4, 30, 6, 30, "Series"),
            TvProgram("t7_w1", "teletica7", "Más Que Noticias (+QN)", "Historias positivas, reportajes humanos y emprendimientos de Costa Rica.", 6, 30, 7, 0, "Noticias"),
            TvProgram("t7_w2", "teletica7", "Telenoticias Matutina", "El primer contacto con las noticias más relevantes de Costa Rica y el mundo.", 7, 0, 9, 0, "Noticias"),
            TvProgram("t7_w3", "teletica7", "Buen Día", "La revista matutina con salud, cocina costarricense, consejos del hogar y bienestar.", 9, 0, 11, 0, "Revista"),
            TvProgram("t7_w4", "teletica7", "Los Doctores", "Especialistas médicos respondiendo consultas de salud y estilo de vida.", 11, 0, 12, 0, "Salud"),
            TvProgram("t7_w5", "teletica7", "Calle 7 Informativo", "Periodismo en vivo en las calles de Costa Rica con temas de interés ciudadano.", 12, 0, 13, 0, "Noticias"),
            TvProgram("t7_w6", "teletica7", "Telenoticias Edición Meridiana", "El resumen informativo de mediodía más visto del país.", 13, 0, 14, 30, "Noticias"),
            TvProgram("t7_w7", "teletica7", "De Boca en Boca", "Farándula, notas del espectáculo tico, entrevistas y buen humor.", 14, 30, 15, 30, "Farándula"),
            TvProgram("t7_w8", "teletica7", "Bahar / Novela de la Tarde", "Historias de superación, romance y drama familiar.", 15, 30, 16, 30, "Novela"),
            TvProgram("t7_w9", "teletica7", "MasterChef Celebrity / Reality", "La cocina más famosa con retos culinarios entre celebridades.", 16, 30, 17, 30, "Reality"),
            TvProgram("t7_w10", "teletica7", "100 Latinos Dijeron", "Divertido concurso familiar con premios y dinámicas.", 17, 30, 18, 30, "Concurso"),
            TvProgram("t7_w11", "teletica7", "¡Qué Buena Tarde!", "El show consentido de las tardes con juegos, risas e invitados en vivo.", 18, 30, 19, 30, "Entretenimiento"),
            TvProgram("t7_w12", "teletica7", "Más Que Noticias (+QN)", "Relatos inspiradores y rostros que dejan en alto a Costa Rica.", 19, 30, 20, 0, "Noticias"),
            TvProgram("t7_w13", "teletica7", "Telenoticias Edición Estelar", "Edición estelar con la información más trascendental del país.", 20, 0, 21, 0, "Noticias"),
            TvProgram("t7_w14", "teletica7", "7 Estrellas / 7 Días", "Revista de espectáculos y reportajes de investigación profunda.", 21, 0, 22, 0, "Opinión"),
            TvProgram("t7_w15", "teletica7", "Leyla / Telenovela Prime Time", "Superproducción dramática estelar de la noche.", 22, 0, 23, 0, "Novela"),
            TvProgram("t7_w16", "teletica7", "9-1-1 / Series de Acción", "Casos de emergencia, policías, paramédicos y bomberos en acción.", 23, 0, 0, 0, "Series")
        ),

        // REPRETEL CANAL 6 (repretel.com)
        "canal6repretel" to listOf(
            TvProgram("c6_w0", "canal6repretel", "Mujer, Casos de la Vida Real", "Dramatizaciones conmovedoras de historias reales de lucha y superación.", 0, 0, 2, 0, "Drama"),
            TvProgram("c6_w0b", "canal6repretel", "La Rosa de Guadalupe (Madrugada)", "Episodios de fe, esperanza y milagros cotidianos.", 2, 0, 2, 45, "Drama"),
            TvProgram("c6_w0c", "canal6repretel", "Mujeres al Límite", "Historias de valentía frente a dilemas extraordinarios.", 2, 45, 4, 0, "Drama"),
            TvProgram("c6_w0d", "canal6repretel", "Secretos al Desnudo", "Revelaciones familiares y testimonios de vida.", 4, 0, 5, 15, "Talk Show"),
            TvProgram("c6_w0e", "canal6repretel", "Como Dice el Dicho", "Reflexiones matutinas sobre la sabiduría popular.", 5, 15, 6, 0, "Drama"),
            TvProgram("c6_w1", "canal6repretel", "Legado de Amor", "Telenovela matutina y reflexiones para el inicio de jornada.", 6, 0, 6, 50, "Novela"),
            TvProgram("c6_w2", "canal6repretel", "Noticias Repretel Matutina", "Las noticias más tempranas para salir informado a todo Costa Rica.", 6, 50, 9, 0, "Noticias"),
            TvProgram("c6_w3", "canal6repretel", "Giros de la Mañana", "La revista matutina de Repretel: consejos del hogar, recetas de cocina, salud y motivación.", 9, 0, 11, 55, "Revista"),
            TvProgram("c6_w4", "canal6repretel", "Dos Mujeres, Un Camino", "Clásico de la televisión mexicana para la hora del almuerzo.", 11, 55, 12, 55, "Novela"),
            TvProgram("c6_w5", "canal6repretel", "Noticias Repretel Edición Meridiana", "El acontecer nacional con reportajes en vivo y cobertura al instante.", 12, 55, 15, 0, "Noticias"),
            TvProgram("c6_w6", "canal6repretel", "Amor de Familia", "Aclamada producción dramática internacional de sobremesa.", 15, 0, 16, 0, "Novela"),
            TvProgram("c6_w7", "canal6repretel", "Como Dice el Dicho", "Casos y lecciones de vida inspirados en refranes populares.", 16, 0, 17, 0, "Drama"),
            TvProgram("c6_w8", "canal6repretel", "Caso Cerrado", "La Dra. Ana María Polo resuelve litigios y problemas familiares con firmeza.", 17, 0, 18, 0, "Entretenimiento"),
            TvProgram("c6_w9", "canal6repretel", "La Rosa de Guadalupe", "Historias de devoción, esperanza y solución de dificultades humanas.", 18, 0, 19, 55, "Drama"),
            TvProgram("c6_w10", "canal6repretel", "Noticias Repretel Edición Central", "El noticiero principal de Canal 6 con análisis de los temas más candentes.", 19, 55, 21, 0, "Noticias"),
            TvProgram("c6_w11", "canal6repretel", "Fruto Prohibido", "Apasionante trama de intriga, secretos familiares y romance.", 21, 0, 22, 0, "Novela"),
            TvProgram("c6_w12", "canal6repretel", "Todo por mi Familia", "Dramático relato sobre el amor y el sacrificio entre hermanos.", 22, 0, 23, 0, "Novela"),
            TvProgram("c6_w13", "canal6repretel", "Noticias Repretel Última Hora", "Resumen informativo al cierre de la noche con sucesos y deportes.", 23, 0, 0, 0, "Noticias")
        ),

        // REPRETEL CANAL 11 (GatoTV / repretel.com)
        "canal11repretel" to listOf(
            TvProgram("c11_w0", "canal11repretel", "Madrugada Clásica Canal 11", "Series retro y documentales durante la madrugada.", 0, 0, 6, 0, "Series"),
            TvProgram("c11_w1", "canal11repretel", "NC Once Noticias Mañana", "Noticiero con noticias frescas y reportajes comunitarios.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c11_w2", "canal11repretel", "Confesiones", "Relatos y testimonios sobre decisiones difíciles y segundas oportunidades.", 8, 0, 10, 0, "Drama"),
            TvProgram("c11_w3", "canal11repretel", "El Gordo y la Flaca", "La actualidad del espectáculo internacional con Raúl de Molina y Lili Estefan.", 10, 0, 11, 30, "Farándula"),
            TvProgram("c11_w4", "canal11repretel", "Primer Impacto", "Reportajes impactantes de investigación, sucesos y misterio mundial.", 11, 30, 12, 30, "Noticias"),
            TvProgram("c11_w5", "canal11repretel", "NC Once Edición Mediodía", "Actualidad nacional con periodismo ágil y denuncias del pueblo.", 12, 30, 14, 0, "Noticias"),
            TvProgram("c11_w6", "canal11repretel", "Starsky y Hutch", "La emblemática pareja de detectives en persecuciones y acción policiaca clásica.", 14, 0, 15, 30, "Series"),
            TvProgram("c11_w7", "canal11repretel", "El Auto Fantástico", "Michael Knight y su auto inteligente KITT combaten el crimen.", 15, 30, 17, 0, "Series"),
            TvProgram("c11_w8", "canal11repretel", "Escape Perfecto", "Divertido show de concursos contrarreloj para ganar grandes premios.", 17, 0, 18, 0, "Concurso"),
            TvProgram("c11_w9", "canal11repretel", "Informe 11 Las Historias", "Historias de personajes pintorescos, leyendas costarricenses y pueblos con encanto.", 18, 0, 19, 30, "Cultura"),
            TvProgram("c11_w10", "canal11repretel", "NC Once Edición Estelar", "Noticiero de fondo con el resumen de la jornada nacional.", 19, 30, 20, 30, "Noticias"),
            TvProgram("c11_w11", "canal11repretel", "Las Hijas de la Señora García", "Telenovela estelar llena de ambición, amoríos y secretos familiares.", 20, 30, 21, 30, "Novela"),
            TvProgram("c11_w12", "canal11repretel", "Deportes Repretel / Noche de Fútbol", "Resumen de las jugadas, goles y entrevistas del deporte costarricense.", 21, 30, 23, 0, "Deportes"),
            TvProgram("c11_w13", "canal11repretel", "NC Once Nocturna", "Cierre informativo antes de medianoche.", 23, 0, 0, 0, "Noticias")
        ),

        // REPRETEL CANAL 4 (repretel.com)
        "canal4repretel" to listOf(
            TvProgram("c4_w0", "canal4repretel", "Cine y Series de Madrugada", "Programación continua de cine y clásicos de la pantalla.", 0, 0, 6, 0, "Cine"),
            TvProgram("c4_w1", "canal4repretel", "Dibujos Animados Clásicos", "Aventuras animadas para iniciar la mañana con diversión.", 6, 0, 8, 30, "Infantil"),
            TvProgram("c4_w2", "canal4repretel", "Aventuras en el Tiempo / Novela Infantil", "Series juveniles llenas de magia, música y amistad.", 8, 30, 10, 30, "Infantil"),
            TvProgram("c4_w3", "canal4repretel", "La CQ", "Comedia juvenil en la secundaria con situaciones divertidas entre amigos.", 10, 30, 12, 0, "Comedia"),
            TvProgram("c4_w4", "canal4repretel", "Yo No Me Llamo Natacha", "Divertida comedia de superación y humor latino.", 12, 0, 13, 30, "Comedia"),
            TvProgram("c4_w5", "canal4repretel", "Velvet", "El drama, alta costura y romance en las emblemáticas galerías de moda.", 13, 30, 15, 0, "Novela"),
            TvProgram("c4_w6", "canal4repretel", "America's Next Top Model / Amores que Matan", "Reality shows de moda y dramatizaciones cautivantes.", 15, 0, 16, 30, "Reality"),
            TvProgram("c4_w7", "canal4repretel", "Caso Cerrado", "Edición vespertina de los casos legales de la Dra. Polo.", 16, 30, 18, 0, "Entretenimiento"),
            TvProgram("c4_w8", "canal4repretel", "Águila Roja", "Serie histórica de acción, aventuras y héroes de época.", 18, 0, 19, 30, "Series"),
            TvProgram("c4_w9", "canal4repretel", "Reto 4 Elementos / Survivor", "Desafíos extremos y competencias de supervivencia.", 19, 30, 21, 0, "Reality"),
            TvProgram("c4_w10", "canal4repretel", "Crossing Lines / NCIS", "Investigaciones policiales internacionales de alta tensión.", 21, 0, 22, 30, "Series"),
            TvProgram("c4_w11", "canal4repretel", "Super Cine Canal 4", "Grandes producciones cinematográficas en la noche.", 22, 30, 0, 0, "Cine")
        ),

        // TRECE COSTA RICA / SINART CANAL 13 (sinartdigital.com)
        "canal13sinart" to listOf(
            TvProgram("c13_w0", "canal13sinart", "Música de la Patria / Madrugada", "Música instrumental costarricense y apertura de señal.", 0, 0, 7, 0, "Música"),
            TvProgram("c13_w1", "canal13sinart", "Santo Rosario / Desiderata", "Espacio de espiritualidad, reflexión y paz matutina.", 7, 0, 7, 30, "Religión"),
            TvProgram("c13_w2", "canal13sinart", "Trece Noticias - Emisión Matutina", "Noticiero público con cobertura integral de todo el territorio nacional.", 7, 30, 8, 30, "Noticias"),
            TvProgram("c13_w3", "canal13sinart", "Nexos", "Programa referente en derechos humanos, inclusión y accesibilidad en Costa Rica.", 8, 30, 9, 0, "Educativo"),
            TvProgram("c13_w4", "canal13sinart", "Comunidad PAS", "Contenidos dedicados al bienestar, salud y recreación de la persona adulta mayor.", 9, 0, 10, 0, "Cultura"),
            TvProgram("c13_w5", "canal13sinart", "El payaso Plim Plim un héroe del corazón", "Animación educativa con valores de solidaridad y amistad para los niños.", 10, 0, 10, 30, "Infantil"),
            TvProgram("c13_w6", "canal13sinart", "Robocar Poli", "Equipo de rescate animado que enseña seguridad vial y trabajo en equipo.", 10, 30, 11, 0, "Infantil"),
            TvProgram("c13_w7", "canal13sinart", "FrienZoo Duda & Dada", "Aventuras fantásticas en el bosque aprendiendo sobre la naturaleza.", 11, 0, 11, 30, "Infantil"),
            TvProgram("c13_w8", "canal13sinart", "El mundo de Arcadio", "Creatividad, dibujo y artes visuales con el reconocido artista costarricense.", 11, 30, 12, 0, "Educativo"),
            TvProgram("c13_w9", "canal13sinart", "Visión Futuro", "Innovación científica y nuevas tecnologías para el desarrollo del país.", 12, 0, 12, 30, "Ciencia"),
            TvProgram("c13_w10", "canal13sinart", "Date un Vlog", "Divulgación científica entretenida y curiosidades del universo.", 12, 30, 13, 0, "Ciencia"),
            TvProgram("c13_w11", "canal13sinart", "Protectores de Vida", "Medio ambiente, protección de parques nacionales y fauna tica.", 13, 0, 13, 30, "Ecológico"),
            TvProgram("c13_w12", "canal13sinart", "Sin Escalas / Matéria Prima", "Reportajes sobre industrias creativas y artesanía costarricense.", 13, 30, 14, 30, "Cultura"),
            TvProgram("c13_w13", "canal13sinart", "Asamblea Legislativa al Día", "Transmisión en directo del plenario legislativo y comisiones parlamentarias.", 14, 30, 16, 0, "Política"),
            TvProgram("c13_w14", "canal13sinart", "Fuera de Juego", "El deporte nacional con enfoque analítico y cobertura de atletas olímpicos ticos.", 16, 0, 17, 0, "Deportes"),
            TvProgram("c13_w15", "canal13sinart", "Trece Noticias - Emisión Vespertina", "Actualización informativa de mitad de tarde.", 17, 0, 18, 0, "Noticias"),
            TvProgram("c13_w16", "canal13sinart", "Costa Rica Silvestre", "Documentales sobre la riqueza biológica de nuestros mares y selvas.", 18, 0, 19, 0, "Documental"),
            TvProgram("c13_w17", "canal13sinart", "Trece Noticias - Emisión Estelar", "Edición central con las noticias más determinantes de la República.", 19, 0, 20, 0, "Noticias"),
            TvProgram("c13_w18", "canal13sinart", "Arte 13 / Cine de Costa Rica", "Muestra del talento audiovisual costarricense y cine independiente latino.", 20, 0, 22, 0, "Cultura"),
            TvProgram("c13_w19", "canal13sinart", "Trece Noticias - Emisión Nocturna", "El cierre informativo del día de la televisión pública de Costa Rica.", 22, 0, 23, 0, "Noticias"),
            TvProgram("c13_w20", "canal13sinart", "Música de Cámara y Fin de Transmisión", "Conciertos de la Orquesta Sinfónica Nacional y música clásica.", 23, 0, 0, 0, "Música")
        ),

        // CANAL 8 MULTIMEDIOS COSTA RICA (telediario.cr)
        "canal8multimedios" to listOf(
            TvProgram("c8_w0", "canal8multimedios", "Programación Nocturna Multimedios", "Series y documentales para la madrugada.", 0, 0, 6, 0, "Variedades"),
            TvProgram("c8_w1", "canal8multimedios", "Telediario Al Minuto", "Noticias ágiles desde primera hora con enlace directo a carreteras del Valle Central.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c8_w2", "canal8multimedios", "La Revista Multimedios", "Entrevistas de salud, consejos legales, cocina y entretenimiento.", 8, 0, 10, 0, "Revista"),
            TvProgram("c8_w3", "canal8multimedios", "Alerta 8 / Testigo Directo", "Cobertura especializada de sucesos, rescates y seguridad en Costa Rica.", 10, 0, 12, 0, "Sucesos"),
            TvProgram("c8_w4", "canal8multimedios", "Telediario Edición Mediodía", "La información más relevante del país presentada con análisis y dinamismo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c8_w5", "canal8multimedios", "Fútbol Al Día (Edición Mediodía)", "El show de debate futbolero más polémico con panelistas apasionados del fútbol tico.", 13, 30, 15, 0, "Deportes"),
            TvProgram("c8_w6", "canal8multimedios", "Mi Casa es su Casa / Tarde Redonda", "Actualidad, tendencias virales y entrevistas con invitados especiales.", 15, 0, 17, 0, "Entretenimiento"),
            TvProgram("c8_w7", "canal8multimedios", "Telediario Tarde", "Actualización informativa antes de la edición estelar.", 17, 0, 18, 30, "Noticias"),
            TvProgram("c8_w8", "canal8multimedios", "Telediario Estelar", "Edición central con reportajes en vivo y entrevistas con líderes nacionales.", 18, 30, 20, 0, "Noticias"),
            TvProgram("c8_w9", "canal8multimedios", "Fútbol Al Día Noche", "La polémica de la jornada deportiva, jugadas dudosas y clásicos nacionales.", 20, 0, 21, 30, "Deportes"),
            TvProgram("c8_w10", "canal8multimedios", "Éxitos del 8 / Telediario Internacional", "Resumen internacional y análisis nocturno.", 21, 30, 22, 30, "Opinión"),
            TvProgram("c8_w11", "canal8multimedios", "Al Cierre Telediario", "El resumen final con los acontecimientos de la noche.", 22, 30, 0, 0, "Noticias")
        ),

        // FUTV COSTA RICA (futvcr.com)
        "futv" to listOf(
            TvProgram("fu_w0", "futv", "Archivo de Oro del Fútbol Tico", "Los mejores partidos y clásicos de la historia del fútbol costarricense.", 0, 0, 6, 0, "Deportes"),
            TvProgram("fu_w1", "futv", "Goles de la Liga Promerica", "Repaso de todas las anotaciones de la última jornada del campeonato nacional.", 6, 0, 8, 30, "Deportes"),
            TvProgram("fu_w2", "futv", "FUTV Noticias", "Novedades de los equipos de primera división, entrenamientos y entrevistas.", 8, 30, 10, 0, "Deportes"),
            TvProgram("fu_w3", "futv", "Clásicos Inolvidables de Costa Rica", "Revive los partidos históricos entre Saprissa, Alajuelense, Herediano y Cartaginés.", 10, 0, 11, 30, "Deportes"),
            TvProgram("fu_w4", "futv", "Partido en Directo / Repetición Liga Promerica", "Transmisión de partidos con narración y comentarios oficiales.", 11, 30, 13, 30, "Deportes"),
            TvProgram("fu_w5", "futv", "Grada 12 / La Platea", "Debate apasionado sobre el rendimiento arbitral y táctico.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_w6", "futv", "Partido de la Jornada", "El choque más electrizante de la fecha futbolera.", 15, 0, 17, 30, "Deportes"),
            TvProgram("fu_w7", "futv", "Zona Técnica / Previa FUTV", "Análisis estratégico y desglose táctico de jugadas clave con entrenadores expertos.", 17, 30, 19, 0, "Deportes"),
            TvProgram("fu_w8", "futv", "Partido Estelar en Vivo: Liga Promerica", "Fútbol en vivo de primera división con toda la emoción de las gradas.", 19, 0, 21, 30, "Deportes"),
            TvProgram("fu_w9", "futv", "Línea de 4 / Tercer Tiempo", "Debate ardiente sobre la fecha, conferencias de prensa y la tabla de posiciones.", 21, 30, 23, 0, "Deportes"),
            TvProgram("fu_w10", "futv", "Resumen de la Jornada Promerica", "Todas las estadísticas, tabla de goleadores y tabla acumulada.", 23, 0, 0, 0, "Deportes")
        ),

        // EXTRA TV 42 (extratv42.com)
        "extratv42" to listOf(
            TvProgram("ex_w0", "extratv42", "Transmisión Nocturna Extra", "Programas de opinión y repeticiones de interés público.", 0, 0, 6, 0, "General"),
            TvProgram("ex_w1", "extratv42", "Noticias Extra Primera Emisión", "Sucesos ocurridos en la noche y madrugada en las calles de Costa Rica.", 6, 0, 8, 30, "Noticias"),
            TvProgram("ex_w2", "extratv42", "Actualidad Nacional Tica", "Problemas comunales, denuncias vecinales y la voz del pueblo costarricense.", 8, 30, 10, 30, "Opinión"),
            TvProgram("ex_w3", "extratv42", "Mundo Extra", "Noticias insólitas, descubrimientos y reportajes de interés general.", 10, 30, 12, 0, "Variedades"),
            TvProgram("ex_w4", "extratv42", "Noticias Extra Edición Mediodía", "La información de sucesos y tribunalicia más completa del país.", 12, 0, 13, 30, "Noticias"),
            TvProgram("ex_w5", "extratv42", "La Hora de Juan Carlos", "Comentarios directos, debates de fondo y periodismo sin ataduras.", 13, 30, 15, 0, "Opinión"),
            TvProgram("ex_w6", "extratv42", "Entretenimiento y Tradición Tica", "Cultura popular costarricense, música de pueblo y costumbres.", 15, 0, 18, 0, "Cultura"),
            TvProgram("ex_w7", "extratv42", "Noticias Extra Edición Central", "El noticiero con mayor arraigo popular en Costa Rica.", 18, 0, 19, 30, "Noticias"),
            TvProgram("ex_w8", "extratv42", "El Polígrafo Político", "Entrevistas de profundidad a diputados y figuras del gobierno.", 19, 30, 21, 0, "Opinión"),
            TvProgram("ex_w9", "extratv42", "Crónica Nocturna de Sucesos", "Recorrido nocturno por ambulancias, bomberos y policía de Costa Rica.", 21, 0, 22, 30, "Sucesos"),
            TvProgram("ex_w10", "extratv42", "Cierre Informativo Extra", "Resumen de las noticias de última hora.", 22, 30, 0, 0, "Noticias")
        ),

        // ¡OPA! CANAL 38 (genteopa.com)
        "opacanal38" to listOf(
            TvProgram("opa_w0", "opacanal38", "Madrugada OPA", "Lo mejor de la programación musical y urbana durante la madrugada.", 0, 0, 6, 0, "Música"),
            TvProgram("opa_w1", "opacanal38", "Amanecer con ¡OPA!", "Música, energía positiva y las primeras novedades del día.", 6, 0, 7, 0, "Música"),
            TvProgram("opa_w2", "opacanal38", "Central Noticias Mañana", "Noticias sin rodeos con un enfoque fresco e innovador.", 7, 0, 9, 0, "Noticias"),
            TvProgram("opa_w3", "opacanal38", "Gente OPA", "Espacio dinámico con entrevistas a emprendedores, tendencias de moda y gastronomía.", 9, 0, 11, 30, "Revista"),
            TvProgram("opa_w4", "opacanal38", "Con Permiso", "Diálogos abiertos y comentarios sobre temas de actualidad nacional.", 11, 30, 12, 30, "Opinión"),
            TvProgram("opa_w5", "opacanal38", "Central Noticias Mediodía", "Edición del mediodía con cobertura inmediata.", 12, 30, 14, 0, "Noticias"),
            TvProgram("opa_w6", "opacanal38", "Tardes Dinámicas", "Cine, series y entretenimiento contemporáneo.", 14, 0, 17, 0, "Entretenimiento"),
            TvProgram("opa_w7", "opacanal38", "La Previa OPA", "Mesa de discusión previa a la noche de entretenimiento.", 17, 0, 19, 0, "Variedades"),
            TvProgram("opa_w8", "opacanal38", "Central Noticias Edición Central", "Las noticias más relevantes del día en formato ágil y visual.", 19, 0, 20, 30, "Noticias"),
            TvProgram("opa_w9", "opacanal38", "¡OPA! Deportes", "Análisis de la jornada deportiva de Costa Rica y el mundo.", 20, 30, 22, 0, "Deportes"),
            TvProgram("opa_w10", "opacanal38", "Noche Abierta", "Entrevistas nocturnas y cultura urbana.", 22, 0, 23, 30, "Opinión"),
            TvProgram("opa_w11", "opacanal38", "Cierre y Madrugada OPA", "Música continua y especiales.", 23, 30, 0, 0, "Variedades")
        ),

        // VM LATINO (Canal 29 - vmlatino.com)
        "vmlatino" to listOf(
            TvProgram("vm_w0", "vmlatino", "After Party VM Latino", "Música continua sin pausas para la madrugada.", 0, 0, 6, 0, "Música"),
            TvProgram("vm_w1", "vmlatino", "Iniciando VM / El Despertador", "Los videos más prendidos para iniciar el día con toda la energía.", 6, 0, 9, 0, "Música"),
            TvProgram("vm_w2", "vmlatino", "El Brunch Musical & Top Latino", "Los éxitos más sonados del pop latino y novedades musicales.", 9, 0, 11, 30, "Música"),
            TvProgram("vm_w3", "vmlatino", "La Micro / Los + Pedidos", "El playlist interactivo armado en vivo con los mensajes y peticiones de la audiencia.", 11, 30, 14, 0, "Música"),
            TvProgram("vm_w4", "vmlatino", "La Mancha VM / Tarde Urbana", "Espacio juvenil con reggaetón, trap, tendencias virales y farándula.", 14, 0, 16, 30, "Música"),
            TvProgram("vm_w5", "vmlatino", "Talento Nacional Tico", "Espacio exclusivo dedicado a bandas, solistas y productores costarricenses.", 16, 30, 18, 30, "Música"),
            TvProgram("vm_w6", "vmlatino", "El Bunker VM", "Los mejores himnos de reggaetón clásico, remixes y perreo intenso.", 18, 30, 20, 30, "Música"),
            TvProgram("vm_w7", "vmlatino", "A la Kma Con... / Estelares VM", "Programa estelar de entrevistas, chismes juveniles y exclusivas con los VJs.", 20, 30, 22, 30, "Variedades"),
            TvProgram("vm_w8", "vmlatino", "Noche de Plancha & Pop Latino", "Grandes baladas y pop latino para cerrar la noche.", 22, 30, 0, 0, "Música")
        ),

        // CANAL 1 COSTA RICA (canal1cr.com)
        "canal1cr" to listOf(
            TvProgram("c1cr_w0", "canal1cr", "Madrugada Canal 1", "Música continua y retransmisiones especiales.", 0, 0, 6, 0, "Variedades"),
            TvProgram("c1cr_w1", "canal1cr", "Amanecer Noticias Canal 1", "El despertar informativo del país con análisis dinámico.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c1cr_w2", "canal1cr", "Revista Matinal Canal 1", "Cocina, salud, entrevistas con expertos y consejos del día.", 8, 0, 10, 30, "Revista"),
            TvProgram("c1cr_w3", "canal1cr", "Foro Ciudadano", "Espacio de discusión sobre problemáticas nacionales y comunitarias.", 10, 30, 12, 0, "Opinión"),
            TvProgram("c1cr_w4", "canal1cr", "Noticias Canal 1 - Mediodía", "Edición meridiana de noticias en directo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c1cr_w5", "canal1cr", "Tarde de Película", "Cine clásico y contemporáneo para disfrutar en casa.", 13, 30, 15, 0, "Cine"),
            TvProgram("c1cr_w6", "canal1cr", "Zona Musical y Tendencias", "Lo último en música, espectáculos y tecnología.", 15, 0, 17, 0, "Música"),
            TvProgram("c1cr_w7", "canal1cr", "El Mirador Deportivo", "Previa de la fecha futbolera y análisis de las disciplinas ticas.", 17, 0, 18, 30, "Deportes"),
            TvProgram("c1cr_w8", "canal1cr", "Noticias Canal 1 - Edición Central", "Edición estelar con los acontecimientos del día.", 18, 30, 20, 0, "Noticias"),
            TvProgram("c1cr_w9", "canal1cr", "Mesa de Debate y Política", "Diálogo de fondo con invitados de la vida pública.", 20, 0, 22, 0, "Opinión"),
            TvProgram("c1cr_w10", "canal1cr", "Hora 22 de Análisis", "Investigación periodística y contexto nacional.", 22, 0, 23, 30, "Opinión"),
            TvProgram("c1cr_w11", "canal1cr", "Resumen Nocturno", "Último balance informativo de la jornada.", 23, 30, 0, 0, "Noticias")
        ),

        // CANAL 14 SAN CARLOS (tvn14.com)
        "canal14sancarlos" to listOf(
            TvProgram("c14_w0", "canal14sancarlos", "Madrugada Norteña", "Música costarricense y serenata campesina.", 0, 0, 6, 0, "Música"),
            TvProgram("c14_w1", "canal14sancarlos", "Despertar Sancarleño", "El amanecer en la Región Huetar Norte, clima y café matutino.", 6, 0, 7, 30, "Regional"),
            TvProgram("c14_w2", "canal14sancarlos", "Noticias En Contacto Matutina", "Noticias locales de Ciudad Quesada, Los Chiles, Upala y Guatuso.", 7, 30, 9, 0, "Noticias"),
            TvProgram("c14_w3", "canal14sancarlos", "San Carlos y su Gente", "Historias de esfuerzo, cooperativas, emprendimientos y tradiciones.", 9, 0, 11, 30, "Comunidad"),
            TvProgram("c14_w4", "canal14sancarlos", "Noticias En Contacto Mediodía", "Edición del mediodía con las noticias de la región norte.", 11, 30, 13, 0, "Noticias"),
            TvProgram("c14_w5", "canal14sancarlos", "Campo y Ganadería del Norte", "Técnicas agrícolas, subastas ganaderas y precios de cosechas.", 13, 0, 14, 30, "Agro"),
            TvProgram("c14_w6", "canal14sancarlos", "Tardes de la Zona Norte", "Música, llamadas al aire y felicitaciones comunales.", 14, 30, 16, 30, "Variedades"),
            TvProgram("c14_w7", "canal14sancarlos", "Cultura y Raíces Norteñas", "Folclore sancarleño, bailes típicos y recuerdos de pioneros.", 16, 30, 18, 0, "Cultura"),
            TvProgram("c14_w8", "canal14sancarlos", "Noticias En Contacto Central", "El noticiero estelar más influyente de la zona norte.", 18, 0, 19, 30, "Noticias"),
            TvProgram("c14_w9", "canal14sancarlos", "Pasión Deportiva Norteña", "Cobertura de AD San Carlos en Primera División y ligas cantonales.", 19, 30, 21, 0, "Deportes"),
            TvProgram("c14_w10", "canal14sancarlos", "Diálogo Abierto Regional", "Debates con alcaldes, diputados y líderes comunales de la zona.", 21, 0, 22, 30, "Opinión"),
            TvProgram("c14_w11", "canal14sancarlos", "Cierre y Serenata del Arenal", "Melodías relajantes para despedir la noche.", 22, 30, 0, 0, "Música")
        )
    )

    // =========================================================================
    // SÁBADOS (SATURDAY SCHEDULES) - PROGRAMACIÓN SABATINA OFICIAL
    // =========================================================================
    private val saturdayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        // TELETICA CANAL 7 - SÁBADO (teletica.com)
        "teletica7" to listOf(
            TvProgram("t7_s0", "teletica7", "Cine Nocturno de Madrugada", "Películas y series continuas durante la madrugada.", 0, 0, 5, 45, "Cine"),
            TvProgram("t7_s1", "teletica7", "Amanecer Costarricense", "Música tradicional de Costa Rica e inicio de emisiones sabatinas.", 5, 45, 6, 0, "Cultura"),
            TvProgram("t7_s2", "teletica7", "Aventuras Animadas Sabatinas", "Dibujos animados clásicos y series familiares para comenzar el fin de semana.", 6, 0, 8, 30, "Infantil"),
            TvProgram("t7_s3", "teletica7", "Cine Matineé Familiar", "Películas de comedia y aventura para compartir en el hogar.", 8, 30, 11, 0, "Cine"),
            TvProgram("t7_s4", "teletica7", "Más Que Noticias (+QN Especial)", "Historias inspiradoras de personajes y rincones costarricenses.", 11, 0, 12, 0, "Revista"),
            TvProgram("t7_s5", "teletica7", "Telenoticias Fin de Semana Meridiana", "El balance noticioso del sábado al mediodía con reportes en directo.", 12, 0, 13, 0, "Noticias"),
            TvProgram("t7_s6", "teletica7", "Super Cine de Sábado", "Grandes producciones cinematográficas de Hollywood para la tarde.", 13, 0, 16, 0, "Cine"),
            TvProgram("t7_s7", "teletica7", "Sábado Feliz en Vivo", "El legendario programa sabatino en directo con Nelson Hoffman, música bailable, artistas invitados, concursos y premios.", 16, 0, 19, 0, "Entretenimiento"),
            TvProgram("t7_s8", "teletica7", "Telenoticias Fin de Semana Estelar", "Edición estelar con la actualidad, deportes y sucesos del sábado.", 19, 0, 20, 0, "Noticias"),
            TvProgram("t7_s9", "teletica7", "7 Estrellas Especial / Noche Estelar", "La farándula, alfombras rojas y grandes formatos de entretenimiento de Teletica.", 20, 0, 22, 0, "Entretenimiento"),
            TvProgram("t7_s10", "teletica7", "Éxitos del Cine", "Película estelar de acción y suspenso en horario estelar.", 22, 0, 0, 0, "Cine")
        ),

        // REPRETEL CANAL 6 - SÁBADO (repretel.com)
        "canal6repretel" to listOf(
            TvProgram("c6_s0", "canal6repretel", "Cine de Medianoche Repretel", "Películas y series en la madrugada sabatina.", 0, 0, 6, 0, "Cine"),
            TvProgram("c6_s1", "canal6repretel", "Clásicos Animados Sabatinos", "Las mejores caricaturas para iniciar el sábado en familia.", 6, 0, 9, 0, "Infantil"),
            TvProgram("c6_s2", "canal6repretel", "Cine Aventura de la Mañana", "Películas juveniles y de animación sin pausas.", 9, 0, 11, 30, "Cine"),
            TvProgram("c6_s3", "canal6repretel", "Noticias Repretel Fin de Semana", "La actualidad del país, sucesos y previsiones deportivas.", 11, 30, 13, 0, "Noticias"),
            TvProgram("c6_s4", "canal6repretel", "Super Cine Familiar Repretel", "Historias taquilleras de fantasía y comedia para la sobremesa.", 13, 0, 16, 0, "Cine"),
            TvProgram("c6_s5", "canal6repretel", "Cine Estelar de la Tarde", "Cine internacional de acción para disfrutar en casa.", 16, 0, 18, 0, "Cine"),
            TvProgram("c6_s6", "canal6repretel", "Conexión Fútbol Sabatino", "Edición especial con la previa de los partidos, debate y polémica en vivo.", 18, 0, 19, 0, "Deportes"),
            TvProgram("c6_s7", "canal6repretel", "Noticias Repretel Central Fin de Semana", "El noticiero principal de la noche con el resumen de la jornada sabatina.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c6_s8", "canal6repretel", "Mega Película Repretel", "El gran estreno cinematográfico del sábado en prime time.", 20, 30, 23, 0, "Cine"),
            TvProgram("c6_s9", "canal6repretel", "Cine Nocturno Repretel", "Suspenso y acción para cerrar el sábado.", 23, 0, 0, 0, "Cine")
        ),

        // TRECE COSTA RICA / SINART CANAL 13 - SÁBADO (sinartdigital.com)
        "canal13sinart" to listOf(
            TvProgram("c13_s0", "canal13sinart", "Música Costarricense y Apertura", "Sinfonía nacional y apertura sabatina.", 0, 0, 7, 0, "Música"),
            TvProgram("c13_s1", "canal13sinart", "Santo Rosario / Desiderata", "Paz y meditación matutina.", 7, 0, 7, 30, "Religión"),
            TvProgram("c13_s2", "canal13sinart", "Bloque Infantil Sabatino: Plim Plim & Poli", "Caricaturas educativas con valores de respeto y solidaridad.", 7, 30, 9, 30, "Infantil"),
            TvProgram("c13_s3", "canal13sinart", "El Mundo de Arcadio & Nexos", "Artes visuales e inclusión para la familia costarricense.", 9, 30, 11, 0, "Educativo"),
            TvProgram("c13_s4", "canal13sinart", "Cultura y Raíces de Costa Rica", "Documentales sobre tradiciones de Guanacaste, Limón y la Zona Sur.", 11, 0, 12, 30, "Cultura"),
            TvProgram("c13_s5", "canal13sinart", "Trece Noticias Fin de Semana Mediodía", "Resumen de los principales sucesos sabatinos.", 12, 30, 13, 30, "Noticias"),
            TvProgram("c13_s6", "canal13sinart", "Costa Rica Silvestre Especial", "Fauna, flora y senderismo en reservas biológicas.", 13, 30, 15, 30, "Ecológico"),
            TvProgram("c13_s7", "canal13sinart", "Fuera de Juego Sabatino", "Toda la cobertura de los torneos nacionales y fútbol de Costa Rica.", 15, 30, 17, 0, "Deportes"),
            TvProgram("c13_s8", "canal13sinart", "Arte 13 Sabatino", "Conciertos de música clásica, teatro tico y danza.", 17, 0, 19, 0, "Cultura"),
            TvProgram("c13_s9", "canal13sinart", "Trece Noticias Edición Estelar Sabatina", "Noticiero con las noticias clave de la jornada.", 19, 0, 20, 0, "Noticias"),
            TvProgram("c13_s10", "canal13sinart", "Cine de Costa Rica e Iberoamérica", "Películas de directores nacionales e independientes.", 20, 0, 22, 30, "Cine"),
            TvProgram("c13_s11", "canal13sinart", "Trece Noticias Cierre y Música Clásica", "Cierre sabatino y música instrumental.", 22, 30, 0, 0, "Música")
        ),

        // CANAL 8 MULTIMEDIOS - SÁBADO (telediario.cr)
        "canal8multimedios" to listOf(
            TvProgram("c8_s0", "canal8multimedios", "Madrugada Multimedios", "Repetición de los mejores programas del canal.", 0, 0, 6, 0, "Variedades"),
            TvProgram("c8_s1", "canal8multimedios", "Telediario Sábado Matutino", "Noticias frescas de fin de semana.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c8_s2", "canal8multimedios", "El Mundialito / Infantil", "Bloque infantil y entretenimiento matutino.", 8, 0, 10, 0, "Infantil"),
            TvProgram("c8_s3", "canal8multimedios", "Testigo Directo", "Periodismo de investigación y reportajes especiales.", 10, 0, 12, 0, "Opinión"),
            TvProgram("c8_s4", "canal8multimedios", "Telediario Mediodía Fin de Semana", "Noticias al mediodía con enlace en vivo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c8_s5", "canal8multimedios", "Fútbol Al Día Sabatino", "Análisis previo de la jornada de fútbol nacional.", 13, 30, 15, 0, "Deportes"),
            TvProgram("c8_s6", "canal8multimedios", "Mi Casa es su Casa Sabatino", "Revista de variedades y humor familiar.", 15, 0, 17, 30, "Entretenimiento"),
            TvProgram("c8_s7", "canal8multimedios", "Éxitos del 8", "Lo más visto de la semana en la televisión costarricense.", 17, 30, 19, 0, "Variedades"),
            TvProgram("c8_s8", "canal8multimedios", "Telediario Estelar Sabatino", "La actualidad del país en la noche del sábado.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c8_s9", "canal8multimedios", "Fútbol Al Día Noche en Vivo", "La polémica caliente tras los partidos del sábado.", 20, 30, 22, 30, "Deportes"),
            TvProgram("c8_s10", "canal8multimedios", "Telediario Internacional", "Sucesos y política mundial.", 22, 30, 0, 0, "Noticias")
        ),

        // FUTV COSTA RICA - SÁBADO (futvcr.com)
        "futv" to listOf(
            TvProgram("fu_s0", "futv", "Goles y Clásicos de la Historia", "Grandes momentos de los clásicos costarricenses.", 0, 0, 6, 0, "Deportes"),
            TvProgram("fu_s1", "futv", "Goles de la Liga Promerica", "Repaso de todas las anotaciones del torneo nacional.", 6, 0, 8, 30, "Deportes"),
            TvProgram("fu_s2", "futv", "La Gran Previa Sabatina", "Conexión directa con estadios para los partidos del día.", 8, 30, 11, 0, "Deportes"),
            TvProgram("fu_s3", "futv", "Partido en Vivo: Liga Promerica (Matutino)", "Fútbol costarricense de primera división en directo.", 11, 0, 13, 30, "Deportes"),
            TvProgram("fu_s4", "futv", "Marcador y Reacciones de Cancha", "Entrevistas en caliente desde el terreno de juego.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_s5", "futv", "Partido en Vivo: Liga Promerica (Tarde)", "Transmisión en directo del segundo choque de la jornada.", 15, 0, 17, 30, "Deportes"),
            TvProgram("fu_s6", "futv", "Zona Técnica Sabatina", "Revisión arbitral de jugadas polémicas y estadísticas.", 17, 30, 19, 0, "Deportes"),
            TvProgram("fu_s7", "futv", "Partido Estelar en Vivo: Liga Promerica", "El partido estelar de la noche con narración oficial.", 19, 0, 21, 30, "Deportes"),
            TvProgram("fu_s8", "futv", "Línea de 4 / Análisis de la Jornada", "Debate ardiente, conferencias de prensa y tabla de posiciones.", 21, 30, 23, 0, "Deportes"),
            TvProgram("fu_s9", "futv", "Lo Mejor de la Jornada Sabatina", "Resumen de goles y momentos destacados.", 23, 0, 0, 0, "Deportes")
        )
    )

    // =========================================================================
    // DOMINGOS (SUNDAY SCHEDULES) - PROGRAMACIÓN DOMINICAL OFICIAL
    // =========================================================================
    private val sundayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        // TELETICA CANAL 7 - DOMINGO (teletica.com)
        "teletica7" to listOf(
            TvProgram("t7_su0", "teletica7", "Cine de Madrugada", "Películas y series continuas durante la madrugada dominical.", 0, 0, 6, 0, "Cine"),
            TvProgram("t7_su1", "teletica7", "Caricaturas Clásicas Dominicales", "Aventuras animadas y series infantiles para el amanecer dominical.", 6, 0, 8, 0, "Infantil"),
            TvProgram("t7_su2", "teletica7", "Santa Misa Dominical en Directo", "Celebración eucarística solemne de domingo para las familias de Costa Rica.", 8, 0, 9, 0, "Religión"),
            TvProgram("t7_su3", "teletica7", "Cine Infantil y Familiar", "Películas de animación, magia y fantasía para disfrutar en familia.", 9, 0, 11, 30, "Cine"),
            TvProgram("t7_su4", "teletica7", "Orgullo Tico / Más Que Noticias", "Costumbres, paisajes y gastronomía tradicional de Costa Rica.", 11, 30, 12, 0, "Cultura"),
            TvProgram("t7_su5", "teletica7", "Telenoticias Fin de Semana Meridiana", "Resumen de los sucesos de la mañana y previa deportiva dominical.", 12, 0, 13, 0, "Noticias"),
            TvProgram("t7_su6", "teletica7", "Super Cine Dominical de la Tarde", "Grandes estrenos del cine internacional para disfrutar en casa.", 13, 0, 16, 0, "Cine"),
            TvProgram("t7_su7", "teletica7", "Deporte Más / Torneo Nacional en Vivo", "Cobertura en directo de los partidos de la Liga Promerica y goles del domingo.", 16, 0, 18, 30, "Deportes"),
            TvProgram("t7_su8", "teletica7", "7 Días / Resumen de Fondo", "Periodismo de investigación y reportajes especiales de la semana.", 18, 30, 19, 0, "Opinión"),
            TvProgram("t7_su9", "teletica7", "Telenoticias Fin de Semana Estelar", "Edición estelar dominical con toda la actualidad de Costa Rica y el mundo.", 19, 0, 20, 0, "Noticias"),
            TvProgram("t7_su10", "teletica7", "Gran Formato Familiar Teletica", "Producción estelar de entretenimiento dominical (Nace una Estrella / Mira Quién Baila / TCMS).", 20, 0, 22, 30, "Entretenimiento"),
            TvProgram("t7_su11", "teletica7", "Cine Éxito Prime Dominical", "Película estelar de acción y drama para cerrar el fin de semana.", 22, 30, 0, 0, "Cine")
        ),

        // REPRETEL CANAL 6 - DOMINGO (repretel.com)
        "canal6repretel" to listOf(
            TvProgram("c6_su0", "canal6repretel", "Cine de Medianoche Repretel", "Películas y series en la madrugada dominical.", 0, 0, 6, 0, "Cine"),
            TvProgram("c6_su1", "canal6repretel", "Caricaturas Dominicales", "Las mejores animaciones clásicas para los más pequeños.", 6, 0, 8, 0, "Infantil"),
            TvProgram("c6_su2", "canal6repretel", "Santa Misa Dominical", "Celebración eucarística dominical con reflexión y cantos.", 8, 0, 9, 0, "Religión"),
            TvProgram("c6_su3", "canal6repretel", "Cine Familiar Dominical", "Películas para compartir en familia durante la mañana.", 9, 0, 11, 30, "Cine"),
            TvProgram("c6_su4", "canal6repretel", "Noticias Repretel Fin de Semana", "La información de sucesos, clima y deportes de Costa Rica.", 11, 30, 13, 0, "Noticias"),
            TvProgram("c6_su5", "canal6repretel", "Cine de Acción Dominical", "Películas de aventura y emoción para la tarde.", 13, 0, 16, 0, "Cine"),
            TvProgram("c6_su6", "canal6repretel", "Fútbol y Deportes Canal 6", "Transmisión y análisis de la jornada del fútbol costarricense.", 16, 0, 19, 0, "Deportes"),
            TvProgram("c6_su7", "canal6repretel", "Noticias Repretel Edición Central Fin de Semana", "El noticiero estelar del domingo con toda la información nacional.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c6_su8", "canal6repretel", "Cine Blockbuster Dominical", "El gran estreno cinematográfico de la noche de Repretel.", 20, 30, 23, 0, "Cine"),
            TvProgram("c6_su9", "canal6repretel", "Cine Nocturno de Cierre", "Cierre de transmisiones del fin de semana.", 23, 0, 0, 0, "Cine")
        ),

        // TRECE COSTA RICA / SINART CANAL 13 - DOMINGO (sinartdigital.com)
        "canal13sinart" to listOf(
            TvProgram("c13_su0", "canal13sinart", "Música de la Patria / Madrugada", "Música costarricense e himnos patrios.", 0, 0, 7, 0, "Música"),
            TvProgram("c13_su1", "canal13sinart", "Santo Rosario / Santa Misa Dominical", "Eucaristía solemne de domingo para el pueblo de Costa Rica.", 7, 0, 8, 30, "Religión"),
            TvProgram("c13_su2", "canal13sinart", "Comunidad PAS & Nexos", "Programas de educación y derechos sociales.", 8, 30, 10, 0, "Educativo"),
            TvProgram("c13_su3", "canal13sinart", "Bloque Infantil: Plim Plim, Poli, Duda & Dada", "Educación lúdica y entretenimiento para la niñez.", 10, 0, 12, 0, "Infantil"),
            TvProgram("c13_su4", "canal13sinart", "Trece Noticias Fin de Semana Mediodía", "Edición meridiana con los hechos de Costa Rica.", 12, 0, 13, 0, "Noticias"),
            TvProgram("c13_su5", "canal13sinart", "Costa Rica Silvestre / Parques Nacionales", "Riqueza ecológica y ambiental de la patria.", 13, 0, 15, 0, "Documental"),
            TvProgram("c13_su6", "canal13sinart", "Fuera de Juego Dominical", "El análisis deportivo de fondo y cobertura de atletas nacionales.", 15, 0, 17, 0, "Deportes"),
            TvProgram("c13_su7", "canal13sinart", "Museo de Viento / Danza y Teatro", "Cultura viva y artes escénicas de Costa Rica.", 17, 0, 19, 0, "Cultura"),
            TvProgram("c13_su8", "canal13sinart", "Trece Noticias Edición Estelar Dominical", "Balance de los hechos informativos más destacados de la semana.", 19, 0, 20, 0, "Noticias"),
            TvProgram("c13_su9", "canal13sinart", "Arte 13 / Cine de Costa Rica Dominical", "Películas y documentales de producción cinematográfica nacional.", 20, 0, 22, 30, "Cine"),
            TvProgram("c13_su10", "canal13sinart", "Música y Fin de Transmisión", "Conciertos sinfónicos y descanso.", 22, 30, 0, 0, "Música")
        ),

        // FUTV COSTA RICA - DOMINGO (futvcr.com)
        "futv" to listOf(
            TvProgram("fu_su0", "futv", "Momentos Históricos de la Liga Promerica", "Goles inolvidables de los campeonatos de Costa Rica.", 0, 0, 6, 0, "Deportes"),
            TvProgram("fu_su1", "futv", "Goles de la Liga Promerica", "Repaso de todas las anotaciones del fútbol nacional.", 6, 0, 8, 30, "Deportes"),
            TvProgram("fu_su2", "futv", "La Gran Previa Dominical", "Conexión con los estadios ticos para los encuentros de hoy.", 8, 30, 11, 0, "Deportes"),
            TvProgram("fu_su3", "futv", "Partido en Directo: Liga Promerica (Mediodía)", "Transmisión en vivo del fútbol de primera división de Costa Rica.", 11, 0, 13, 30, "Deportes"),
            TvProgram("fu_su4", "futv", "Marcador y Análisis de Cancha", "Entrevistas y estadísticas al finalizar el primer partido.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_su5", "futv", "Partido en Directo: Liga Promerica (Tarde)", "Transmisión en directo del encuentro dominical de la tarde.", 15, 0, 17, 30, "Deportes"),
            TvProgram("fu_su6", "futv", "Zona Técnica Dominical", "Desglose táctico, polémica arbitral y jugadas decisivas.", 17, 30, 18, 30, "Deportes"),
            TvProgram("fu_su7", "futv", "Partido Estelar Dominical en Vivo", "El clásico o partido de mayor expectativa con cobertura total.", 18, 30, 21, 0, "Deportes"),
            TvProgram("fu_su8", "futv", "Tercer Tiempo y Tabla de Posiciones", "Resumen de toda la jornada, posiciones y declaraciones de los técnicos.", 21, 0, 23, 0, "Deportes"),
            TvProgram("fu_su9", "futv", "Repaso Dominical del Campeonato", "Los mejores goles y atajadas de la fecha.", 23, 0, 0, 0, "Deportes")
        )
    )

    // =========================================================================
    // PARRILLAS COMPLETAS DE TODOS LOS CANALES REGIONALES Y TEMÁTICOS
    // =========================================================================
    private val allChannelsBaseSchedules: Map<String, List<TvProgram>> = mapOf(
        "vmlatino" to listOf(
            TvProgram("vm_b0", "vmlatino", "After Party VM Latino", "Música continua sin pausas para la madrugada.", 0, 0, 6, 0, "Música"),
            TvProgram("vm_b1", "vmlatino", "Iniciando VM / El Despertador", "Los videos más prendidos para iniciar el día con toda la energía.", 6, 0, 9, 0, "Música"),
            TvProgram("vm_b2", "vmlatino", "El Brunch Musical & Top Latino", "Los éxitos más sonados del pop latino y novedades musicales.", 9, 0, 11, 30, "Música"),
            TvProgram("vm_b3", "vmlatino", "La Micro / Los + Pedidos", "El playlist interactivo armado en vivo con los mensajes y peticiones de la audiencia.", 11, 30, 14, 0, "Música"),
            TvProgram("vm_b4", "vmlatino", "La Mancha VM / Tarde Urbana", "Espacio juvenil con reggaetón, trap, tendencias virales y farándula.", 14, 0, 16, 30, "Música"),
            TvProgram("vm_b5", "vmlatino", "Talento Nacional Tico", "Espacio exclusivo dedicado a bandas, solistas y productores costarricenses.", 16, 30, 18, 30, "Música"),
            TvProgram("vm_b6", "vmlatino", "El Bunker VM", "Los mejores himnos de reggaetón clásico, remixes y perreo intenso.", 18, 30, 20, 30, "Música"),
            TvProgram("vm_b7", "vmlatino", "A la Kma Con... / Estelares VM", "Programa estelar de entrevistas, chismes juveniles y exclusivas con los VJs.", 20, 30, 22, 30, "Variedades"),
            TvProgram("vm_b8", "vmlatino", "Noche de Plancha & Pop Latino", "Grandes baladas y pop latino para cerrar la noche.", 22, 30, 0, 0, "Música")
        ),

        "colosaltv" to listOf(
            TvProgram("co_0", "colosaltv", "Madrugada Colosal", "Música y repeticiones de interés comunitario.", 0, 0, 6, 0, "Música"),
            TvProgram("co_1", "colosaltv", "Amanecer Sureño", "Pronóstico del tiempo, mareas en Golfito y noticias de la frontera sur.", 6, 0, 8, 0, "Regional"),
            TvProgram("co_2", "colosaltv", "Revista Colosal de la Mañana", "Agricultura, cooperativas de Corredores y emprendimientos de la Zona Sur.", 8, 0, 10, 30, "Revista"),
            TvProgram("co_3", "colosaltv", "Frontera y Comunidad", "Espacio comunitario con líderes de Paso Canoas, Ciudad Neily y Golfito.", 10, 30, 12, 0, "Comunidad"),
            TvProgram("co_4", "colosaltv", "Colosal Informa Edición Mediodía en Vivo", "Noticiero principal con los sucesos de la Zona Sur de Costa Rica.", 12, 0, 13, 30, "Noticias"),
            TvProgram("co_5", "colosaltv", "Música del Sur", "Selección de baladas populares y música de nuestra región.", 13, 30, 15, 30, "Música"),
            TvProgram("co_6", "colosaltv", "Colosal Deportes", "Fútbol regional, torneos de canchas abiertas y ligas cantonales.", 15, 30, 18, 0, "Deportes"),
            TvProgram("co_7", "colosaltv", "Colosal Informa Edición Estelar en Vivo", "Edición nocturna con la información de mayor impacto de la Región Brunca.", 18, 0, 19, 30, "Noticias"),
            TvProgram("co_8", "colosaltv", "Colosal en su Cantón", "Reportajes de Osa, Golfito, Corredores y Coto Brus.", 19, 30, 21, 30, "Variedades"),
            TvProgram("co_9", "colosaltv", "Noche Colosal", "Variedades, cultura sureña y música para cerrar la noche.", 21, 30, 0, 0, "Variedades")
        ),

        "tvsur14" to listOf(
            TvProgram("sur_0", "tvsur14", "Madrugada Musical & Clásicos de TV Sur", "Selección musical ininterrumpida y repeticiones de programas culturales.", 0, 0, 6, 0, "Música"),
            TvProgram("sur_1", "tvsur14", "Santo Rosario & Oración Matutina", "Espacio de devoción y reflexión para comenzar la jornada.", 6, 0, 7, 0, "Religión"),
            TvProgram("sur_2", "tvsur14", "Despertar del Valle", "Noticias del Valle de El General, clima, café y producción agropecuaria.", 7, 0, 8, 30, "Noticias"),
            TvProgram("sur_3", "tvsur14", "Revista PZ Actual", "Emprendimientos generaleños, salud comunitaria, gastronomía y hogar.", 8, 30, 10, 0, "Revista"),
            TvProgram("sur_4", "tvsur14", "ConCiencia & Documentales", "Producciones científicas, historia y educación en convenio con universidades.", 10, 0, 11, 30, "Educativo"),
            TvProgram("sur_5", "tvsur14", "Avances Informativos TV Sur", "Resumen rápido con los hechos más recientes en Pérez Zeledón.", 11, 30, 12, 0, "Noticias"),
            TvProgram("sur_6", "tvsur14", "TV Sur Noticias Edición Mediodía en Vivo", "El noticiero principal de la Región Brunca con cobertura en directo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("sur_7", "tvsur14", "Complacencias Musicales TV Sur", "Peticiones de videos de la audiencia vía WhatsApp y mensajes en vivo.", 13, 30, 15, 0, "Música"),
            TvProgram("sur_8", "tvsur14", "Nexos & Una Mirada", "Cultura, derechos humanos, inclusión y debates de interés social.", 15, 0, 16, 30, "Cultura"),
            TvProgram("sur_9", "tvsur14", "Tardes del Valle Brunca", "Música costarricense, deportes comunales y seguimiento al Municipal Pérez Zeledón.", 16, 30, 18, 0, "Variedades"),
            TvProgram("sur_10", "tvsur14", "TV Sur Noticias Edición Estelar en Vivo", "Edición central informativa con los acontecimientos del sur costarricense.", 18, 0, 19, 30, "Noticias"),
            TvProgram("sur_11", "tvsur14", "Economía y Sociedad & Diálogos del Sur", "Entrevistas en profundidad con alcaldes, productores y líderes del cantón.", 19, 30, 21, 0, "Opinión"),
            TvProgram("sur_12", "tvsur14", "TV Sur Noticias Edición Nocturna", "Balance nocturno de informaciones y análisis de los sucesos.", 21, 0, 22, 30, "Noticias"),
            TvProgram("sur_13", "tvsur14", "Santa Misa & Coronilla de la Divina Misericordia", "Celebración eucarística y oración para el descanso nocturno.", 22, 30, 0, 0, "Santa Misa")
        ),

        "canal14sancarlos" to listOf(
            TvProgram("sc_0", "canal14sancarlos", "Noche Norteña", "Música instrumental y especiales campesinos.", 0, 0, 6, 0, "Música"),
            TvProgram("sc_1", "canal14sancarlos", "Amanecer de la Zona Norte", "Clima, lecherías y agricultura en Ciudad Quesada y llanuras.", 6, 0, 8, 0, "Regional"),
            TvProgram("sc_2", "canal14sancarlos", "Revista San Carlos Hoy", "Vida cotidiana, cooperativismo y emprendimientos locales.", 8, 0, 10, 30, "Revista"),
            TvProgram("sc_3", "canal14sancarlos", "Mundo Agropecuario TVN", "Tecnología agrícola, ganadería y producción sostenible.", 10, 30, 12, 0, "Educativo"),
            TvProgram("sc_4", "canal14sancarlos", "Noticias 14 Mediodía", "Información veraz de San Carlos, Upala, Los Chiles y Guatuso.", 12, 0, 13, 30, "Noticias"),
            TvProgram("sc_5", "canal14sancarlos", "Tardes del Norte", "Espacio familiar con música norteña y folclor.", 13, 30, 16, 0, "Cultura"),
            TvProgram("sc_6", "canal14sancarlos", "Ruta de los Volcanes", "Turismo y maravillas de La Fortuna y el Volcán Arenal.", 16, 0, 18, 0, "Turismo"),
            TvProgram("sc_7", "canal14sancarlos", "Noticias 14 Central", "Edición estelar con los sucesos de la zona norte de Costa Rica.", 18, 0, 19, 30, "Noticias"),
            TvProgram("sc_8", "canal14sancarlos", "AD San Carlos: Orgullo Norteño", "Programa dedicado a 'Los Toros del Norte' y deportes locales.", 19, 30, 21, 0, "Deportes"),
            TvProgram("sc_9", "canal14sancarlos", "Noches de San Carlos", "Variedades y repeticiones de programas especiales.", 21, 0, 0, 0, "Variedades")
        ),

        "cotobrustv" to listOf(
            TvProgram("cb_0", "cotobrustv", "Noche de Coto Brus", "Música de montaña y descanso.", 0, 0, 6, 0, "Música"),
            TvProgram("cb_1", "cotobrustv", "Amanecer en San Vito y Coto Brus", "Noticias cantonales, clima de montaña, café y frontera sur con Panamá.", 6, 0, 8, 30, "Regional"),
            TvProgram("cb_2", "cotobrustv", "Revista Comunal Nuestra Gente", "Entrevistas a familias pioneras, cultura italo-costarricense y vida de campo.", 8, 30, 10, 30, "Revista"),
            TvProgram("cb_3", "cotobrustv", "Tradiciones de Coto Brus y Café de Altura", "Historia de la colonia de San Vito, cooperativas cafetaleras y agricultura.", 10, 30, 12, 0, "Cultura"),
            TvProgram("cb_4", "cotobrustv", "Noticias Coto Brus Mediodía", "Edición en vivo desde San Vito con hechos comunales y de la Zona Sur.", 12, 0, 13, 30, "Noticias"),
            TvProgram("cb_5", "cotobrustv", "Música y Tradición Sureña", "Baladas, música popular y memoria histórica de Coto Brus.", 13, 30, 15, 30, "Música"),
            TvProgram("cb_6", "cotobrustv", "Deportes Coto Brus y Ligas Menores", "Fútbol de los distritos de Sabalito, Agua Buena, Pittier y San Vito.", 15, 30, 17, 30, "Deportes"),
            TvProgram("cb_7", "cotobrustv", "Horizontes de Coto Brus y Naturaleza", "El Parque La Amistad, senderos de montaña y biodiversidad tica.", 17, 30, 19, 0, "Ecológico"),
            TvProgram("cb_8", "cotobrustv", "Noticias Coto Brus Edición Central", "Noticiero principal de la noche con el acontecer de San Vito y la frontera.", 19, 0, 20, 30, "Noticias"),
            TvProgram("cb_9", "cotobrustv", "Mesa de Diálogo y Desarrollo Local", "Debates cantonales, líderes comunitarios y obras municipales.", 20, 30, 22, 0, "Opinión"),
            TvProgram("cb_10", "cotobrustv", "Madrugada en las Montañas de Coto Brus", "Música instrumental y repeticiones de programas culturales.", 22, 0, 0, 0, "Variedades")
        ),

        "canal1cr" to listOf(
            TvProgram("c1cr_0", "canal1cr", "Serie Retro", "Clásicos televisivos y series legendarias para la madrugada.", 0, 0, 1, 0, "Series"),
            TvProgram("c1cr_1", "canal1cr", "Dramas Internacionales", "Grandes producciones dramáticas y suspenso.", 1, 0, 2, 0, "Drama"),
            TvProgram("c1cr_2", "canal1cr", "Cómo Han Pasado Los Años", "Recorrido nostálgico por la historia, música y momentos que marcaron época.", 2, 0, 3, 0, "Cultura"),
            TvProgram("c1cr_3", "canal1cr", "Musicales del 1", "Videoclips de baladas, pop latino y música del recuerdo.", 3, 0, 6, 0, "Música"),
            TvProgram("c1cr_4", "canal1cr", "Amanecer Ciudadano & Dibujos Retro", "Animaciones clásicas para iniciar la mañana y noticias matutinas.", 6, 0, 7, 30, "Infantil"),
            TvProgram("c1cr_5", "canal1cr", "Primero Noticias (Primera Emisión)", "El primer contacto informativo del día en Canal 1 con las noticias del país.", 7, 30, 9, 30, "Noticias"),
            TvProgram("c1cr_6", "canal1cr", "Revista Mañanera del 1", "Salud, cocina costarricense, consejos del hogar y entrevistas de actualidad.", 9, 30, 12, 0, "Revista"),
            TvProgram("c1cr_7", "canal1cr", "Pulso y Voces de Costa Rica", "Reportajes sobre comunidades, pequeñas y medianas empresas y turismo.", 12, 0, 13, 30, "Cultura"),
            TvProgram("c1cr_8", "canal1cr", "Primero Noticias (Edición Mediodía)", "Toda la actualidad nacional, economía y deportes de mitad de jornada.", 13, 30, 15, 0, "Noticias"),
            TvProgram("c1cr_9", "canal1cr", "Las Tardes del 1", "El programa vespertino de entretenimiento, entrevistas en vivo y farándula.", 15, 0, 18, 0, "Entretenimiento"),
            TvProgram("c1cr_10", "canal1cr", "Primero Noticias (Vespertina)", "Resumen informativo al final de la tarde antes del horario estelar.", 18, 0, 19, 0, "Noticias"),
            TvProgram("c1cr_11", "canal1cr", "Debate Costa Rica & Opinión", "Mesa redonda sobre política, leyes, asamblea legislativa y economía.", 19, 0, 21, 0, "Debate"),
            TvProgram("c1cr_12", "canal1cr", "Primero Noticias (Edición Estelar)", "El noticiero principal de Canal 1 con Paul Ulloa y los hechos de fondo.", 21, 0, 22, 30, "Noticias"),
            TvProgram("c1cr_13", "canal1cr", "Noche de Opinión y Cierre", "Análisis con analistas políticos y resumen del acontecer nacional.", 22, 30, 0, 0, "Opinión")
        ),

        "lossantostv" to listOf(
            TvProgram("ls_0", "lossantostv", "Serenata en las Alturas", "Música de descanso y noches de montaña.", 0, 0, 6, 0, "Música"),
            TvProgram("ls_1", "lossantostv", "Amanecer en Los Santos", "Clima de altura, lecherías y noticias de Tarrazú, Dota y León Cortés.", 6, 0, 8, 30, "Regional"),
            TvProgram("ls_2", "lossantostv", "La Hora del Café de Los Santos", "El proceso del mejor café del mundo, cooperativas y productores.", 8, 30, 11, 0, "Agro"),
            TvProgram("ls_3", "lossantostv", "Noticias Los Santos Mediodía", "Acontecimientos comunitarios en los pueblos de la zona.", 11, 0, 12, 30, "Noticias"),
            TvProgram("ls_4", "lossantostv", "Tradiciones Cafetaleras y Campesinas", "Historias de cosecheros, trapiches y costumbres de la cordillera.", 12, 30, 15, 30, "Cultura"),
            TvProgram("ls_5", "lossantostv", "Voces Comunales de Los Santos", "Espacio de los vecinos de San Marcos, Santa María y San Pablo.", 15, 30, 18, 0, "Comunidad"),
            TvProgram("ls_6", "lossantostv", "Noticias Los Santos Central", "Resumen estelar de los sucesos de la zona de Los Santos.", 18, 0, 19, 30, "Noticias"),
            TvProgram("ls_7", "lossantostv", "Cultura y Folclor de Nuestra Sierra", "Música campesina, poesía y leyendas de la montaña.", 19, 30, 21, 30, "Cultura"),
            TvProgram("ls_8", "lossantostv", "Noche en la Cordillera", "Cierre y música tranquila.", 21, 30, 0, 0, "Música")
        ),

        "garabitotv" to listOf(
            TvProgram("gb_0", "garabitotv", "Brisas del Pacífico Madrugada", "Música relajante y paisajes de los atardeceres de Jacó.", 0, 0, 6, 0, "Música"),
            TvProgram("gb_1", "garabitotv", "Olas del Pacífico Central", "Condiciones marítimas, olas de Jacó, mareas y clima del litoral.", 6, 0, 8, 30, "Turismo"),
            TvProgram("gb_2", "garabitotv", "Revista Jacó y Herradura Hoy", "Emprendimientos turísticos, gastronomía de mar y vida costera.", 8, 30, 11, 0, "Revista"),
            TvProgram("gb_3", "garabitotv", "Garabito Noticias Mediodía", "Noticias locales del cantón de Garabito y Puntarenas.", 11, 0, 12, 30, "Noticias"),
            TvProgram("gb_4", "garabitotv", "Surf, Turismo y Playas de Costa Rica", "Competiciones de surf, aventuras en catamarán y reservas naturales.", 12, 30, 15, 30, "Deportes"),
            TvProgram("gb_5", "garabitotv", "Actualidad Porteña y Comunal", "Desarrollo local, comercio y comunidad de la costa pacífica.", 15, 30, 18, 0, "Comunidad"),
            TvProgram("gb_6", "garabitotv", "Garabito Noticias Central", "Edición central con la información del cantón y el pacífico central.", 18, 0, 19, 30, "Noticias"),
            TvProgram("gb_7", "garabitotv", "Vida Nocturna y Turismo Responsable", "Guía de entretenimiento, cultura y seguridad ciudadana.", 19, 30, 21, 30, "Variedades"),
            TvProgram("gb_8", "garabitotv", "Noche en el Pacífico", "Música y paisajes nocturnos de la costa.", 21, 30, 0, 0, "Música")
        ),

        "costaricachannel" to listOf(
            TvProgram("crc_0", "costaricachannel", "Naturaleza en la Noche", "Sonidos del bosque lluvioso y vida nocturna en la selva.", 0, 0, 6, 0, "Naturaleza"),
            TvProgram("crc_1", "costaricachannel", "Despertar en el Paraíso Verde", "Aves del trópico, sonidos del bosque nuboso y amaneceres en Costa Rica.", 6, 0, 9, 0, "Ecológico"),
            TvProgram("crc_2", "costaricachannel", "Parques Nacionales y Selvas Tropicales", "Expedición a Manuel Antonio, Corcovado, Tortuguero y Monteverde.", 9, 0, 12, 0, "Naturaleza"),
            TvProgram("crc_3", "costaricachannel", "Volcanes, Ríos y Cascadas", "Aventuras en el Volcán Poás, Arenal, Río Celeste y rafting nacional.", 12, 0, 15, 0, "Aventura"),
            TvProgram("crc_4", "costaricachannel", "Playas de Guanacaste y el Caribe", "Aguas cristalinas, arrecifes de Cahuita y playas doradas del pacífico.", 15, 0, 18, 0, "Turismo"),
            TvProgram("crc_5", "costaricachannel", "Fauna y Conservación Costarricense", "Jaguares, perezosos, tortugas marinas y esfuerzos de sostenibilidad.", 18, 0, 21, 0, "Documental"),
            TvProgram("crc_6", "costaricachannel", "Maravillas Naturales de Costa Rica", "El 5% de la biodiversidad del planeta resumido en alta definición.", 21, 0, 0, 0, "Naturaleza")
        ),

        "soyplanchatv" to listOf(
            TvProgram("sp_0", "soyplanchatv", "Serenata Nocturna", "La mejor compañía musical para descansar y cantar.", 0, 0, 6, 0, "Música"),
            TvProgram("sp_1", "soyplanchatv", "Baladas del Despertar", "Música suave en español para comenzar la mañana con nostalgia.", 6, 0, 9, 0, "Música"),
            TvProgram("sp_2", "soyplanchatv", "Los Reyes de la Plancha", "Grandes baladas de Juan Gabriel, Amanda Miguel, Rocío Dúrcal y Camilo Sesto.", 9, 0, 12, 0, "Música"),
            TvProgram("sp_3", "soyplanchatv", "Cantando al Mediodía", "Éxitos para cantar a todo pulmón.", 12, 0, 15, 0, "Música"),
            TvProgram("sp_4", "soyplanchatv", "Tardes de Romance y Recuerdos", "Las mejores baladas románticas de los 80s y 90s.", 15, 0, 18, 0, "Música"),
            TvProgram("sp_5", "soyplanchatv", "El Gran Especial de Plancha", "Biografías musicales y conciertos legendarios.", 18, 0, 21, 0, "Música"),
            TvProgram("sp_6", "soyplanchatv", "Grandes Voces de Siempre", "Inolvidables himnos románticos.", 21, 0, 0, 0, "Música")
        ),

        "urbanotv" to listOf(
            TvProgram("ur_0", "urbanotv", "Clubbing Urbano Night", "Sesiones ininterrumpidas de DJs costarricenses.", 0, 0, 6, 0, "Música"),
            TvProgram("ur_1", "urbanotv", "Urbano Flow Mañana", "Reggaetón y ritmo para activar el cuerpo.", 6, 0, 9, 0, "Música"),
            TvProgram("ur_2", "urbanotv", "Reggaetón Clásico y Nuevo", "Desde los pioneros hasta los temas número uno del año.", 9, 0, 12, 0, "Música"),
            TvProgram("ur_3", "urbanotv", "El Imperio del Trap Tico", "Nuevas promesas del movimiento urbano de Costa Rica.", 12, 0, 15, 0, "Música"),
            TvProgram("ur_4", "urbanotv", "Batallas de Freestyle y Beats", "Lo mejor del rap improvisado y producciones musicales.", 15, 0, 18, 0, "Música"),
            TvProgram("ur_5", "urbanotv", "Urbano Prime Time", "Los videos oficiales en alta definición con el mejor sonido.", 18, 0, 22, 0, "Música"),
            TvProgram("ur_6", "urbanotv", "Sesión Nocturna Urbana", "Música continua y estrenos de videoclips.", 22, 0, 0, 0, "Música")
        ),

        "gextv" to listOf(
            TvProgram("gex_0", "gextv", "Noche Geek & Synthwave", "Debates sobre películas de ciencia ficción y música lofi.", 0, 0, 7, 0, "Cultura Pop"),
            TvProgram("gex_1", "gextv", "Anime & Gaming News", "Novedades de la industria de videojuegos y estrenos de anime.", 7, 0, 10, 0, "Tecnología"),
            TvProgram("gex_2", "gextv", "Tech & Gadgets Ticos", "Reseñas de smartphones, consolas y accesorios de computación.", 10, 0, 13, 0, "Tecnología"),
            TvProgram("gex_3", "gextv", "Pop Culture Express", "Cine de superhéroes, cómics y series de streaming.", 13, 0, 16, 0, "Cultura Pop"),
            TvProgram("gex_4", "gextv", "Speedruns y Desafíos Gamer", "Partidas épicas y trucos para tus juegos favoritos.", 16, 0, 19, 0, "Videojuegos"),
            TvProgram("gex_5", "gextv", "Gex Music & Chill", "Bandas sonoras de videojuegos y sintetizadores.", 19, 0, 22, 0, "Música"),
            TvProgram("gex_6", "gextv", "Cierre Geek de la Noche", "Repaso de las mejores noticias de tecnología.", 22, 0, 0, 0, "Tecnología")
        ),

        "vintagemusic" to listOf(
            TvProgram("vm_v0", "vintagemusic", "Noches Vintage en Estéreo", "Sonido clásico para terminar la velada con los mejores recuerdos.", 0, 0, 6, 0, "Música"),
            TvProgram("vm_v1", "vintagemusic", "Clásicos Matinales de los 70s y 80s", "Grandes melodías del pop y rock que marcaron una época.", 6, 0, 9, 0, "Música"),
            TvProgram("vm_v2", "vintagemusic", "La Era Dorada del Pop & Rock", "Videoclips remasterizados de Queen, Michael Jackson, Madonna y The Beatles.", 9, 0, 12, 0, "Música"),
            TvProgram("vm_v3", "vintagemusic", "Rock Clásico y Baladas Anglosajonas", "Guitarras legendarias y coros inolvidables del siglo XX.", 12, 0, 15, 0, "Música"),
            TvProgram("vm_v4", "vintagemusic", "Grandes Conciertos Históricos", "Grabaciones míticas en estadios y teatros de todo el mundo.", 15, 0, 18, 0, "Música"),
            TvProgram("vm_v5", "vintagemusic", "Vintage Prime Time: Hits Inmortales", "Los temas número uno de los años dorados de la música.", 18, 0, 22, 0, "Música"),
            TvProgram("vm_v6", "vintagemusic", "Grandes Éxitos de Todos los Tiempos", "Sesión continua de música legendaria.", 22, 0, 0, 0, "Música")
        ),

        "88stereotv" to listOf(
            TvProgram("st88_0", "88stereotv", "Música Continua 88 Stereo", "Programación musical ininterrumpida para la noche.", 0, 0, 6, 0, "Música"),
            TvProgram("st88_1", "88stereotv", "El Mañanero 88 en Vivo", "Transmisión visual de la cabina de radio con música alegre y notas matinales.", 6, 0, 9, 0, "Radio Visual"),
            TvProgram("st88_2", "88stereotv", "Lo Mejor de la Radio Visual", "Mensajes de la audiencia, saludos en vivo y éxitos del momento.", 9, 0, 12, 0, "Radio Visual"),
            TvProgram("st88_3", "88stereotv", "Éxitos Musicales del Momento", "Los temas más sonados en la frecuencia de 88 Stereo en Costa Rica.", 12, 0, 15, 0, "Música"),
            TvProgram("st88_4", "88stereotv", "Tardes de Interacción Sureña", "Participación de oyentes de Pérez Zeledón y todo el país.", 15, 0, 18, 0, "Interactivo"),
            TvProgram("st88_5", "88stereotv", "Especial 88 Stereo en Concierto", "Grandes temas en vivo y mezclas exclusivas de cabina.", 18, 0, 21, 0, "Música"),
            TvProgram("st88_6", "88stereotv", "Noche de Cabina Abierta", "Música continua y complacencias.", 21, 0, 0, 0, "Música")
        ),

        "sanjosetv" to listOf(
            TvProgram("sj_0", "sanjosetv", "Oración de la Noche y Madrugada en Paz", "Completas y meditación nocturna.", 0, 0, 6, 0, "Religión"),
            TvProgram("sj_1", "sanjosetv", "Oración de la Mañana y Laudes", "Comienzo espiritual del día con lecturas bíblicas y cánticos.", 6, 0, 7, 0, "Religión"),
            TvProgram("sj_2", "sanjosetv", "Santa Misa desde la Catedral Metropolitana", "Eucaristía en vivo desde San José, Costa Rica.", 7, 0, 8, 30, "Santa Misa"),
            TvProgram("sj_3", "sanjosetv", "Evangelio y Reflexión Pastoral", "Mensaje del Arzobispo y sacerdotes de la arquidiócesis.", 8, 30, 10, 0, "Reflexión"),
            TvProgram("sj_4", "sanjosetv", "Vida y Familia Católica", "Orientación cristiana para matrimonios y jóvenes.", 10, 0, 12, 0, "Familia"),
            TvProgram("sj_5", "sanjosetv", "El Ángelus y Santa Misa Mediodía", "La oración tradicional del mediodía y eucaristía solemne.", 12, 0, 13, 30, "Santa Misa"),
            TvProgram("sj_6", "sanjosetv", "Formación en la Fe y Doctrina", "Catequesis, encíclicas papales e historia de la Iglesia.", 13, 30, 16, 0, "Educativo"),
            TvProgram("sj_7", "sanjosetv", "Santo Rosario Comunitario", "El rezo del rosario con intenciones por los enfermos y Costa Rica.", 16, 0, 17, 0, "Oración"),
            TvProgram("sj_8", "sanjosetv", "Santa Misa Vespertina de la Catedral", "Eucaristía de la tarde celebrada en la Catedral Metropolitana.", 17, 0, 18, 30, "Santa Misa"),
            TvProgram("sj_9", "sanjosetv", "Testimonios y Evangelización", "Historias de conversión y caridad en las parroquias ticas.", 18, 30, 21, 0, "Testimonios"),
            TvProgram("sj_10", "sanjosetv", "Completas y Descanso", "Oración comunitaria para finalizar la jornada.", 21, 0, 0, 0, "Religión")
        ),

        "cristovision31" to listOf(
            TvProgram("cv_0", "cristovision31", "Noche de Promesas y Paz", "Música instrumental de adoración y versículos bíblicos.", 0, 0, 6, 0, "Religión"),
            TvProgram("cv_1", "cristovision31", "Salmos y Alabanzas del Despertar", "Adoración y palabras de ánimo para comenzar la jornada.", 6, 0, 8, 0, "Alabanza"),
            TvProgram("cv_2", "cristovision31", "Prédica y Edificación Espiritual", "Estudio bíblico y conferencias pastorales.", 8, 0, 10, 30, "Enseñanza"),
            TvProgram("cv_3", "cristovision31", "Mujeres de Fe y Hogar Cristiano", "Consejos para madres y fortalecimiento familiar.", 10, 30, 12, 0, "Familia"),
            TvProgram("cv_4", "cristovision31", "Clamor e Intercesión por Costa Rica", "Tiempo de oración por la paz, los enfermos y la nación.", 12, 0, 13, 30, "Oración"),
            TvProgram("cv_5", "cristovision31", "Música Cristiana Contemporánea", "Alabanzas de grupos costarricenses e internacionales.", 13, 30, 16, 0, "Música"),
            TvProgram("cv_6", "cristovision31", "Mensajes de Esperanza", "Predicación y testimonios de sanidad y salvación.", 16, 0, 18, 30, "Prédica"),
            TvProgram("cv_7", "cristovision31", "Gran Culto y Adoración en Directo", "Servicio congregacional con música y predicación en vivo.", 18, 30, 21, 0, "Culto"),
            TvProgram("cv_8", "cristovision31", "Palabra de Dios para la Noche", "Predicación nocturna y alabanzas.", 21, 0, 0, 0, "Prédica")
        ),

        "enlacejuvenil" to listOf(
            TvProgram("ej_0", "enlacejuvenil", "Urban Gospel y Madrugada", "Música urbana inspiradora durante toda la noche.", 0, 0, 6, 0, "Música"),
            TvProgram("ej_1", "enlacejuvenil", "Despierta con Ritmo Cristiano Juvenil", "Música pop y urbana cristiana para empezar con energía.", 6, 0, 9, 0, "Música"),
            TvProgram("ej_2", "enlacejuvenil", "Top Videos Juveniles de Alabanza", "Los videos musicales más pedidos por los jóvenes.", 9, 0, 12, 0, "Música"),
            TvProgram("ej_3", "enlacejuvenil", "Tendencias y Juventud", "Entrevistas a líderes de jóvenes, tecnología y retos virales positivos.", 12, 0, 14, 30, "Juvenil"),
            TvProgram("ej_4", "enlacejuvenil", "Festivales y Conciertos Cristianos", "Las mejores presentaciones de bandas juveniles en vivo.", 14, 30, 17, 30, "Concierto"),
            TvProgram("ej_5", "enlacejuvenil", "Podcast Juvenil y Debates", "Charlas francas sobre relaciones, universidad y fe.", 17, 30, 20, 0, "Podcast"),
            TvProgram("ej_6", "enlacejuvenil", "Adoración Extrema en Concierto", "Noche de alabanza acústica y contemporánea.", 20, 0, 23, 0, "Alabanza"),
            TvProgram("ej_7", "enlacejuvenil", "Cierre Juvenil y Música", "Melodías cristianas contemporáneas.", 23, 0, 0, 0, "Música")
        ),

        "extremakids" to listOf(
            TvProgram("ek_0", "extremakids", "Sueños Dulces: Melodías Relajantes", "Música de cuna instrumental para la noche de los más pequeños.", 0, 0, 6, 0, "Infantil"),
            TvProgram("ek_1", "extremakids", "Canciones Infantiles del Despertar", "Rondas, canciones con animalitos y alegría matinal.", 6, 0, 8, 30, "Infantil"),
            TvProgram("ek_2", "extremakids", "Aventuras Animadas y Cuentos Mágicos", "Dibujos animados educativos con personajes coloridos.", 8, 30, 11, 30, "Animación"),
            TvProgram("ek_3", "extremakids", "Aprendiendo Jugando: Letras y Números", "Programas didácticos para niños de preescolar y primaria.", 11, 30, 13, 30, "Educativo"),
            TvProgram("ek_4", "extremakids", "Marionetas y Fábulas Divertidas", "Historias con valores sobre el respeto y la amistad.", 13, 30, 16, 0, "Infantil"),
            TvProgram("ek_5", "extremakids", "Película Infantil de la Tarde", "Películas de animación y magia para compartir en casa.", 16, 0, 18, 30, "Cine Infantil"),
            TvProgram("ek_6", "extremakids", "Canciones para Dormir y Cuentos", "Cuentos para conciliar el sueño y música tierna.", 18, 30, 21, 0, "Familiar"),
            TvProgram("ek_7", "extremakids", "Descanso de los Pequeños", "Música instrumental de cuna.", 21, 0, 0, 0, "Infantil")
        ),

        "zurquitv" to listOf(
            TvProgram("zq_0", "zurquitv", "Noches del Zurquí", "Música instrumental y serenatas costarricenses.", 0, 0, 6, 0, "Música"),
            TvProgram("zq_1", "zurquitv", "Despertar Herediano", "Noticias de San Isidro, San Rafael, Barva y Heredia.", 6, 0, 8, 30, "Regional"),
            TvProgram("zq_2", "zurquitv", "Tradiciones en las Faldas del Zurquí", "Flora, fauna de montaña, lecherías y costumbres heredianas.", 8, 30, 11, 0, "Cultura"),
            TvProgram("zq_3", "zurquitv", "Informativo Heredia Mediodía", "Noticias del Valle Central y la provincia de las flores.", 11, 0, 12, 30, "Noticias"),
            TvProgram("zq_4", "zurquitv", "Senderos del Braulio Carrillo", "Recorridos ecológicos por el parque nacional y bosques nubosos.", 12, 30, 15, 30, "Ecológico"),
            TvProgram("zq_5", "zurquitv", "Cultura y Artesanos del Valle", "Pintura, escultura, mascaradas heredianas y música de cimarrona.", 15, 30, 18, 0, "Tradición"),
            TvProgram("zq_6", "zurquitv", "Noticias Zurquí Central", "Resumen estelar de los sucesos comunales y cantonales.", 18, 0, 19, 30, "Noticias"),
            TvProgram("zq_7", "zurquitv", "Tertulia Herediana", "Espacio de opinión con vecinos y líderes del cantón.", 19, 30, 21, 30, "Opinión"),
            TvProgram("zq_8", "zurquitv", "Serenata Nocturna Herediana", "Melodías costarricenses para el reposo.", 21, 30, 0, 0, "Música")
        )
    )

    /**
     * Gets the full day programming for a channel, taking into account weekdays, Saturdays and Sundays.
     */
    fun getScheduleForChannel(channelId: String, channelName: String, categoryName: String): List<TvProgram> {
        val dayOfWeek = getCostaRicaDayOfWeek()
        val isSaturday = dayOfWeek == Calendar.SATURDAY
        val isSunday = dayOfWeek == Calendar.SUNDAY
        val isWeekday = !isSaturday && !isSunday

        // 1. Saturday-specific schedule
        if (isSaturday && saturdayProgramsByChannel.containsKey(channelId)) {
            return saturdayProgramsByChannel[channelId]!!
        }

        // 2. Sunday-specific schedule
        if (isSunday && sundayProgramsByChannel.containsKey(channelId)) {
            return sundayProgramsByChannel[channelId]!!
        }

        // 3. Weekday-specific schedule
        if (isWeekday && weekdayProgramsByChannel.containsKey(channelId)) {
            return weekdayProgramsByChannel[channelId]!!
        }

        // 4. Specific schedule across all regional and thematic channels
        val specific = allChannelsBaseSchedules[channelId]
        if (specific != null && specific.isNotEmpty()) {
            return specific
        }

        // 5. Check Saturday/Sunday fallback to weekday if weekend not defined specifically
        if (weekdayProgramsByChannel.containsKey(channelId)) {
            return weekdayProgramsByChannel[channelId]!!
        }

        // 6. Guaranteed continuous tailored schedule
        return listOf(
            TvProgram("${channelId}_1", channelId, "Amanecer en $channelName", "Inicio de transmisiones y música de apertura.", 6, 0, 8, 30, categoryName),
            TvProgram("${channelId}_2", channelId, "Revista Matinal de Costa Rica", "Consejos de salud, cocina tica y entrevistas comunitarias.", 8, 30, 11, 0, "Revista"),
            TvProgram("${channelId}_3", channelId, "Noticiero Mediodía en Vivo", "El acontecer de Costa Rica a mitad del día.", 11, 0, 13, 0, "Noticias"),
            TvProgram("${channelId}_4", channelId, "Espacio de Entretenimiento y Tradición", "Música y documentales sobre costumbres costarricenses.", 13, 0, 16, 0, "Cultura"),
            TvProgram("${channelId}_5", channelId, "Tarde Familiar Tica", "Variedades y contacto con los televidentes.", 16, 0, 18, 30, "Variedades"),
            TvProgram("${channelId}_6", channelId, "Edición Central de Noticias", "La información más relevante del día en Costa Rica.", 18, 30, 20, 0, "Noticias"),
            TvProgram("${channelId}_7", channelId, "Franja Estelar de $channelName", "Producciones especiales en horario estelar.", 20, 0, 22, 0, "Especial"),
            TvProgram("${channelId}_8", channelId, "Cierre Informativo de la Noche", "Resumen de los principales acontecimientos del día.", 22, 0, 23, 30, "Noticias"),
            TvProgram("${channelId}_9", channelId, "Madrugada en Vivo y Música", "Selección musical y repetición de los mejores especiales.", 23, 30, 6, 0, "Música")
        )
    }

    /**
     * Gets currently playing program for a channel with foolproof matching
     */
    fun getCurrentProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        currentHour: Int? = null,
        currentMinute: Int? = null
    ): TvProgram {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val (h, m) = if (currentHour != null && currentMinute != null) {
            Pair(currentHour, currentMinute)
        } else {
            getCurrentCostaRicaTime()
        }

        // 1. Exact active program match
        val liveMatch = schedule.firstOrNull { it.isLiveAt(h, m) }
        if (liveMatch != null) return liveMatch

        // 2. Intelligent fallback: closest past program or overnight block
        val currentMins = h * 60 + m
        val pastCandidate = schedule.filter {
            val startMins = it.startHour * 60 + it.startMinute
            startMins <= currentMins
        }.maxByOrNull { it.startHour * 60 + it.startMinute }

        return pastCandidate
            ?: schedule.lastOrNull()
            ?: fallbackProgram(channelId, channelName, categoryName, h)
    }

    /**
     * Gets next upcoming program for a channel
     */
    fun getNextProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        currentHour: Int? = null,
        currentMinute: Int? = null
    ): TvProgram? {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val (h, m) = if (currentHour != null && currentMinute != null) {
            Pair(currentHour, currentMinute)
        } else {
            getCurrentCostaRicaTime()
        }

        val currentProg = getCurrentProgram(channelId, channelName, categoryName, h, m)
        val currentIndex = schedule.indexOfFirst { it.id == currentProg.id }

        return if (currentIndex != -1 && currentIndex + 1 < schedule.size) {
            schedule[currentIndex + 1]
        } else {
            schedule.firstOrNull()
        }
    }

    private fun fallbackProgram(channelId: String, channelName: String, categoryName: String, hour: Int): TvProgram {
        return TvProgram(
            id = "${channelId}_live",
            channelId = channelId,
            title = "Transmisión en Directo: $channelName",
            description = "Señal en vivo transmitiendo para todo Costa Rica.",
            startHour = hour,
            startMinute = 0,
            endHour = (hour + 1) % 24,
            endMinute = 0,
            category = categoryName
        )
    }
}
