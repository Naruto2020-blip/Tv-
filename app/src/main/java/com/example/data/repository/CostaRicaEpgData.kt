package com.example.data.repository

import com.example.data.model.TvProgram
import java.util.Calendar
import java.util.TimeZone

object CostaRicaEpgData {

    private val programsByChannel: Map<String, List<TvProgram>> = mapOf(
        "teletica7" to listOf(
            TvProgram("t7_1", "teletica7", "Telenoticias Primera Hora", "El despertar informativo de Costa Rica con cobertura nacional, clima y tránsito.", 5, 45, 8, 0, "Noticias"),
            TvProgram("t7_2", "teletica7", "Buen Día", "La revista matutina líder con consejos de salud, hogar, bienestar, cocina costarricense y motivación.", 8, 0, 10, 0, "Revista"),
            TvProgram("t7_3", "teletica7", "Telenovela Matutina", "Historias apasionantes y los mejores melodramas internacionales.", 10, 0, 11, 30, "Novela"),
            TvProgram("t7_4", "teletica7", "De Boca en Boca", "El acontecer del espectáculo y farándula de Costa Rica con el mejor humor y entrevistas exclusivas.", 11, 30, 12, 0, "Farándula"),
            TvProgram("t7_5", "teletica7", "Telenoticias Edición Meridiana", "El resumen informativo más completo a mitad del día con reportes en vivo de los siete cantones.", 12, 0, 13, 30, "Noticias"),
            TvProgram("t7_6", "teletica7", "Cine de la Tarde", "Grandes películas familiares y estrenos para disfrutar en casa.", 13, 30, 15, 30, "Cine"),
            TvProgram("t7_7", "teletica7", "Laura Sin Censura", "Casos de la vida real analizados con franqueza y debate familiar.", 15, 30, 17, 0, "Entretenimiento"),
            TvProgram("t7_8", "teletica7", "Calle 7 Informativo", "Periodismo ágil y cercano que recorre las calles del país para contar historias cotidianas.", 17, 0, 19, 0, "Noticias"),
            TvProgram("t7_9", "teletica7", "Telenoticias Edición Estelar", "El noticiero estelar con las noticias de mayor impacto político, social y económico de Costa Rica.", 19, 0, 20, 30, "Noticias"),
            TvProgram("t7_10", "teletica7", "7 Días / Noche de Gala", "Periodismo de investigación profunda, reportajes especiales y transmisiones estelares.", 20, 30, 22, 0, "Opinión"),
            TvProgram("t7_11", "teletica7", "Novela de la Noche", "Producciones dramáticas estelares en horario prime time.", 22, 0, 23, 0, "Novela"),
            TvProgram("t7_12", "teletica7", "Telenoticias Medianoche", "El cierre de la jornada con el resumen de última hora y previsiones de mañana.", 23, 0, 23, 45, "Noticias"),
            TvProgram("t7_13", "teletica7", "Cine Nocturno de Estreno", "Películas de acción, suspenso y drama para la madrugada.", 23, 45, 5, 45, "Cine")
        ),

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

        "futv" to listOf(
            TvProgram("fu_1", "futv", "Goles del Fútbol Nacional", "Repaso de todas las anotaciones de la última jornada de la Liga Promerica.", 6, 0, 8, 0, "Deportes"),
            TvProgram("fu_2", "futv", "Conexión Promerica", "Entrevistas con técnicos, capitanes y figuras de los clubes de primera división.", 8, 0, 10, 0, "Deportes"),
            TvProgram("fu_3", "futv", "Clásicos Inolvidables de Costa Rica", "Revive los partidos históricos entre Saprissa, Alajuelense, Herediano y Cartaginés.", 10, 0, 12, 0, "Deportes"),
            TvProgram("fu_4", "futv", "Zona Técnica", "Análisis estratégico y desglose táctico de jugadas clave con entrenadores expertos.", 12, 0, 13, 30, "Deportes"),
            TvProgram("fu_5", "futv", "Mesa Redonda FUTV", "Debate ardiente sobre la selección nacional y el campeonato nacional.", 13, 30, 15, 0, "Deportes"),
            TvProgram("fu_6", "futv", "Previa del Partido en Vivo", "Transmisión desde la cancha con alineaciones y ambiente en las gradas.", 15, 0, 16, 0, "Deportes"),
            TvProgram("fu_7", "futv", "Partido de Primera División en Directo", "Emocionante encuentro del fútbol de Costa Rica con los mejores narradores y comentaristas.", 16, 0, 18, 15, "Deportes"),
            TvProgram("fu_8", "futv", "El Post-Partido / Tercer Tiempo", "Reacciones en caliente, conferencias de prensa y entrevistas exclusivas.", 18, 15, 19, 30, "Deportes"),
            TvProgram("fu_9", "futv", "Pasión Tica: Especial de Fútbol", "Documentales sobre las glorias del fútbol costarricense.", 19, 30, 21, 0, "Deportes"),
            TvProgram("fu_10", "futv", "La Jornada al Detalle", "Estadísticas, tabla de posiciones y tabla del no descenso.", 21, 0, 22, 30, "Deportes"),
            TvProgram("fu_11", "futv", "Goles y Jugadas de la Historia", "Los mejores momentos del balompié tico.", 22, 30, 6, 0, "Deportes")
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

        "canal6repretel" to listOf(
            TvProgram("c6_1", "canal6repretel", "Noticias Repretel Primera Edición", "Las noticias más tempranas para salir informado al trabajo o estudio.", 6, 0, 8, 0, "Noticias"),
            TvProgram("c6_2", "canal6repretel", "Giros de la Mañana", "Revista con consejos para el hogar, nutrición, moda y belleza.", 8, 0, 10, 0, "Revista"),
            TvProgram("c6_3", "canal6repretel", "Cine Familiar Repretel", "Películas para compartir en familia.", 10, 0, 12, 0, "Cine"),
            TvProgram("c6_4", "canal6repretel", "Noticias Repretel Edición Meridiana", "El acontecer nacional con las unidades móviles en vivo.", 12, 0, 13, 30, "Noticias"),
            TvProgram("c6_5", "canal6repretel", "Caso Cerrado con la Dra. Polo", "Juicios dramáticos y conflictos familiares con resoluciones legales.", 13, 30, 15, 0, "Entretenimiento"),
            TvProgram("c6_6", "canal6repretel", "Cine de Acción", "Películas de aventura, superhéroes y suspenso.", 15, 0, 17, 30, "Cine"),
            TvProgram("c6_7", "canal6repretel", "Conexión Fútbol", "Juegos, retos, humor y debate futbolero con reconocidos exfutbolistas ticos.", 17, 30, 19, 0, "Deportes"),
            TvProgram("c6_8", "canal6repretel", "Noticias Repretel Edición Central", "El noticiero nocturno con las noticias más comentadas en el país.", 19, 0, 20, 0, "Noticias"),
            TvProgram("c6_9", "canal6repretel", "Película de Gala", "Grandes superproducciones de Hollywood para cerrar el día.", 20, 0, 22, 0, "Cine"),
            TvProgram("c6_10", "canal6repretel", "Noticias Repretel Nocturna", "El balance definitivo del día.", 22, 0, 23, 0, "Noticias"),
            TvProgram("c6_11", "canal6repretel", "Cine de Medianoche", "Películas y series en la noche.", 23, 0, 6, 0, "Cine")
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
            TvProgram("co_6", "colosaltv", "Colosal Noticias Estelar", "Edición central con cobertura de los 5 cantones del sur.", 18, 0, 19, 30, "Noticias"),
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
            TvProgram("ur_3", "urbanotv", "El Imperio del Trap Tico", "Artistas emergentes de la escena urbana de San José y Limón.", 12, 0, 15, 0, "Música"),
            TvProgram("ur_4", "urbanotv", "Batallas de Freestyle y Beats", "Lo mejor de las competencias de rimas improvisadas.", 15, 0, 18, 0, "Música"),
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
        )
    )

    /**
     * Obtains the current Costa Rica time (UTC-6)
     */
    fun getCurrentCostaRicaTime(): Pair<Int, Int> {
        val tz = TimeZone.getTimeZone("GMT-06:00")
        val calendar = Calendar.getInstance(tz)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }

    /**
     * Gets the full day programming for a channel.
     * If the channel doesn't have a specific custom list, a realistic default schedule is generated.
     */
    fun getScheduleForChannel(channelId: String, channelName: String, categoryName: String): List<TvProgram> {
        val specific = programsByChannel[channelId]
        if (specific != null && specific.isNotEmpty()) {
            return specific
        }

        // Generate tailored dynamic schedule based on channel type
        return listOf(
            TvProgram("${channelId}_1", channelId, "Amanecer en $channelName", "Inicio de transmisiones y música de apertura.", 6, 0, 8, 30, categoryName),
            TvProgram("${channelId}_2", channelId, "Revista Matinal Tica", "Consejos de salud, recetas tradicionales y entrevistas de interés comunitario.", 8, 30, 11, 0, "Revista"),
            TvProgram("${channelId}_3", channelId, "Noticiero Mediodía $channelName", "El resumen de los hechos más destacados en Costa Rica.", 11, 0, 13, 0, "Noticias"),
            TvProgram("${channelId}_4", channelId, "Espacio de Entretenimiento y Cultura", "Programas especiales, documentales y reportajes de nuestras tradiciones.", 13, 0, 16, 0, "Cultura"),
            TvProgram("${channelId}_5", channelId, "Tardes Familiares en Vivo", "Música, llamadas del público y concursos interactivos.", 16, 0, 18, 30, "Variedades"),
            TvProgram("${channelId}_6", channelId, "Edición Central de Noticias", "La información más relevante de la jornada con análisis detallado.", 18, 30, 20, 0, "Noticias"),
            TvProgram("${channelId}_7", channelId, "Franja Estelar en $channelName", "Producciones estelares, debates y programas especiales en horario estelar.", 20, 0, 22, 0, "Especial"),
            TvProgram("${channelId}_8", channelId, "Cierre Informativo de la Noche", "Resumen de los principales acontecimientos del día en Costa Rica.", 22, 0, 23, 30, "Noticias"),
            TvProgram("${channelId}_9", channelId, "Madrugada en Vivo y Música", "Selección musical y repetición de los mejores programas del canal.", 23, 30, 6, 0, "Música")
        )
    }

    /**
     * Gets currently playing program for a channel
     */
    fun getCurrentProgram(channelId: String, channelName: String, categoryName: String): TvProgram {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val (hour, minute) = getCurrentCostaRicaTime()
        return schedule.firstOrNull { it.isLiveAt(hour, minute) }
            ?: schedule.firstOrNull()
            ?: TvProgram(
                id = "${channelId}_live",
                channelId = channelId,
                title = "Transmisión en Vivo: $channelName",
                description = "Señal en directo transmitiendo para todo Costa Rica.",
                startHour = hour,
                startMinute = 0,
                endHour = (hour + 1) % 24,
                endMinute = 0,
                category = categoryName
            )
    }

    /**
     * Gets next upcoming program for a channel
     */
    fun getNextProgram(channelId: String, channelName: String, categoryName: String): TvProgram? {
        val schedule = getScheduleForChannel(channelId, channelName, categoryName)
        val (hour, minute) = getCurrentCostaRicaTime()
        val currentIndex = schedule.indexOfFirst { it.isLiveAt(hour, minute) }
        return if (currentIndex != -1 && currentIndex + 1 < schedule.size) {
            schedule[currentIndex + 1]
        } else {
            schedule.firstOrNull()
        }
    }
}
