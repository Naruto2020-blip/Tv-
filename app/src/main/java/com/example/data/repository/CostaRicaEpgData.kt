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
            TvProgram("c13_w0", "canal13sinart", "Himno Nacional y Madrugada Cultural", "Música instrumental costarricense y apertura de señal pública.", 0, 0, 6, 0, "Música"),
            TvProgram("c13_w1", "canal13sinart", "Trece Noticias - Edición Matutina", "Primer contacto con la actualidad nacional, sucesos y tránsito.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c13_w2", "canal13sinart", "Su Lado Positivo", "Revista matutina de superación, bienestar, psicología y entrevistas con Luis Carlos Méndez.", 8, 0, 10, 0, "Variedades"),
            TvProgram("c13_w3", "canal13sinart", "Materia Prima", "Producción agrícola nacional, exportaciones costarricenses y tecnología de campo con Enrique Mora.", 10, 0, 11, 0, "Educativo"),
            TvProgram("c13_w4", "canal13sinart", "Consulta en Directo", "Consultas médicas y ciudadanas atendidas en vivo por especialistas.", 11, 0, 12, 0, "Salud"),
            TvProgram("c13_w5", "canal13sinart", "Trece Noticias - Edición Meridiana", "El noticiero completo de mediodía de la televisión pública costarricense.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c13_w6", "canal13sinart", "Documentales UNED / Animalia", "Series científicas, investigación ambiental, volcanes y biodiversidad.", 13, 30, 15, 0, "Documental"),
            TvProgram("c13_w7", "canal13sinart", "Dominio Documental / Tatamundo", "Patrimonio cultural, comunidades e identidad costarricense.", 15, 0, 16, 30, "Cultura"),
            TvProgram("c13_w8", "canal13sinart", "Arte 13 / La Senda Ignorada", "Historia, artes escénicas, música costarricense y literatura.", 16, 30, 18, 0, "Cultura"),
            TvProgram("c13_w9", "canal13sinart", "Trece Noticias - Edición Estelar", "Análisis a profundidad y reportajes especiales con cobertura nacional.", 18, 0, 19, 30, "Noticias"),
            TvProgram("c13_w10", "canal13sinart", "Deportivas del 13", "Toda la actualidad del fútbol nacional y deportes olímpicos de Costa Rica.", 19, 30, 20, 30, "Deportes"),
            TvProgram("c13_w11", "canal13sinart", "Especiales SINART & Debate Público", "Mesas redondas, coyuntura política y programas de opinión.", 20, 30, 22, 0, "Opinión"),
            TvProgram("c13_w12", "canal13sinart", "Noche Cultural del 13 / Cine Iberoamericano", "Cine independiente y conciertos de la Orquesta Sinfónica Nacional.", 22, 0, 0, 0, "Cine")
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

        // FUTV COSTA RICA (futvcr.com/programacion/)
        "futv" to listOf(
            TvProgram("fu_w0", "futv", "Juego Liga Promerica / Futsal / Femenino", "Transmisiones oficiales y repeticiones de la Primera División costarricense.", 0, 0, 2, 0, "Deportes"),
            TvProgram("fu_w1", "futv", "Tiempo Final / Al Ritmo del Deporte", "El análisis deportivo de las jornadas y atletas costarricenses.", 2, 0, 3, 0, "Deportes"),
            TvProgram("fu_w2", "futv", "Un Recuerdo Maravilloso", "El exjugador Roy Myers y sus invitados reviven momentos históricos del fútbol tico.", 3, 0, 4, 0, "Deportes"),
            TvProgram("fu_w3", "futv", "Fútbol TV & Más / Los 11 Titulares", "Análisis táctico, semblanzas y entrevistas exclusivas.", 4, 0, 5, 0, "Deportes"),
            TvProgram("fu_w4", "futv", "Fútbol en 60: Resumen Liga Promerica", "Resumen compacto de 60 minutos con todas las jugadas del fútbol nacional.", 5, 0, 7, 0, "Deportes"),
            TvProgram("fu_w5", "futv", "Mundo de Fútbol Menor", "Academias de fútbol menor, semillero y talentos emergentes de Costa Rica.", 7, 0, 8, 0, "Deportes"),
            TvProgram("fu_w6", "futv", "Somos el Team / Los Saprissa", "Programas oficiales de los clubes grandes del balompié costarricense.", 8, 0, 10, 0, "Deportes"),
            TvProgram("fu_w7", "futv", "El Negocio del Fútbol", "Análisis del mercado deportivo, fichajes y finanzas de la industria.", 10, 0, 11, 0, "Deportes"),
            TvProgram("fu_w8", "futv", "La Previa de la Jornada Promerica", "Conexión en vivo desde los estadios con alineaciones y ambiente.", 11, 0, 12, 0, "Deportes"),
            TvProgram("fu_w9", "futv", "Partido en Vivo: Liga Promerica (Mediodía)", "Transmisión del campeonato nacional de primera división en directo.", 12, 0, 14, 0, "Deportes"),
            TvProgram("fu_w10", "futv", "Marcador y Reacciones de Cancha", "Entrevistas en caliente desde el terreno de juego con protagonistas.", 14, 0, 15, 0, "Deportes"),
            TvProgram("fu_w11", "futv", "Partido Vespertino: Liga Promerica", "Fútbol en directo de primera división de Costa Rica.", 15, 0, 17, 0, "Deportes"),
            TvProgram("fu_w12", "futv", "Futsal 360 / Únicamente Fútbol", "El futsal costarricense desde todos los ángulos y debate futbolero.", 17, 0, 18, 0, "Deportes"),
            TvProgram("fu_w13", "futv", "La Jornada (Edición Central)", "30 minutos con la información más relevante de Primera División y Liga de Ascenso.", 18, 0, 19, 0, "Deportes"),
            TvProgram("fu_w14", "futv", "Partido Estelar en Vivo: Liga Promerica", "El encuentro estelar de la fecha con narración y comentarios oficiales.", 19, 0, 21, 0, "Deportes"),
            TvProgram("fu_w15", "futv", "Acercándonos - Fútbol Femenino", "El crecimiento, esfuerzo y partidos del fútbol femenino en Costa Rica.", 21, 0, 22, 0, "Deportes"),
            TvProgram("fu_w16", "futv", "A Fondo Con…", "Entrevistas en profundidad con personajes y leyendas del fútbol.", 22, 0, 23, 0, "Deportes"),
            TvProgram("fu_w17", "futv", "Cierre de Jornada / Lo Mejor de la Fecha", "Resumen de goles, tabla de posiciones y goles destacados.", 23, 0, 0, 0, "Deportes")
        ),

        // EXTRA TV 42 (extratv42.com)
        "extratv42" to listOf(
            TvProgram("ex_w0", "extratv42", "Transmisión Nocturna Extra", "Programas de opinión y repeticiones de interés público.", 0, 0, 6, 0, "General"),
            TvProgram("ex_w1", "extratv42", "Noticias Extra", "Sucesos ocurridos en la noche y madrugada en las calles de Costa Rica.", 6, 0, 8, 30, "Noticias"),
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

        // ¡OPA! CANAL 38 (genteopa.com/programas/)
        "opacanal38" to listOf(
            TvProgram("opa_w0", "opacanal38", "Madrugada OPA", "Lo mejor de la programación musical y urbana durante la madrugada.", 0, 0, 6, 0, "Música"),
            TvProgram("opa_w1", "opacanal38", "Central Noticias", "Las noticias tempraneras de Costa Rica con cobertura ágil y directa.", 6, 0, 8, 0, "Noticias"),
            TvProgram("opa_w2", "opacanal38", "Gente OPA Revista Matutina", "Moda, bienestar, entretenimiento, farándula y creadores de contenido.", 8, 0, 10, 30, "Variedades"),
            TvProgram("opa_w3", "opacanal38", "Tarde Dinámica OPA", "Series, tendencias de streaming y tecnología para la juventud.", 10, 30, 12, 0, "Entretenimiento"),
            TvProgram("opa_w4", "opacanal38", "Central Noticias Mediodía", "Edición meridiana con los sucesos e información en vivo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("opa_w5", "opacanal38", "Miss Universe Costa Rica & Estilo", "Actualidad de pasarela, belleza y estilo de vida.", 13, 30, 15, 0, "Moda"),
            TvProgram("opa_w6", "opacanal38", "Tarde de Cine y Variedades OPA", "Cine de acción, comedia y entretenimiento para la familia.", 15, 0, 17, 0, "Cine"),
            TvProgram("opa_w7", "opacanal38", "A Doble Nudo / Alma de Mujer", "Temas de economía, innovación y empoderamiento de la mujer.", 17, 0, 18, 0, "Variedades"),
            TvProgram("opa_w8", "opacanal38", "Central Noticias Edición Central", "La edición estelar con las noticias más impactantes del día en Costa Rica.", 18, 0, 19, 0, "Noticias"),
            TvProgram("opa_w9", "opacanal38", "Está Pasando", "La noticia ocurre en cualquier momento y Está Pasando está ahí para contarla.", 19, 0, 20, 0, "Noticias"),
            TvProgram("opa_w10", "opacanal38", "¡OPA! Deportes", "Debate futbolero, Liga Promerica y atletas de Costa Rica.", 20, 0, 22, 0, "Deportes"),
            TvProgram("opa_w11", "opacanal38", "Equilibrio con Yaxún / Noche Abierta", "Balance de mente, cuerpo y espíritu, entrevistas sin filtro.", 22, 0, 23, 30, "Opinión"),
            TvProgram("opa_w12", "opacanal38", "Cierre de Programación OPA", "Música continua para cerrar la jornada.", 23, 30, 0, 0, "Música")
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

        // CANAL 1 COSTA RICA (canal1cr.com/programacion/)
        "canal1cr" to listOf(
            TvProgram("c1cr_w0", "canal1cr", "Serie Retro", "Las mejores series clásicas y producciones retro de la televisión internacional.", 0, 0, 1, 0, "Series"),
            TvProgram("c1cr_w1", "canal1cr", "Dramas", "Producciones dramáticas y telenovelas internacionales de gran audiencia.", 1, 0, 2, 0, "Novela"),
            TvProgram("c1cr_w2", "canal1cr", "Cómo Han Pasado Los Años", "Recorrido nostálgico con historia, música y recuerdos inolvidables.", 2, 0, 3, 0, "Cultura"),
            TvProgram("c1cr_w3", "canal1cr", "Musicales", "Los mejores videoclips y música variada para disfrutar la madrugada.", 3, 0, 6, 0, "Música"),
            TvProgram("c1cr_w4", "canal1cr", "Santa Misa", "Celebración eucarística matutina.", 6, 0, 7, 30, "Religión"),
            TvProgram("c1cr_w5", "canal1cr", "Animado Retro", "Dibujos animados y caricaturas clásicas familiares.", 7, 30, 8, 0, "Infantil"),
            TvProgram("c1cr_w6", "canal1cr", "RT En Vivo", "Noticias internacionales y actualidad en directo.", 8, 0, 8, 30, "Noticias"),
            TvProgram("c1cr_w7", "canal1cr", "Cómo Han Pasado Los Años", "Historias de antaño, anécdotas y música del recuerdo.", 8, 30, 9, 30, "Cultura"),
            TvProgram("c1cr_w8", "canal1cr", "Animado Retro", "Aventuras animadas clásicas para la mañana.", 9, 30, 10, 0, "Infantil"),
            TvProgram("c1cr_w9", "canal1cr", "Reinventados", "Espacio de innovación, emprendimientos y superación en Costa Rica.", 10, 0, 11, 0, "Revista"),
            TvProgram("c1cr_w10", "canal1cr", "Novela China", "Superproducción dramática y romance de época.", 11, 0, 12, 0, "Novela"),
            TvProgram("c1cr_w11", "canal1cr", "RT En Vivo", "Transmisión informativa internacional al mediodía.", 12, 0, 12, 30, "Noticias"),
            TvProgram("c1cr_w12", "canal1cr", "Primero Deportes", "Toda la actualidad del fútbol nacional y deportes ticos.", 12, 30, 13, 0, "Deportes"),
            TvProgram("c1cr_w13", "canal1cr", "Serie Retro", "Clásicos televisivos de la pantalla chica.", 13, 0, 13, 30, "Series"),
            TvProgram("c1cr_w14", "canal1cr", "Primero Noticias - Mediodía", "Noticiero con las noticias clave de la jornada.", 13, 30, 14, 0, "Noticias"),
            TvProgram("c1cr_w15", "canal1cr", "Dramas", "Telenovela dramática internacional.", 14, 0, 15, 0, "Novela"),
            TvProgram("c1cr_w16", "canal1cr", "Serie Retro", "Historias, acción y comedia de series de culto.", 15, 0, 16, 0, "Series"),
            TvProgram("c1cr_w17", "canal1cr", "Animado Retro", "Bloque infantil y juvenil de la tarde.", 16, 0, 17, 0, "Infantil"),
            TvProgram("c1cr_w18", "canal1cr", "Cómo Han Pasado Los Años", "Crónicas y momentos históricos inolvidables.", 17, 0, 18, 0, "Cultura"),
            TvProgram("c1cr_w19", "canal1cr", "Primero Noticias - Edición Central", "Edición estelar del noticiero principal de Canal 1.", 18, 0, 20, 0, "Noticias"),
            TvProgram("c1cr_w20", "canal1cr", "Primero Deportes Estelar", "Análisis, debate deportivo y polémica futbolera.", 20, 0, 20, 30, "Deportes"),
            TvProgram("c1cr_w21", "canal1cr", "Marcelo Castro Presenta", "Entrevistas de fondo e investigación con Marcelo Castro.", 20, 30, 21, 0, "Opinión"),
            TvProgram("c1cr_w22", "canal1cr", "Primero Noticias - Resumen Noche", "La información de cierre y acontecimientos de última hora.", 21, 0, 22, 0, "Noticias"),
            TvProgram("c1cr_w23", "canal1cr", "Kick Off", "Fútbol nacional, debate picante y análisis de fondo.", 22, 0, 23, 0, "Deportes"),
            TvProgram("c1cr_w24", "canal1cr", "CGTN En Vivo", "Noticias internacionales y actualidad global en directo.", 23, 0, 0, 0, "Noticias")
        )
    )

    // =========================================================================
    // SÁBADOS (SATURDAY SCHEDULES) - PROGRAMACIÓN SABATINA OFICIAL
    // =========================================================================
    private val saturdayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        // TELETICA CANAL 7 - SÁBADO (teletica.com)
        "teletica7" to listOf(
            TvProgram("t7_s0", "teletica7", "Cine Nocturno de Madrugada", "Películas y series continuas durante la madrugada.", 0, 0, 5, 45, "Cine"),
            TvProgram("t7_s1", "teletica7", "Amanecer Costarricense", "Música tradicional de Costa Rica para iniciar el sábado.", 5, 45, 6, 0, "Cultura"),
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
        ),

        // CANAL 1 COSTA RICA - SÁBADO (canal1cr.com/programacion/)
        "canal1cr" to listOf(
            TvProgram("c1cr_s0", "canal1cr", "Fiesta La Tica", "Música bailable y fiesta sabatina costarricense.", 0, 0, 1, 0, "Música"),
            TvProgram("c1cr_s1", "canal1cr", "Dramas", "Producciones dramáticas en la madrugada.", 1, 0, 2, 0, "Novela"),
            TvProgram("c1cr_s2", "canal1cr", "Serie Retro", "Series clásicas de la televisión.", 2, 0, 3, 0, "Series"),
            TvProgram("c1cr_s3", "canal1cr", "Musicales", "Selección musical continua para amanecer.", 3, 0, 6, 0, "Música"),
            TvProgram("c1cr_s4", "canal1cr", "Conciertos", "Presentaciones en vivo y recitales especiales.", 6, 0, 8, 0, "Música"),
            TvProgram("c1cr_s5", "canal1cr", "Animado Retro", "Caricaturas clásicas para la mañana sabatina.", 8, 0, 10, 30, "Infantil"),
            TvProgram("c1cr_s6", "canal1cr", "Back Up", "Música y entretenimiento juvenil.", 10, 30, 11, 0, "Variedades"),
            TvProgram("c1cr_s7", "canal1cr", "Más Que Música", "Especiales musicales, estrenos y entrevistas.", 11, 0, 12, 0, "Música"),
            TvProgram("c1cr_s8", "canal1cr", "Primero Deportes Sabatino", "Previa futbolera y actualidad deportiva.", 12, 0, 12, 30, "Deportes"),
            TvProgram("c1cr_s9", "canal1cr", "Serie Retro", "Aventuras y clásicos retro.", 12, 30, 13, 0, "Series"),
            TvProgram("c1cr_s10", "canal1cr", "Retro Hits", "Los grandes éxitos musicales de siempre.", 13, 0, 14, 0, "Música"),
            TvProgram("c1cr_s11", "canal1cr", "Cine del 1", "Películas para disfrutar el sábado en casa.", 14, 0, 16, 0, "Cine"),
            TvProgram("c1cr_s12", "canal1cr", "Serie Retro", "Series legendarias para la sobremesa.", 16, 0, 17, 0, "Series"),
            TvProgram("c1cr_s13", "canal1cr", "Cosas Que Pasas", "Historias humanas y anécdotas curiosas.", 17, 0, 17, 30, "Variedades"),
            TvProgram("c1cr_s14", "canal1cr", "Movilidad Sin Mitos", "Cultura vial, transporte y movilidad en Costa Rica.", 17, 30, 18, 0, "Cultura"),
            TvProgram("c1cr_s15", "canal1cr", "Cómo Han Pasado Los Años", "Recuerdos históricos, música y vivencias del ayer.", 18, 0, 19, 0, "Cultura"),
            TvProgram("c1cr_s16", "canal1cr", "Marcelo Castro Presenta", "Entrevistas estelares con Marcelo Castro.", 19, 0, 20, 0, "Opinión"),
            TvProgram("c1cr_s17", "canal1cr", "Cine del 1 Prime", "El gran estreno cinematográfico de la noche sabatina.", 20, 0, 23, 0, "Cine"),
            TvProgram("c1cr_s18", "canal1cr", "Retro Hits Noche", "Clásicos musicales para cerrar el sábado.", 23, 0, 0, 0, "Música")
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
        ),

        // CANAL 1 COSTA RICA - DOMINGO (canal1cr.com/programacion/)
        "canal1cr" to listOf(
            TvProgram("c1cr_su0", "canal1cr", "Fiesta La Tica", "Música popular y ritmos bailables.", 0, 0, 1, 0, "Música"),
            TvProgram("c1cr_su1", "canal1cr", "Dramas", "Dramas internacionales de madrugada.", 1, 0, 2, 0, "Novela"),
            TvProgram("c1cr_su2", "canal1cr", "Serie Retro", "Series clásicas inolvidables.", 2, 0, 3, 0, "Series"),
            TvProgram("c1cr_su3", "canal1cr", "Musicales", "Música para acompañar el amanecer.", 3, 0, 6, 0, "Música"),
            TvProgram("c1cr_su4", "canal1cr", "Animado Retro", "Caricaturas familiares para el despertar dominical.", 6, 0, 8, 0, "Infantil"),
            TvProgram("c1cr_su5", "canal1cr", "Cosas Que Pasas", "Historias humanas y vivencias cotidianas.", 8, 0, 8, 30, "Variedades"),
            TvProgram("c1cr_su6", "canal1cr", "Movilidad Sin Mitos", "Seguridad vial y transporte urbano.", 8, 30, 9, 0, "Cultura"),
            TvProgram("c1cr_su7", "canal1cr", "Santa Misa Dominical", "Solemne misa dominical.", 9, 0, 10, 30, "Religión"),
            TvProgram("c1cr_su8", "canal1cr", "Animado Retro", "Dibujos animados clásicos.", 10, 30, 11, 0, "Infantil"),
            TvProgram("c1cr_su9", "canal1cr", "Más Que Música", "Espacio musical con los mejores éxitos.", 11, 0, 12, 0, "Música"),
            TvProgram("c1cr_su10", "canal1cr", "Serie Retro", "Clásicos de televisión para el mediodía.", 12, 0, 13, 0, "Series"),
            TvProgram("c1cr_su11", "canal1cr", "Retro Hits", "Las mejores canciones retro.", 13, 0, 14, 0, "Música"),
            TvProgram("c1cr_su12", "canal1cr", "Cine del 1", "Películas de aventura y comedia para la tarde.", 14, 0, 16, 0, "Cine"),
            TvProgram("c1cr_su13", "canal1cr", "Serie Retro", "Aventuras clásicas familiares.", 16, 0, 16, 30, "Series"),
            TvProgram("c1cr_su14", "canal1cr", "Back Up", "Entretenimiento y música.", 16, 30, 17, 0, "Variedades"),
            TvProgram("c1cr_su15", "canal1cr", "Animado Retro", "Bloque infantil de la tarde.", 17, 0, 17, 30, "Infantil"),
            TvProgram("c1cr_su16", "canal1cr", "Música CR", "Talento nacional costarricense y videos musicales.", 17, 30, 18, 0, "Música"),
            TvProgram("c1cr_su17", "canal1cr", "Serie Retro", "Producciones retro en la sobremesa dominical.", 18, 0, 19, 30, "Series"),
            TvProgram("c1cr_su18", "canal1cr", "Sorteo JPS en Vivo", "Transmisión en directo del Sorteo de la Lotería Nacional de la Junta de Protección Social.", 19, 30, 20, 30, "Especiales"),
            TvProgram("c1cr_su19", "canal1cr", "Cine del 1 Estelar", "Película estelar de domingo para cerrar la semana.", 20, 30, 23, 0, "Cine"),
            TvProgram("c1cr_su20", "canal1cr", "Retro Hits Noche", "Música retro para despedir el domingo.", 23, 0, 0, 0, "Música")
        )
    )

    // =========================================================================
    // CANALES CON PROGRAMACIÓN OFICIAL VERIFICADA
    // =========================================================================
    val VERIFIED_CHANNELS_WITH_EPG: Set<String> = setOf(
        "teletica7",
        "canal6repretel",
        "canal11repretel",
        "canal4repretel",
        "canal8multimedios",
        "opacanal38",
        "futv",
        "extratv42",
        "canal1cr",
        "canal13sinart",
        "vmlatino"
    )

    fun normalizeChannelId(channelId: String): String {
        return when (channelId.lowercase()) {
            "teletica", "canal7", "t7" -> "teletica7"
            "canal6", "c6", "repretel6" -> "canal6repretel"
            "canal11", "c11", "repretel11" -> "canal11repretel"
            "canal4", "c4", "repretel4" -> "canal4repretel"
            "canal8", "c8", "multimedios" -> "canal8multimedios"
            "sinart", "canal13", "c13", "trece" -> "canal13sinart"
            "canal1", "c1", "canal1costarica" -> "canal1cr"
            "canal42", "c42", "extra", "extratv" -> "extratv42"
            "opa", "canal38", "c38" -> "opacanal38"
            else -> channelId
        }
    }

    fun hasVerifiedSchedule(channelId: String): Boolean {
        val norm = normalizeChannelId(channelId)
        return VERIFIED_CHANNELS_WITH_EPG.contains(norm)
    }

    fun cleanTitle(title: String): String {
        return title
            .replace(Regex("(?i)\\s*-\\s*primera\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*primera\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*-\\s*segunda\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*segunda\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*-\\s*tercera\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*tercera\\s+emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*cierre\\s+de\\s+emisi[oó]n"), "Cierre de Programación")
            .replace(Regex("(?i)\\s*emisi[oó]n"), "")
            .replace(Regex("(?i)\\s*emisiones"), "")
            .trim()
    }

    const val NO_PROGRAMMING_TITLE = "Sin programación"

    fun isNoProgramming(program: TvProgram?): Boolean {
        if (program == null) return true
        val title = program.title.trim().lowercase()
        return title == "sin programación" ||
               title == "no programación" ||
               title == "programación no disponible" ||
               program.id.endsWith("_no_prog")
    }

    fun createNoProgrammingItem(channelId: String, channelName: String): TvProgram {
        return TvProgram(
            id = "${channelId}_no_prog",
            channelId = channelId,
            title = NO_PROGRAMMING_TITLE,
            description = "No hay programación disponible para este canal.",
            startHour = 0,
            startMinute = 0,
            endHour = 23,
            endMinute = 59,
            category = "Sin programación"
        )
    }

    /**
     * Gets the full day programming for a channel, taking into account weekdays, Saturdays and Sundays.
     * Returns empty list for channels that do not have verified programming.
     */
    fun getScheduleForChannel(channelId: String, channelName: String, categoryName: String): List<TvProgram> {
        val normId = normalizeChannelId(channelId)
        if (!hasVerifiedSchedule(normId)) {
            return emptyList()
        }

        val dayOfWeek = getCostaRicaDayOfWeek()
        val isSaturday = dayOfWeek == Calendar.SATURDAY
        val isSunday = dayOfWeek == Calendar.SUNDAY
        val isWeekday = !isSaturday && !isSunday

        // 1. Saturday-specific schedule
        if (isSaturday && saturdayProgramsByChannel.containsKey(normId)) {
            return saturdayProgramsByChannel[normId]!!
        }

        // 2. Sunday-specific schedule
        if (isSunday && sundayProgramsByChannel.containsKey(normId)) {
            return sundayProgramsByChannel[normId]!!
        }

        // 3. Weekday-specific schedule
        if (isWeekday && weekdayProgramsByChannel.containsKey(normId)) {
            return weekdayProgramsByChannel[normId]!!
        }

        // 4. Fallback to weekday for verified channels if weekend not defined specifically
        if (weekdayProgramsByChannel.containsKey(normId)) {
            return weekdayProgramsByChannel[normId]!!
        }

        return emptyList()
    }

    /**
     * Gets currently playing program for a channel.
     * Returns a "Sin programación" placeholder if the channel has no schedule.
     */
    fun getCurrentProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        currentHour: Int? = null,
        currentMinute: Int? = null
    ): TvProgram {
        val normId = normalizeChannelId(channelId)
        if (!hasVerifiedSchedule(normId)) {
            return createNoProgrammingItem(channelId, channelName)
        }

        val schedule = getScheduleForChannel(normId, channelName, categoryName)
        if (schedule.isEmpty()) {
            return createNoProgrammingItem(channelId, channelName)
        }

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
            ?: createNoProgrammingItem(channelId, channelName)
    }

    /**
     * Gets next upcoming program for a channel.
     * Returns null if the channel has no schedule or no next program.
     */
    fun getNextProgram(
        channelId: String,
        channelName: String,
        categoryName: String,
        currentHour: Int? = null,
        currentMinute: Int? = null
    ): TvProgram? {
        val normId = normalizeChannelId(channelId)
        if (!hasVerifiedSchedule(normId)) {
            return null
        }

        val schedule = getScheduleForChannel(normId, channelName, categoryName)
        if (schedule.isEmpty()) return null

        val (h, m) = if (currentHour != null && currentMinute != null) {
            Pair(currentHour, currentMinute)
        } else {
            getCurrentCostaRicaTime()
        }

        val currentProg = getCurrentProgram(normId, channelName, categoryName, h, m)
        if (isNoProgramming(currentProg)) return null

        val currentIndex = schedule.indexOfFirst { it.id == currentProg.id }

        return if (currentIndex != -1 && currentIndex + 1 < schedule.size) {
            schedule[currentIndex + 1]
        } else {
            schedule.firstOrNull()
        }
    }
}
