package com.example.data.repository

import com.example.data.model.TvProgram
import java.util.Calendar
import java.util.TimeZone

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

    // Standard Weekday Schedules (Lunes a Viernes)
    private val weekdayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        "teletica7" to listOf(
            TvProgram("t7_w1", "teletica7", "Telenoticias Primera Hora", "El despertar informativo de Costa Rica con cobertura nacional, clima y tránsito.", 5, 45, 8, 0, "Noticias"),
            TvProgram("t7_w2", "teletica7", "Buen Día", "La revista matutina líder con consejos de salud, hogar, bienestar, cocina costarricense y motivación.", 8, 0, 10, 0, "Revista"),
            TvProgram("t7_w3", "teletica7", "Telenovela Matutina", "Historias apasionantes y melodramas internacionales de gran audiencia.", 10, 0, 11, 30, "Novela"),
            TvProgram("t7_w4", "teletica7", "De Boca en Boca", "El acontecer del espectáculo y farándula de Costa Rica con humor y entrevistas exclusivas.", 11, 30, 12, 0, "Farándula"),
            TvProgram("t7_w5", "teletica7", "Telenoticias Edición Meridiana", "El resumen informativo más completo a mitad del día con reportes en vivo de todo el país.", 12, 0, 13, 30, "Noticias"),
            TvProgram("t7_w6", "teletica7", "Cine de la Tarde", "Grandes producciones y películas familiares para disfrutar en casa.", 13, 30, 15, 30, "Cine"),
            TvProgram("t7_w7", "teletica7", "Laura Sin Censura", "Casos de la vida real analizados con franqueza y debate familiar.", 15, 30, 17, 0, "Entretenimiento"),
            TvProgram("t7_w8", "teletica7", "Calle 7 Informativo", "Periodismo ágil y cercano que recorre las calles del país para contar historias cotidianas.", 17, 0, 19, 0, "Noticias"),
            TvProgram("t7_w9", "teletica7", "Telenoticias Edición Estelar", "El noticiero estelar con las noticias de mayor impacto político, social y económico de Costa Rica.", 19, 0, 20, 0, "Noticias"),
            TvProgram("t7_w10", "teletica7", "7 Días / Formato Estelar", "Periodismo de investigación profunda, reportajes especiales y programas estelares.", 20, 0, 22, 0, "Opinión"),
            TvProgram("t7_w11", "teletica7", "Novela Prime Time", "Producciones dramáticas estelares en horario estelar.", 22, 0, 23, 0, "Novela"),
            TvProgram("t7_w12", "teletica7", "Telenoticias Medianoche", "El cierre de la jornada con el resumen de última hora y previsiones de mañana.", 23, 0, 23, 45, "Noticias"),
            TvProgram("t7_w13", "teletica7", "Cine Nocturno de Madrugada", "Películas de acción, suspenso y drama para la madrugada.", 23, 45, 5, 45, "Cine")
        ),

        "canal6repretel" to listOf(
            TvProgram("c6_w1", "canal6repretel", "Legado de Amor", "Telenovela matutina y reflexiones para el inicio de jornada.", 6, 0, 6, 50, "Novela"),
            TvProgram("c6_w2", "canal6repretel", "Noticias Repretel Matutina", "Las noticias más tempranas para salir informado a todo Costa Rica.", 6, 50, 9, 0, "Noticias"),
            TvProgram("c6_w3", "canal6repretel", "Giros de la Mañana", "Revista con consejos para el hogar, nutrición, salud y bienestar familiar.", 9, 0, 11, 45, "Revista"),
            TvProgram("c6_w4", "canal6repretel", "Noticias Repretel Meridiana", "El acontecer nacional con reportajes en vivo y cobertura al instante.", 11, 45, 13, 45, "Noticias"),
            TvProgram("c6_w5", "canal6repretel", "Telenovela Internacional", "Melodramas internacionales y romance para la sobremesa.", 13, 45, 15, 30, "Novela"),
            TvProgram("c6_w6", "canal6repretel", "Como Dice el Dicho", "Casos y lecciones de vida basados en dichos populares.", 15, 30, 17, 0, "Drama"),
            TvProgram("c6_w7", "canal6repretel", "Caso Cerrado", "Conflictos humanos, demandas legales y decisiones con la Dra. Polo.", 17, 0, 18, 0, "Entretenimiento"),
            TvProgram("c6_w8", "canal6repretel", "Conexión Fútbol Previa", "La previa de la jornada deportiva con panelistas costarricenses.", 18, 0, 19, 0, "Deportes"),
            TvProgram("c6_w9", "canal6repretel", "Noticias Repretel Edición Central", "El noticiero estelar con los acontecimientos del país.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c6_w10", "canal6repretel", "Telenovela Estelar Prime", "Grandes producciones internacionales de drama e intriga.", 20, 30, 22, 0, "Novela"),
            TvProgram("c6_w11", "canal6repretel", "Conexión Fútbol en Vivo", "Juegos, retos, humor y debate futbolero con exfutbolistas ticos.", 22, 0, 23, 0, "Deportes"),
            TvProgram("c6_w12", "canal6repretel", "Cine de Medianoche Repretel", "Películas y series en la madrugada.", 23, 0, 6, 0, "Cine")
        ),

        "futv" to listOf(
            TvProgram("fu_w1", "futv", "Goles del Fútbol Nacional", "Repaso de todas las anotaciones de la última jornada de la Liga Promerica.", 6, 0, 8, 0, "Deportes"),
            TvProgram("fu_w2", "futv", "Conexión Promerica", "Entrevistas con técnicos, capitanes y figuras de los clubes de primera división.", 8, 0, 10, 0, "Deportes"),
            TvProgram("fu_w3", "futv", "Clásicos Inolvidables de Costa Rica", "Revive los partidos históricos entre Saprissa, Alajuelense, Herediano y Cartaginés.", 10, 0, 12, 0, "Deportes"),
            TvProgram("fu_w4", "futv", "Zona Técnica", "Análisis estratégico y desglose táctico de jugadas clave con entrenadores expertos.", 12, 0, 13, 30, "Deportes"),
            TvProgram("fu_w5", "futv", "Mesa Redonda FUTV", "Debate ardiente sobre la selección nacional y los clubes del campeonato.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_w6", "futv", "Repetición del Partido Estelar", "Revive el encuentro más emocionante de la jornada anterior.", 15, 0, 17, 0, "Deportes"),
            TvProgram("fu_w7", "futv", "Previa y Actualidad de los Clubes", "Novedades, bajas y alineaciones de los equipos de primera división.", 17, 0, 18, 30, "Deportes"),
            TvProgram("fu_w8", "futv", "Pasión Tica: Especial de Fútbol", "Documentales sobre las glorias del fútbol costarricense.", 18, 30, 20, 0, "Deportes"),
            TvProgram("fu_w9", "futv", "FUTV Hoy: Noche de Fútbol", "Transmisión de fútbol con narración oficial y estadísticas en pantalla.", 20, 0, 21, 30, "Deportes"),
            TvProgram("fu_w10", "futv", "La Jornada al Detalle", "Estadísticas, tabla de posiciones y tabla acumulada.", 21, 30, 23, 0, "Deportes"),
            TvProgram("fu_w11", "futv", "Goles y Jugadas de la Historia", "Los mejores momentos del balompié tico de todas las épocas.", 23, 0, 6, 0, "Deportes")
        )
    )

    // Saturday-specific Schedules (Sábados)
    private val saturdayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        "teletica7" to listOf(
            TvProgram("t7_sat1", "teletica7", "Aventuras Animadas Matinales", "Dibujos animados clásicos y series familiares para comenzar el sábado.", 6, 0, 8, 30, "Infantil"),
            TvProgram("t7_sat2", "teletica7", "Cine Sabatino Familiar", "Películas de aventura y animación para disfrutar en familia.", 8, 30, 11, 0, "Cine"),
            TvProgram("t7_sat3", "teletica7", "Más Que Noticias (+QN Especial)", "Historias inspiradoras de comunidades y personajes de Costa Rica.", 11, 0, 12, 0, "Revista"),
            TvProgram("t7_sat4", "teletica7", "Telenoticias Fin de Semana Meridiana", "El acontecer noticioso del sábado con reportes en vivo.", 12, 0, 13, 0, "Noticias"),
            TvProgram("t7_sat5", "teletica7", "Super Cine de Sábado", "Grandes producciones cinematográficas y comedia para la tarde.", 13, 0, 16, 0, "Cine"),
            TvProgram("t7_sat6", "teletica7", "Sábado Feliz en Vivo", "El legendario programa sabatino de concursos, música bailable, artistas y premios en vivo por Canal 7.", 16, 0, 19, 0, "Entretenimiento"),
            TvProgram("t7_sat7", "teletica7", "Telenoticias Fin de Semana Estelar", "Edición completa con el balance noticioso y deportivo del sábado.", 19, 0, 20, 0, "Noticias"),
            TvProgram("t7_sat8", "teletica7", "Noche Estelar Teletica", "Grandes programas especiales, conciertos y formatos estelares de Teletica.", 20, 0, 22, 0, "Entretenimiento"),
            TvProgram("t7_sat9", "teletica7", "Cine Éxito de Hollywood", "Película estelar de acción y suspenso en horario prime time.", 22, 0, 0, 0, "Cine"),
            TvProgram("t7_sat10", "teletica7", "Cine Nocturno de Madrugada", "Películas y series continuas durante la madrugada.", 0, 0, 5, 45, "Cine"),
            TvProgram("t7_sat11", "teletica7", "Amanecer de Fin de Semana", "Música instrumental costarricense y apertura de transmisiones.", 5, 45, 6, 0, "Música")
        ),

        "canal6repretel" to listOf(
            TvProgram("c6_sat1", "canal6repretel", "Clásicos Animados Sabatinos", "Las caricaturas más queridas para iniciar el fin de semana.", 6, 0, 9, 0, "Infantil"),
            TvProgram("c6_sat2", "canal6repretel", "Cine Aventura de la Mañana", "Películas para toda la familia y diversión sin pausas.", 9, 0, 11, 30, "Cine"),
            TvProgram("c6_sat3", "canal6repretel", "Noticias Repretel Fin de Semana", "La actualidad del país, deportes y sucesos de las últimas horas.", 11, 30, 13, 0, "Noticias"),
            TvProgram("c6_sat4", "canal6repretel", "Super Cine Familiar Repretel", "Historias emocionantes de acción y fantasía.", 13, 0, 16, 0, "Cine"),
            TvProgram("c6_sat5", "canal6repretel", "Cine Estelar de la Tarde", "Producciones taquilleras internacionales.", 16, 0, 18, 0, "Cine"),
            TvProgram("c6_sat6", "canal6repretel", "Conexión Fútbol Sabatino", "Edición especial con la previa de los partidos y polémica en vivo.", 18, 0, 19, 0, "Deportes"),
            TvProgram("c6_sat7", "canal6repretel", "Noticias Repretel Central Fin de Semana", "El noticiero principal del sábado con el resumen de la jornada.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c6_sat8", "canal6repretel", "Mega Película Repretel", "El gran estreno cinematográfico del sábado por la noche.", 20, 30, 23, 0, "Cine"),
            TvProgram("c6_sat9", "canal6repretel", "Cine de Medianoche Repretel", "Películas y suspenso para la madrugada.", 23, 0, 6, 0, "Cine")
        ),

        "futv" to listOf(
            TvProgram("fu_sat1", "futv", "Goles de la Liga Promerica", "Todas las anotaciones del torneo nacional de primera división.", 6, 0, 8, 30, "Deportes"),
            TvProgram("fu_sat2", "futv", "Conexión Promerica Sábado", "Alineaciones confirmadas, ambiente de estadio y previa de los partidos.", 8, 30, 11, 0, "Deportes"),
            TvProgram("fu_sat3", "futv", "Partido en Directo: Liga Promerica (Matutino)", "Fútbol costarricense de primera división en vivo.", 11, 0, 13, 30, "Deportes"),
            TvProgram("fu_sat4", "futv", "Análisis y Marcador al Minuto", "Entrevistas en caliente desde el terreno de juego.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_sat5", "futv", "Partido en Directo: Liga Promerica (Tarde)", "Transmisión en directo del segundo choque de la jornada sabatina.", 15, 0, 17, 30, "Deportes"),
            TvProgram("fu_sat6", "futv", "Zona Técnica Sabatina", "Revisión arbitral de jugadas polémicas y estadísticas.", 17, 30, 19, 0, "Deportes"),
            TvProgram("fu_sat7", "futv", "Partido Estelar: Liga Promerica en Vivo", "El partido estelar de la noche con narración oficial en directo.", 19, 0, 21, 30, "Deportes"),
            TvProgram("fu_sat8", "futv", "Línea de 4 / Análisis de la Jornada", "Debate ardiente, conferencias de prensa y la tabla de posiciones.", 21, 30, 23, 0, "Deportes"),
            TvProgram("fu_sat9", "futv", "Repeticiones y Momentos Históricos", "Los mejores partidos y clásicos de la historia del fútbol costarricense.", 23, 0, 6, 0, "Deportes")
        )
    )

    // Sunday-specific Schedules (Domingos)
    private val sundayProgramsByChannel: Map<String, List<TvProgram>> = mapOf(
        "teletica7" to listOf(
            TvProgram("t7_sun1", "teletica7", "Caricaturas Clásicas Dominicales", "Aventuras animadas y series infantiles para el amanecer dominical.", 6, 0, 8, 0, "Infantil"),
            TvProgram("t7_sun2", "teletica7", "Santa Misa Dominical en Directo", "Celebración eucarística solemne de domingo para las familias de Costa Rica.", 8, 0, 9, 0, "Religión"),
            TvProgram("t7_sun3", "teletica7", "Cine Infantil Dominical", "Películas de animación, magia y aventura familiar.", 9, 0, 11, 30, "Cine"),
            TvProgram("t7_sun4", "teletica7", "Orgullo Tico / Más Que Noticias", "Costumbres, paisajes y gastronomía tradicional de Costa Rica.", 11, 30, 12, 0, "Cultura"),
            TvProgram("t7_sun5", "teletica7", "Telenoticias Fin de Semana Meridiana", "Resumen de los sucesos de la mañana y previa deportiva.", 12, 0, 13, 0, "Noticias"),
            TvProgram("t7_sun6", "teletica7", "Super Cine Dominical de la Tarde", "Grandes estrenos del cine internacional para disfrutar en casa.", 13, 0, 16, 0, "Cine"),
            TvProgram("t7_sun7", "teletica7", "Deporte Más / Torneo Nacional", "Cobertura de los partidos de la Liga Promerica y goles del domingo.", 16, 0, 18, 30, "Deportes"),
            TvProgram("t7_sun8", "teletica7", "7 Días / Resumen de Fondo", "Periodismo de investigación y reportajes especiales de la semana.", 18, 30, 19, 0, "Opinión"),
            TvProgram("t7_sun9", "teletica7", "Telenoticias Fin de Semana Estelar", "Edición estelar dominical con toda la actualidad de Costa Rica y el mundo.", 19, 0, 20, 0, "Noticias"),
            TvProgram("t7_sun10", "teletica7", "Gran Formato Familiar Dominical", "Producción estelar de entretenimiento familiar (Mira Quién Baila / Nace una Estrella / Tu Cara Me Suena).", 20, 0, 22, 30, "Entretenimiento"),
            TvProgram("t7_sun11", "teletica7", "Cine Éxito Prime Dominical", "Película estelar de acción y drama para cerrar el fin de semana.", 22, 30, 0, 30, "Cine"),
            TvProgram("t7_sun12", "teletica7", "Cine de Madrugada", "Programación cinematográfica continua durante la madrugada.", 0, 30, 6, 0, "Cine")
        ),

        "canal6repretel" to listOf(
            TvProgram("c6_sun1", "canal6repretel", "Caricaturas Dominicales", "Las mejores animaciones clásicas para los más pequeños.", 6, 0, 8, 0, "Infantil"),
            TvProgram("c6_sun2", "canal6repretel", "Santa Misa Dominical", "Celebración eucarística dominical con reflexión y cantos.", 8, 0, 9, 0, "Religión"),
            TvProgram("c6_sun3", "canal6repretel", "Cine Familiar Dominical", "Películas para compartir en familia durante la mañana.", 9, 0, 11, 30, "Cine"),
            TvProgram("c6_sun4", "canal6repretel", "Noticias Repretel Fin de Semana", "La información de sucesos, clima y deportes de Costa Rica.", 11, 30, 13, 0, "Noticias"),
            TvProgram("c6_sun5", "canal6repretel", "Cine de Acción Dominical", "Películas de aventura y emoción para la tarde.", 13, 0, 16, 0, "Cine"),
            TvProgram("c6_sun6", "canal6repretel", "Fútbol y Deportes Canal 6", "Análisis de los partidos de la jornada futbolera costarricense.", 16, 0, 19, 0, "Deportes"),
            TvProgram("c6_sun7", "canal6repretel", "Noticias Repretel Edición Central Fin de Semana", "El noticiero estelar del domingo con toda la información.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c6_sun8", "canal6repretel", "Cine Blockbuster Dominical", "El gran estreno cinematográfico de la noche de Repretel.", 20, 30, 23, 0, "Cine"),
            TvProgram("c6_sun9", "canal6repretel", "Cine de Medianoche Repretel", "Películas continuas para la madrugada.", 23, 0, 6, 0, "Cine")
        ),

        "futv" to listOf(
            TvProgram("fu_sun1", "futv", "Goles de la Liga Promerica", "Repaso de todas las anotaciones del fútbol nacional.", 6, 0, 8, 30, "Deportes"),
            TvProgram("fu_sun2", "futv", "La Gran Previa Dominical", "Conexión con los estadios ticos para los encuentros de hoy.", 8, 30, 11, 0, "Deportes"),
            TvProgram("fu_sun3", "futv", "Partido en Directo: Liga Promerica (Mediodía)", "Transmisión en vivo del fútbol de primera división de Costa Rica.", 11, 0, 13, 30, "Deportes"),
            TvProgram("fu_sun4", "futv", "Marcador y Análisis de Cancha", "Entrevistas y estadísticas al finalizar el primer partido.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_sun5", "futv", "Partido en Directo: Liga Promerica (Tarde)", "Transmisión en directo del encuentro dominical de la tarde.", 15, 0, 17, 30, "Deportes"),
            TvProgram("fu_sun6", "futv", "Zona Técnica Dominical", "Desglose táctico, polémica y jugadas decisivas.", 17, 30, 18, 30, "Deportes"),
            TvProgram("fu_sun7", "futv", "Partido Estelar Dominical en Vivo", "El clásico o partido de mayor expectativa con cobertura total.", 18, 30, 21, 0, "Deportes"),
            TvProgram("fu_sun8", "futv", "Tercer Tiempo y Tabla de Posiciones", "Resumen de toda la jornada, posiciones y declaraciones de los técnicos.", 21, 0, 23, 0, "Deportes"),
            TvProgram("fu_sun9", "futv", "Repeticiones y Momentos Históricos", "Grandes clásicos del balompié costarricense.", 23, 0, 6, 0, "Deportes")
        )
    )

    // Comprehensive schedules for ALL 28 Costa Rican channels
    private val allChannelsBaseSchedules: Map<String, List<TvProgram>> = mapOf(
        "canal8multimedios" to listOf(
            TvProgram("c8_1", "canal8multimedios", "Telediario Al Minuto", "Noticias ágiles desde primera hora con enlace directo a carreteras del Valle Central.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c8_2", "canal8multimedios", "La Revista Multimedios", "Entrevistas de salud, consejos legales, cocina y entretenimiento.", 8, 0, 10, 0, "Revista"),
            TvProgram("c8_3", "canal8multimedios", "Alerta 8", "Cobertura especializada de sucesos, rescates y seguridad en Costa Rica.", 10, 0, 12, 0, "Sucesos"),
            TvProgram("c8_4", "canal8multimedios", "Telediario Edición Mediodía", "La información más relevante del país presentada con análisis y dinamismo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c8_5", "canal8multimedios", "Fútbol Al Día", "El show de debate futbolero más polémico con panelistas apasionados del fútbol tico.", 13, 30, 15, 0, "Deportes"),
            TvProgram("c8_6", "canal8multimedios", "Tarde Redonda", "Actualidad, tendencias virales y entrevistas con invitados especiales.", 15, 0, 17, 0, "Entretenimiento"),
            TvProgram("c8_7", "canal8multimedios", "Telediario Tarde", "Actualización informativa antes de la edición estelar.", 17, 0, 18, 30, "Noticias"),
            TvProgram("c8_8", "canal8multimedios", "Telediario Estelar", "Edición central con reportajes en vivo y entrevistas con líderes nacionales.", 18, 30, 20, 0, "Noticias"),
            TvProgram("c8_9", "canal8multimedios", "Fútbol Al Día Noche", "La polémica de la jornada deportiva, jugadas dudosas y clásicos nacionales.", 20, 0, 21, 30, "Deportes"),
            TvProgram("c8_10", "canal8multimedios", "Al Cierre de Noticias", "Resumen internacional y análisis nocturno.", 21, 30, 23, 0, "Opinión"),
            TvProgram("c8_11", "canal8multimedios", "Programación Nocturna Multimedios", "Series y documentales para la madrugada.", 23, 0, 6, 0, "Variedades")
        ),

        "opacanal38" to listOf(
            TvProgram("opa_1", "opacanal38", "Amanecer con ¡OPA!", "Música, energía positiva y las primeras novedades del día.", 6, 0, 7, 0, "Música"),
            TvProgram("opa_2", "opacanal38", "Central Noticias Mañana", "Noticias sin rodeos con un enfoque fresco e innovador.", 7, 0, 9, 0, "Noticias"),
            TvProgram("opa_3", "opacanal38", "Gente OPA", "Espacio dinámico con entrevistas a emprendedores, tendencias de moda y gastronomía.", 9, 0, 11, 30, "Revista"),
            TvProgram("opa_4", "opacanal38", "Con Permiso", "Diálogos abiertos y comentarios sobre temas de actualidad nacional.", 11, 30, 12, 30, "Opinión"),
            TvProgram("opa_5", "opacanal38", "Central Noticias Mediodía", "Edición del mediodía con cobertura inmediata.", 12, 30, 14, 0, "Noticias"),
            TvProgram("opa_6", "opacanal38", "Tardes Dinámicas", "Cine, series y entretenimiento contemporáneo.", 14, 0, 17, 0, "Entretenimiento"),
            TvProgram("opa_7", "opacanal38", "La Previa OPA", "Mesa de discusión previa a la noche de entretenimiento.", 17, 0, 19, 0, "Variedades"),
            TvProgram("opa_8", "opacanal38", "Central Noticias Edición Central", "Las noticias más relevantes del día en formato ágil y visual.", 19, 0, 20, 30, "Noticias"),
            TvProgram("opa_9", "opacanal38", "¡OPA! Deportes", "Análisis de la jornada deportiva de Costa Rica y el mundo.", 20, 30, 22, 0, "Deportes"),
            TvProgram("opa_10", "opacanal38", "Noche Abierta", "Entrevistas nocturnas y cultura urbana.", 22, 0, 23, 30, "Opinión"),
            TvProgram("opa_11", "opacanal38", "Madrugada OPA", "Lo mejor de la programación del día y música.", 23, 30, 6, 0, "Variedades")
        ),

        "canal11repretel" to listOf(
            TvProgram("c11_1", "canal11repretel", "NC Once Noticias Mañana", "Noticiero con noticias frescas y reportajes comunitarios.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c11_2", "canal11repretel", "Telenovela Matutina", "Historias clásicas llenas de romance.", 8, 0, 10, 0, "Novela"),
            TvProgram("c11_3", "canal11repretel", "Aventuras Animadas", "Series clásicas de animación infantil.", 10, 0, 12, 0, "Infantil"),
            TvProgram("c11_4", "canal11repretel", "NC Once Edición Mediodía", "Actualidad nacional con periodismo ágil.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c11_5", "canal11repretel", "Novelas del Recuerdo", "Las producciones que marcaron épocas.", 13, 30, 15, 30, "Novela"),
            TvProgram("c11_6", "canal11repretel", "Series Premier", "Series internacionales aclamadas.", 15, 30, 18, 0, "Series"),
            TvProgram("c11_7", "canal11repretel", "Informe 11 Las Historias", "Historias de personajes pintorescos, leyendas costarricenses y pueblos con encanto.", 18, 0, 19, 30, "Cultura"),
            TvProgram("c11_8", "canal11repretel", "NC Once Edición Estelar", "Noticiero con análisis de la jornada.", 19, 30, 20, 30, "Noticias"),
            TvProgram("c11_9", "canal11repretel", "Cine de Aventuras", "Películas emocionantes para disfrutar de noche.", 20, 30, 22, 30, "Cine"),
            TvProgram("c11_10", "canal11repretel", "Programación Continua", "Películas clásicas y documentales.", 22, 30, 6, 0, "Variedades")
        ),

        "extratv42" to listOf(
            TvProgram("ex_1", "extratv42", "Noticias Extra Primera Emisión", "Sucesos ocurridos en la noche y madrugada en las calles de Costa Rica.", 6, 0, 8, 30, "Noticias"),
            TvProgram("ex_2", "extratv42", "Actualidad Nacional Tica", "Problemas comunales, denuncias vecinales y voz de las comunidades.", 8, 30, 10, 30, "Opinión"),
            TvProgram("ex_3", "extratv42", "Mundo Extra", "Noticias insólitas, descubrimientos y reportajes de interés general.", 10, 30, 12, 0, "Variedades"),
            TvProgram("ex_4", "extratv42", "Noticias Extra Edición Mediodía", "La información de sucesos y tribunalicia más completa del país.", 12, 0, 13, 30, "Noticias"),
            TvProgram("ex_5", "extratv42", "La Hora de Juan Carlos", "Comentarios directos, debates de fondo y periodismo sin ataduras.", 13, 30, 15, 0, "Opinión"),
            TvProgram("ex_6", "extratv42", "Entretenimiento y Tradición Tica", "Cultura popular costarricense, música de pueblo y costumbres.", 15, 0, 18, 0, "Cultura"),
            TvProgram("ex_7", "extratv42", "Noticias Extra Edición Central", "El noticiero con mayor arraigo popular en Costa Rica.", 18, 0, 19, 30, "Noticias"),
            TvProgram("ex_8", "extratv42", "El Polígrafo Político", "Entrevistas de profundidad a diputados y figuras del gobierno.", 19, 30, 21, 0, "Opinión"),
            TvProgram("ex_9", "extratv42", "Crónica Nocturna de Sucesos", "Recorrido nocturno por ambulancias, bomberos y policía de Costa Rica.", 21, 0, 22, 30, "Sucesos"),
            TvProgram("ex_10", "extratv42", "Transmisión Nocturna Extra", "Programas de opinión y repeticiones de interés público.", 22, 30, 6, 0, "General")
        ),

        "canal4repretel" to listOf(
            TvProgram("c4_1", "canal4repretel", "Aventuras Matinales Animadas", "Dibujos animados clásicos para empezar el día con alegría.", 6, 0, 8, 30, "Infantil"),
            TvProgram("c4_2", "canal4repretel", "Anime y Acción Canal 4", "Las mejores series de anime japonés y acción animada.", 8, 30, 11, 0, "Animación"),
            TvProgram("c4_3", "canal4repretel", "Series Cómicas y Familiares", "Las comedias internacionales más divertidas de la televisión.", 11, 0, 13, 30, "Comedia"),
            TvProgram("c4_4", "canal4repretel", "Cine Juvenil de la Tarde", "Películas de aventura, comedia y romance para la juventud.", 13, 30, 16, 0, "Cine"),
            TvProgram("c4_5", "canal4repretel", "Series de Misterio y Drama", "Casos policiacos, drama y suspenso sin pausas.", 16, 0, 18, 30, "Series"),
            TvProgram("c4_6", "canal4repretel", "Telenovela Juvenil Estelar", "Historias de romance y música en horario de la tarde.", 18, 30, 20, 30, "Novela"),
            TvProgram("c4_7", "canal4repretel", "Super Cine Canal 4", "Grandes producciones cinematográficas en prime time.", 20, 30, 22, 30, "Cine"),
            TvProgram("c4_8", "canal4repretel", "Noche de Series de Culto", "Series clásicas inolvidables y programación para la madrugada.", 22, 30, 6, 0, "Series")
        ),

        "vmlatino" to listOf(
            TvProgram("vm_1", "vmlatino", "El Despertador Musical VM", "Los videos más prendidos para iniciar el día con toda la energía.", 6, 0, 9, 0, "Música"),
            TvProgram("vm_2", "vmlatino", "Top 10 Tico", "Los 10 temas musicales más sonados en Costa Rica votados por el público.", 9, 0, 11, 0, "Música"),
            TvProgram("vm_3", "vmlatino", "Pop & Urban Mix", "Los éxitos internacionales del pop latino y la música urbana actual.", 11, 0, 13, 30, "Música"),
            TvProgram("vm_4", "vmlatino", "Los Más Pedidos de Costa Rica", "El playlist armado en vivo con los mensajes y peticiones de los televidentes.", 13, 30, 16, 0, "Música"),
            TvProgram("vm_5", "vmlatino", "Talento Nacional Costarricense", "Espacio exclusivo para bandas, solistas y productores ticos.", 16, 0, 18, 0, "Música"),
            TvProgram("vm_6", "vmlatino", "VM Golden Hour", "Los mejores himnos de reggaetón clásico, trap y remixes.", 18, 0, 21, 0, "Música"),
            TvProgram("vm_7", "vmlatino", "Conciertos VM Latino", "Grabaciones en directo de las mejores giras internacionales.", 21, 0, 23, 0, "Música"),
            TvProgram("vm_8", "vmlatino", "After Party VM Latino", "Música continua sin pausas para la noche y madrugada.", 23, 0, 6, 0, "Música")
        ),

        "colosaltv" to listOf(
            TvProgram("co_1", "colosaltv", "Amanecer en la Zona Sur", "Paisajes, café y noticias de Ciudad Neily, Corredores y Golfito.", 6, 0, 8, 30, "Regional"),
            TvProgram("co_2", "colosaltv", "Revista Sur y Campo", "Agricultura, palma, cacao y vida campesina en el sur de Costa Rica.", 8, 30, 11, 0, "Cultura"),
            TvProgram("co_3", "colosaltv", "Colosal Noticias Mediodía", "Información comunitaria y fronteriza al instante.", 11, 0, 12, 30, "Noticias"),
            TvProgram("co_4", "colosaltv", "Música del Sur", "Selección de baladas y música popular regional.", 12, 30, 15, 0, "Música"),
            TvProgram("co_5", "colosaltv", "Encuentro Comunal", "Los líderes de las comunidades del cantón plantean sus soluciones.", 15, 0, 18, 0, "Comunidad"),
            TvProgram("co_6", "colosaltv", "Colosal Noticias Estelar", "Edición central con cobertura de los cantones del sur.", 18, 0, 19, 30, "Noticias"),
            TvProgram("co_7", "colosaltv", "Deportes de la Zona Sur", "Fútbol regional, torneos locales y ligas cantonales.", 19, 30, 21, 0, "Deportes"),
            TvProgram("co_8", "colosaltv", "Noche Colosal", "Entretenimiento y transmisiones especiales.", 21, 0, 6, 0, "Variedades")
        ),

        "tvsur14" to listOf(
            TvProgram("sur_1", "tvsur14", "Despertar del Valle", "Noticias y clima del Valle de El General y Pérez Zeledón.", 6, 0, 8, 0, "Noticias"),
            TvProgram("sur_2", "tvsur14", "PZ Hoy Revista", "Emprendimientos generaleños, salud y cocina con sabor del sur.", 8, 0, 10, 30, "Revista"),
            TvProgram("sur_3", "tvsur14", "Tradición y Tierra Brunca", "Reportajes sobre las montañas del Chirripó y los pueblos del valle.", 10, 30, 12, 0, "Cultura"),
            TvProgram("sur_4", "tvsur14", "TV Sur Noticias Mediodía", "La información de Pérez Zeledón y la Región Brunca en vivo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("sur_5", "tvsur14", "Fútbol y Pasión Sureña", "Seguimiento al Municipal Pérez Zeledón y equipos locales.", 13, 30, 15, 30, "Deportes"),
            TvProgram("sur_6", "tvsur14", "La Tarde en TV Sur", "Música, arte y cultura local.", 15, 30, 18, 30, "Variedades"),
            TvProgram("sur_7", "tvsur14", "TV Sur Noticias Central", "La edición estelar con las noticias de mayor impacto en la región.", 18, 30, 20, 0, "Noticias"),
            TvProgram("sur_8", "tvsur14", "Diálogos del Sur", "Entrevistas de fondo con alcaldes, productores y ciudadanos.", 20, 0, 21, 30, "Opinión"),
            TvProgram("sur_9", "tvsur14", "Música de Nuestra Tierra", "Música costarricense para cerrar la noche.", 21, 30, 6, 0, "Música")
        ),

        "canal14sancarlos" to listOf(
            TvProgram("sc_1", "canal14sancarlos", "Amanecer de la Zona Norte", "Clima, lecherías y agricultura en Ciudad Quesada y llanuras.", 6, 0, 8, 0, "Regional"),
            TvProgram("sc_2", "canal14sancarlos", "Revista San Carlos Hoy", "Vida cotidiana, cooperativismo y emprendimientos locales.", 8, 0, 10, 30, "Revista"),
            TvProgram("sc_3", "canal14sancarlos", "Mundo Agropecuario TVN", "Tecnología agrícola, ganadería y producción sostenible.", 10, 30, 12, 0, "Educativo"),
            TvProgram("sc_4", "canal14sancarlos", "Noticias 14 Mediodía", "Información veraz de San Carlos, Upala, Los Chiles y Guatuso.", 12, 0, 13, 30, "Noticias"),
            TvProgram("sc_5", "canal14sancarlos", "Tardes del Norte", "Espacio familiar con música norteña y folclor.", 13, 30, 16, 0, "Cultura"),
            TvProgram("sc_6", "canal14sancarlos", "Ruta de los Volcanes", "Turismo y maravillas de La Fortuna y el Volcán Arenal.", 16, 0, 18, 0, "Turismo"),
            TvProgram("sc_7", "canal14sancarlos", "Noticias 14 Central", "Edición estelar con los sucesos de la zona norte de Costa Rica.", 18, 0, 19, 30, "Noticias"),
            TvProgram("sc_8", "canal14sancarlos", "AD San Carlos: Orgullo Norteño", "Programa dedicado a 'Los Toros del Norte' y deportes locales.", 19, 30, 21, 0, "Deportes"),
            TvProgram("sc_9", "canal14sancarlos", "Noches de San Carlos", "Variedades y repeticiones de programas especiales.", 21, 0, 6, 0, "Variedades")
        ),

        "cotobrustv" to listOf(
            TvProgram("cb_1", "cotobrustv", "Amanecer en San Vito y Coto Brus", "Noticias cantonales, clima de montaña, café y frontera sur con Panamá.", 6, 0, 8, 30, "Regional"),
            TvProgram("cb_2", "cotobrustv", "Revista Comunal Nuestra Gente", "Entrevistas a familias pioneras, cultura italo-costarricense y vida de campo.", 8, 30, 10, 30, "Revista"),
            TvProgram("cb_3", "cotobrustv", "Tradiciones de Coto Brus y Café de Altura", "Historia de la colonia de San Vito, cooperativas cafetaleras y agricultura.", 10, 30, 12, 0, "Cultura"),
            TvProgram("cb_4", "cotobrustv", "Noticias Coto Brus Mediodía", "Edición en vivo desde San Vito con hechos comunales y de la Zona Sur.", 12, 0, 13, 30, "Noticias"),
            TvProgram("cb_5", "cotobrustv", "Música y Tradición Sureña", "Baladas, música popular y memoria histórica de Coto Brus.", 13, 30, 15, 30, "Música"),
            TvProgram("cb_6", "cotobrustv", "Deportes Coto Brus y Ligas Menores", "Fútbol de los distritos de Sabalito, Agua Buena, Pittier y San Vito.", 15, 30, 17, 30, "Deportes"),
            TvProgram("cb_7", "cotobrustv", "Horizontes de Coto Brus y Naturaleza", "El Parque La Amistad, senderos de montaña y biodiversidad tica.", 17, 30, 19, 0, "Ecológico"),
            TvProgram("cb_8", "cotobrustv", "Noticias Coto Brus Edición Central", "Noticiero principal de la noche con el acontecer de San Vito y la frontera.", 19, 0, 20, 30, "Noticias"),
            TvProgram("cb_9", "cotobrustv", "Mesa de Diálogo y Desarrollo Local", "Debates cantonales, líderes comunitarios y obras municipales.", 20, 30, 22, 0, "Opinión"),
            TvProgram("cb_10", "cotobrustv", "Madrugada en las Montañas de Coto Brus", "Música instrumental y repeticiones de programas culturales.", 22, 0, 6, 0, "Variedades")
        ),

        "canal1cr" to listOf(
            TvProgram("c1cr_1", "canal1cr", "Amanecer Ciudadano", "Información fresca de la política nacional, tránsito y clima de Costa Rica.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c1cr_2", "canal1cr", "Pulso Nacional y Análisis", "Entrevistas de fondo sobre economía, trabajo y actualidad tica.", 8, 0, 10, 30, "Opinión"),
            TvProgram("c1cr_3", "canal1cr", "Voces de Costa Rica", "Reportajes sobre comunidades, emprendedores y desarrollo social.", 10, 30, 12, 30, "Cultura"),
            TvProgram("c1cr_4", "canal1cr", "Noticias Canal 1 Mediodía", "El resumen informativo de mitad de jornada con objetividad y rigor.", 12, 30, 14, 0, "Noticias"),
            TvProgram("c1cr_5", "canal1cr", "Ventana Cultural y Social", "Documentales sobre historia, artes y tradiciones costarricenses.", 14, 0, 17, 0, "Educativo"),
            TvProgram("c1cr_6", "canal1cr", "Debate Costa Rica en Directo", "Mesa redonda con analistas sobre temas legislativos y de gobierno.", 17, 0, 19, 0, "Debate"),
            TvProgram("c1cr_7", "canal1cr", "Noticias Canal 1 Estelar", "Edición central con las noticias más determinantes de la República.", 19, 0, 20, 30, "Noticias"),
            TvProgram("c1cr_8", "canal1cr", "Mesa de Análisis Político de Fondo", "Entrevistas exclusivas a figuras públicas y candidatos.", 20, 30, 22, 30, "Opinión"),
            TvProgram("c1cr_9", "canal1cr", "Cierre Informativo y Madrugada", "Resumen de lo más destacado del día en Costa Rica.", 22, 30, 6, 0, "Variedades")
        ),

        "lossantostv" to listOf(
            TvProgram("ls_1", "lossantostv", "Amanecer en Los Santos", "Clima de altura, lecherías y noticias de Tarrazú, Dota y León Cortés.", 6, 0, 8, 30, "Regional"),
            TvProgram("ls_2", "lossantostv", "La Hora del Café de Los Santos", "El proceso del mejor café del mundo, cooperativas y productores.", 8, 30, 11, 0, "Agro"),
            TvProgram("ls_3", "lossantostv", "Noticias Los Santos Mediodía", "Acontecimientos comunitarios en los pueblos de la zona.", 11, 0, 12, 30, "Noticias"),
            TvProgram("ls_4", "lossantostv", "Tradiciones Cafetaleras y Campesinas", "Historias de cosecheros, trapiches y costumbres de la cordillera.", 12, 30, 15, 30, "Cultura"),
            TvProgram("ls_5", "lossantostv", "Voces Comunales de Los Santos", "Espacio de los vecinos de San Marcos, Santa María y San Pablo.", 15, 30, 18, 0, "Comunidad"),
            TvProgram("ls_6", "lossantostv", "Noticias Los Santos Central", "Resumen estelar de los sucesos de la zona de Los Santos.", 18, 0, 19, 30, "Noticias"),
            TvProgram("ls_7", "lossantostv", "Cultura y Folclor de Nuestra Sierra", "Música campesina, poesía y leyendas de la montaña.", 19, 30, 21, 30, "Cultura"),
            TvProgram("ls_8", "lossantostv", "Serenata en las Alturas", "Música de descanso y noches de montaña.", 21, 30, 6, 0, "Música")
        ),

        "garabitotv" to listOf(
            TvProgram("gb_1", "garabitotv", "Olas del Pacífico Central", "Condiciones marítimas, olas de Jacó, mareas y clima del litoral.", 6, 0, 8, 30, "Turismo"),
            TvProgram("gb_2", "garabitotv", "Revista Jacó y Herradura Hoy", "Emprendimientos turísticos, gastronomía de mar y vida costera.", 8, 30, 11, 0, "Revista"),
            TvProgram("gb_3", "garabitotv", "Garabito Noticias Mediodía", "Noticias locales del cantón de Garabito y Puntarenas.", 11, 0, 12, 30, "Noticias"),
            TvProgram("gb_4", "garabitotv", "Surf, Turismo y Playas de Costa Rica", "Competiciones de surf, aventuras en catamarán y reservas naturales.", 12, 30, 15, 30, "Deportes"),
            TvProgram("gb_5", "garabitotv", "Actualidad Porteña y Comunal", "Desarrollo local, comercio y comunidad de la costa pacífica.", 15, 30, 18, 0, "Comunidad"),
            TvProgram("gb_6", "garabitotv", "Garabito Noticias Central", "Edición central con la información del cantón y el pacífico central.", 18, 0, 19, 30, "Noticias"),
            TvProgram("gb_7", "garabitotv", "Vida Nocturna y Turismo Responsable", "Guía de entretenimiento, cultura y seguridad ciudadana.", 19, 30, 21, 30, "Variedades"),
            TvProgram("gb_8", "garabitotv", "Brisas del Pacífico Madrugada", "Música relajante y paisajes de los atardeceres de Jacó.", 21, 30, 6, 0, "Música")
        ),

        "costaricachannel" to listOf(
            TvProgram("crc_1", "costaricachannel", "Despertar en el Paraíso Verde", "Aves del trópico, sonidos del bosque nuboso y amaneceres en Costa Rica.", 6, 0, 9, 0, "Ecológico"),
            TvProgram("crc_2", "costaricachannel", "Parques Nacionales y Selvas Tropicales", "Expedición a Manuel Antonio, Corcovado, Tortuguero y Monteverde.", 9, 0, 12, 0, "Naturaleza"),
            TvProgram("crc_3", "costaricachannel", "Volcanes, Ríos y Cascadas", "Aventuras en el Volcán Poás, Arenal, Río Celeste y rafting nacional.", 12, 0, 15, 0, "Aventura"),
            TvProgram("crc_4", "costaricachannel", "Playas de Guanacaste y el Caribe", "Aguas cristalinas, arrecifes de Cahuita y playas doradas del pacífico.", 15, 0, 18, 0, "Turismo"),
            TvProgram("crc_5", "costaricachannel", "Fauna y Conservación Costarricense", "Jaguares, perezosos, tortugas marinas y esfuerzos de sostenibilidad.", 18, 0, 21, 0, "Documental"),
            TvProgram("crc_6", "costaricachannel", "Maravillas Naturales de Costa Rica", "El 5% de la biodiversidad del planeta resumido en alta definición.", 21, 0, 6, 0, "Naturaleza")
        ),

        "soyplanchatv" to listOf(
            TvProgram("sp_1", "soyplanchatv", "Baladas del Despertar", "Música suave en español para comenzar la mañana con nostalgia.", 6, 0, 9, 0, "Música"),
            TvProgram("sp_2", "soyplanchatv", "Los Reyes de la Plancha", "Grandes baladas de Juan Gabriel, Amanda Miguel, Rocío Dúrcal y Camilo Sesto.", 9, 0, 12, 0, "Música"),
            TvProgram("sp_3", "soyplanchatv", "Cantando al Mediodía", "Éxitos para cantar a todo pulmón.", 12, 0, 15, 0, "Música"),
            TvProgram("sp_4", "soyplanchatv", "Tardes de Romance y Recuerdos", "Las mejores baladas románticas de los 80s y 90s.", 15, 0, 18, 0, "Música"),
            TvProgram("sp_5", "soyplanchatv", "El Gran Especial de Plancha", "Biografías musicales y conciertos legendarios.", 18, 0, 21, 0, "Música"),
            TvProgram("sp_6", "soyplanchatv", "Serenata Nocturna", "La mejor compañía musical para descansar y cantar.", 21, 0, 6, 0, "Música")
        ),

        "urbanotv" to listOf(
            TvProgram("ur_1", "urbanotv", "Urbano Flow Mañana", "Reggaetón y ritmo para activar el cuerpo.", 6, 0, 9, 0, "Música"),
            TvProgram("ur_2", "urbanotv", "Reggaetón Clásico y Nuevo", "Desde los pioneros hasta los temas número uno del año.", 9, 0, 12, 0, "Música"),
            TvProgram("ur_3", "urbanotv", "El Imperio del Trap Tico", "Nuevas promesas del movimiento urbano de Costa Rica.", 12, 0, 15, 0, "Música"),
            TvProgram("ur_4", "urbanotv", "Batallas de Freestyle y Beats", "Lo mejor del rap improvisado y producciones musicales.", 15, 0, 18, 0, "Música"),
            TvProgram("ur_5", "urbanotv", "Urbano Prime Time", "Los videos oficiales en alta definición con el mejor sonido.", 18, 0, 22, 0, "Música"),
            TvProgram("ur_6", "urbanotv", "Clubbing Urbano Night", "Sesiones ininterrumpidas de DJs costarricenses.", 22, 0, 6, 0, "Música")
        ),

        "gextv" to listOf(
            TvProgram("gex_1", "gextv", "Anime & Gaming News", "Novedades de la industria de videojuegos y estrenos de anime.", 7, 0, 10, 0, "Tecnología"),
            TvProgram("gex_2", "gextv", "Tech & Gadgets Ticos", "Reseñas de smartphones, consolas y accesorios de computación.", 10, 0, 13, 0, "Tecnología"),
            TvProgram("gex_3", "gextv", "Pop Culture Express", "Cine de superhéroes, cómics y series de streaming.", 13, 0, 16, 0, "Cultura Pop"),
            TvProgram("gex_4", "gextv", "Speedruns y Desafíos Gamer", "Partidas épicas y trucos para tus juegos favoritos.", 16, 0, 19, 0, "Videojuegos"),
            TvProgram("gex_5", "gextv", "Gex Music & Chill", "Bandas sonoras de juegos, lofi y synthwave.", 19, 0, 22, 0, "Música"),
            TvProgram("gex_6", "gextv", "Noche Geek", "Debates sobre películas de ciencia ficción y tecnología del futuro.", 22, 0, 7, 0, "Cultura Pop")
        ),

        "vintagemusic" to listOf(
            TvProgram("vm_v1", "vintagemusic", "Clásicos Matinales de los 70s y 80s", "Grandes melodías del pop y rock que marcaron una época.", 6, 0, 9, 0, "Música"),
            TvProgram("vm_v2", "vintagemusic", "La Era Dorada del Pop & Rock", "Videoclips remasterizados de Queen, Michael Jackson, Madonna y The Beatles.", 9, 0, 12, 0, "Música"),
            TvProgram("vm_v3", "vintagemusic", "Rock Clásico y Baladas Anglosajonas", "Guitarras legendarias y coros inolvidables del siglo XX.", 12, 0, 15, 0, "Música"),
            TvProgram("vm_v4", "vintagemusic", "Grandes Conciertos Históricos", "Grabaciones míticas en estadios y teatros de todo el mundo.", 15, 0, 18, 0, "Música"),
            TvProgram("vm_v5", "vintagemusic", "Vintage Prime Time: Hits Inmortales", "Los temas número uno de los años dorados de la música.", 18, 0, 22, 0, "Música"),
            TvProgram("vm_v6", "vintagemusic", "Noches Vintage en Estéreo", "Sonido clásico para terminar la velada con los mejores recuerdos.", 22, 0, 6, 0, "Música")
        ),

        "88stereotv" to listOf(
            TvProgram("st88_1", "88stereotv", "El Mañanero 88 en Vivo", "Transmisión visual de la cabina de radio con música alegre y notas matinales.", 6, 0, 9, 0, "Radio Visual"),
            TvProgram("st88_2", "88stereotv", "Lo Mejor de la Radio Visual", "Mensajes de la audiencia, saludos en vivo y éxitos del momento.", 9, 0, 12, 0, "Radio Visual"),
            TvProgram("st88_3", "88stereotv", "Éxitos Musicales del Momento", "Los temas más sonados en la frecuencia de 88 Stereo en Costa Rica.", 12, 0, 15, 0, "Música"),
            TvProgram("st88_4", "88stereotv", "Tardes de Interacción Sureña", "Participación de oyentes de Pérez Zeledón y todo el país.", 15, 0, 18, 0, "Interactivo"),
            TvProgram("st88_5", "88stereotv", "Especial 88 Stereo en Concierto", "Grandes temas en vivo y mezclas exclusivas de cabina.", 18, 0, 21, 0, "Música"),
            TvProgram("st88_6", "88stereotv", "Música Continua 88 Stereo", "Programación musical ininterrumpida para la noche.", 21, 0, 6, 0, "Música")
        ),

        "sanjosetv" to listOf(
            TvProgram("sj_1", "sanjosetv", "Oración de la Mañana y Laudes", "Comienzo espiritual del día con lecturas bíblicas y cánticos.", 6, 0, 7, 0, "Religión"),
            TvProgram("sj_2", "sanjosetv", "Santa Misa desde la Catedral Metropolitana", "Eucaristía en vivo desde San José, Costa Rica.", 7, 0, 8, 30, "Santa Misa"),
            TvProgram("sj_3", "sanjosetv", "Evangelio y Reflexión Pastoral", "Mensaje del Arzobispo y sacerdotes de la arquidiócesis.", 8, 30, 10, 0, "Reflexión"),
            TvProgram("sj_4", "sanjosetv", "Vida y Familia Católica", "Orientación cristiana para matrimonios y jóvenes.", 10, 0, 12, 0, "Familia"),
            TvProgram("sj_5", "sanjosetv", "El Ángelus y Santa Misa Mediodía", "La oración tradicional del mediodía y eucaristía solemne.", 12, 0, 13, 30, "Santa Misa"),
            TvProgram("sj_6", "sanjosetv", "Formación en la Fe y Doctrina", "Catequesis, encíclicas papales e historia de la Iglesia.", 13, 30, 16, 0, "Educativo"),
            TvProgram("sj_7", "sanjosetv", "Santo Rosario Comunitario", "El rezo del rosario con intenciones por los enfermos y Costa Rica.", 16, 0, 17, 0, "Oración"),
            TvProgram("sj_8", "sanjosetv", "Santa Misa Vespertina de la Catedral", "Eucaristía de la tarde celebrada en la Catedral Metropolitana.", 17, 0, 18, 30, "Santa Misa"),
            TvProgram("sj_9", "sanjosetv", "Testimonios y Evangelización", "Historias de conversión y caridad en las parroquias ticas.", 18, 30, 21, 0, "Testimonios"),
            TvProgram("sj_10", "sanjosetv", "Oración de la Noche y Madrugada en Paz", "Completas y meditación nocturna.", 21, 0, 6, 0, "Religión")
        ),

        "cristovision31" to listOf(
            TvProgram("cv_1", "cristovision31", "Salmos y Alabanzas del Despertar", "Adoración y palabras de ánimo para comenzar la jornada.", 6, 0, 8, 0, "Alabanza"),
            TvProgram("cv_2", "cristovision31", "Prédica y Edificación Espiritual", "Estudio bíblico y conferencias pastorales.", 8, 0, 10, 30, "Enseñanza"),
            TvProgram("cv_3", "cristovision31", "Mujeres de Fe y Hogar Cristiano", "Consejos para madres y fortalecimiento familiar.", 10, 30, 12, 0, "Familia"),
            TvProgram("cv_4", "cristovision31", "Clamor e Intercesión por Costa Rica", "Tiempo de oración por la paz, los enfermos y la nación.", 12, 0, 13, 30, "Oración"),
            TvProgram("cv_5", "cristovision31", "Música Cristiana Contemporánea", "Alabanzas de grupos costarricenses e internacionales.", 13, 30, 16, 0, "Música"),
            TvProgram("cv_6", "cristovision31", "Mensajes de Esperanza", "Predicación y testimonios de sanidad y salvación.", 16, 0, 18, 30, "Prédica"),
            TvProgram("cv_7", "cristovision31", "Gran Culto y Adoración en Directo", "Servicio congregacional con música y predicación en vivo.", 18, 30, 21, 0, "Culto"),
            TvProgram("cv_8", "cristovision31", "Noche de Promesas y Paz", "Música instrumental de adoración y versículos bíblicos.", 21, 0, 6, 0, "Religión")
        ),

        "enlacejuvenil" to listOf(
            TvProgram("ej_1", "enlacejuvenil", "Despierta con Ritmo Cristiano Juvenil", "Música pop y urbana cristiana para empezar con energía.", 6, 0, 9, 0, "Música"),
            TvProgram("ej_2", "enlacejuvenil", "Top Videos Juveniles de Alabanza", "Los videos musicales más pedidos por los jóvenes.", 9, 0, 12, 0, "Música"),
            TvProgram("ej_3", "enlacejuvenil", "Tendencias y Juventud", "Entrevistas a líderes de jóvenes, tecnología y retos virales positivos.", 12, 0, 14, 30, "Juvenil"),
            TvProgram("ej_4", "enlacejuvenil", "Festivales y Conciertos Cristianos", "Las mejores presentaciones de bandas juveniles en vivo.", 14, 30, 17, 30, "Concierto"),
            TvProgram("ej_5", "enlacejuvenil", "Podcast Juvenil y Debates", "Charlas francas sobre relaciones, universidad y fe.", 17, 30, 20, 0, "Podcast"),
            TvProgram("ej_6", "enlacejuvenil", "Adoración Extrema en Concierto", "Noche de alabanza acústica y contemporánea.", 20, 0, 23, 0, "Alabanza"),
            TvProgram("ej_7", "enlacejuvenil", "Urban Gospel y Madrugada", "Música urbana inspiradora durante toda la noche.", 23, 0, 6, 0, "Música")
        ),

        "extremakids" to listOf(
            TvProgram("ek_1", "extremakids", "Canciones Infantiles del Despertar", "Rondas, canciones con animalitos y alegría matinal.", 6, 0, 8, 30, "Infantil"),
            TvProgram("ek_2", "extremakids", "Aventuras Animadas y Cuentos Mágicos", "Dibujos animados educativos con personajes coloridos.", 8, 30, 11, 30, "Animación"),
            TvProgram("ek_3", "extremakids", "Aprendiendo Jugando: Letras y Números", "Programas didácticos para niños de preescolar y primaria.", 11, 30, 13, 30, "Educativo"),
            TvProgram("ek_4", "extremakids", "Marionetas y Fábulas Divertidas", "Historias con valores sobre el respeto y la amistad.", 13, 30, 16, 0, "Infantil"),
            TvProgram("ek_5", "extremakids", "Película Infantil de la Tarde", "Películas de animación y magia para compartir en casa.", 16, 0, 18, 30, "Cine Infantil"),
            TvProgram("ek_6", "extremakids", "Canciones para Dormir y Cuentos", "Cuentos para conciliar el sueño y música tierna.", 18, 30, 21, 0, "Familiar"),
            TvProgram("ek_7", "extremakids", "Sueños Dulces: Melodías Relajantes", "Música de cuna instrumental para la noche de los más pequeños.", 21, 0, 6, 0, "Infantil")
        ),

        "zurquitv" to listOf(
            TvProgram("zq_1", "zurquitv", "Despertar Herediano", "Noticias de San Isidro, San Rafael, Barva y Heredia.", 6, 0, 8, 30, "Regional"),
            TvProgram("zq_2", "zurquitv", "Tradiciones en las Faldas del Zurquí", "Flora, fauna de montaña, lecherías y costumbres heredianas.", 8, 30, 11, 0, "Cultura"),
            TvProgram("zq_3", "zurquitv", "Informativo Heredia Mediodía", "Noticias del Valle Central y la provincia de las flores.", 11, 0, 12, 30, "Noticias"),
            TvProgram("zq_4", "zurquitv", "Senderos del Braulio Carrillo", "Recorridos ecológicos por el parque nacional y bosques nubosos.", 12, 30, 15, 30, "Ecológico"),
            TvProgram("zq_5", "zurquitv", "Cultura y Artesanos del Valle", "Pintura, escultura, mascaradas heredianas y música de cimarrona.", 15, 30, 18, 0, "Tradición"),
            TvProgram("zq_6", "zurquitv", "Noticias Zurquí Central", "Resumen estelar de los sucesos comunales y cantonales.", 18, 0, 19, 30, "Noticias"),
            TvProgram("zq_7", "zurquitv", "Tertulia Herediana", "Espacio de opinión con vecinos y líderes del cantón.", 19, 30, 21, 30, "Opinión"),
            TvProgram("zq_8", "zurquitv", "Noches del Zurquí", "Música instrumental y serenatas costarricenses.", 21, 30, 6, 0, "Música")
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

        // 4. Check general specific schedule across all channels
        val specific = allChannelsBaseSchedules[channelId]
        if (specific != null && specific.isNotEmpty()) {
            return specific
        }

        // 5. Guaranteed continuous tailored schedule
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
            ?: schedule.lastOrNull() // Early morning before the first program started belongs to the overnight program
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
