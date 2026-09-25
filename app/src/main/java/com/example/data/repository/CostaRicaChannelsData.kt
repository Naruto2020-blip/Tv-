package com.example.data.repository

import com.example.data.model.ChannelCategory
import com.example.data.model.TvChannel

object CostaRicaChannelsData {

    val channels: List<TvChannel> = listOf(
        TvChannel(
            id = "teletica7",
            name = "Teletica Canal 7",
            callsign = "Canal 7",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "http://190.61.101.11:7050/play/a07t/index.m3u8",
                "https://59ef525c24caa.streamlock.net/teletica/teletica/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/ctnvE3c.png",
            description = "Principal canal de televisión de Costa Rica. Transmite Telenoticias, Buen Día, De Boca en Boca y producciones estelares.",
            location = "San José",
            quality = "1080p HD"
        ),
        TvChannel(
            id = "canal8multimedios",
            name = "Canal 8 Multimedios",
            callsign = "Canal 8",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "https://mdstrm.com/live-stream-playlist/5a7b1e63a8da282c34d65445.m3u8"
            ),
            logoUrl = "https://i.imgur.com/nefPi2Y.png",
            description = "Información continua con Telediario Costa Rica, Alerta 8, fútbol costarricense y entretenimiento diario.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "opacanal38",
            name = "¡OPA! Canal 38",
            callsign = "Canal 38",
            category = ChannelCategory.ENTRETENIMIENTO,
            streamUrls = listOf(
                "https://5f1af61612fb5.streamlock.net/genteopa/smil:genteopa.smil/playlist.m3u8?wowzatokenendtime=1790211874&wowzatokenhash=ILPRDt5LIKM_soLg4bda6Ouj7bil9EPv9gIJJjZiMwo%3D",
                "https://5f1af61612fb5.streamlock.net/genteopa/videogenteopa_720p/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/300c1ZH.png",
            description = "La nueva televisión de Costa Rica con Central Noticias, Está Pasando, Equilibrio con Yaxún, A Doble Nudo, Alma de Mujer y Miss Universe.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "futv",
            name = "FUTV Costa Rica",
            callsign = "FUTV",
            category = ChannelCategory.DEPORTES,
            streamUrls = listOf(
                "https://futvcr.com/wp-content/uploads/2026/09/RESUMEN-FECHA-JORNADA-9-A-26.mp4",
                "https://futvcr.com/wp-content/uploads/2026/09/RESUMEN-LDA-5-0-ISC.mp4"
            ),
            logoUrl = "https://i.imgur.com/f8BkLql.png",
            description = "El canal oficial del fútbol costarricense. Partidos exclusivos de la Liga Promerica, previas y análisis deportivo de primera.",
            location = "Costa Rica",
            quality = "720p HD"
        ),
        TvChannel(
            id = "extratv42",
            name = "Extra TV 42",
            callsign = "Canal 42",
            category = ChannelCategory.NOTICIAS,
            streamUrls = listOf(
                "https://d2n1wzrr0aogf5.cloudfront.net/ts:abr.m3u8"
            ),
            logoUrl = "https://i.imgur.com/jjTodXj.png",
            description = "El canal del pueblo con Noticias Extra, sucesos al minuto, opinión ciudadana y cobertura en todo el territorio nacional.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "canal6repretel",
            name = "Repretel Canal 6",
            callsign = "Canal 6",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "https://d3xpfoln9sj56.cloudfront.net/ts:abr.m3u8"
            ),
            logoUrl = "https://i.imgur.com/nfKJftW.png",
            description = "Noticias Repretel, revista matutina Giros, Conexión Fútbol, series estelares y grandes eventos deportivos.",
            location = "La Uruca, San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "canal11repretel",
            name = "Repretel Canal 11",
            callsign = "Canal 11",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "https://d3mstgwjcwqz1y.cloudfront.net/ts:abr.m3u8"
            ),
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Repretel_11_logo.png/960px-Repretel_11_logo.png",
            description = "Noticias NC Once, Informe 11 Las Historias, producciones ticas y clásicos de la pantalla nacional.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "canal4repretel",
            name = "Repretel Canal 4",
            callsign = "Canal 4",
            category = ChannelCategory.ENTRETENIMIENTO,
            streamUrls = listOf(
                "https://dlhn7gdemxofz.cloudfront.net/ts:abr.m3u8"
            ),
            logoUrl = "https://i.imgur.com/5arZEgO.png",
            description = "Programación infantil, cine familiar, comedias juveniles y series de entretenimiento para todo el hogar.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "canal13sinart",
            name = "Trece Costa Rica (SINART)",
            callsign = "Canal 13",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "https://geo.dailymotion.com/player/xcdvm.html?video=x7vh8g3",
                "https://www.dailymotion.com/embed/video/x7vh8g3?autoplay=1&mute=0",
                "https://sinartdigital.com/envivo-canaltrece"
            ),
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Trece_Costa_Rica_Televisi%C3%B3n_logo.svg/800px-Trece_Costa_Rica_Televisi%C3%B3n_logo.svg.png",
            description = "Televisión pública de Costa Rica con Trece Noticias, Su Lado Positivo, Materia Prima, Consulta en Directo, Arte 13 y Deportivas del 13.",
            location = "La Uruca, San José",
            quality = "1080p HD"
        ),
        TvChannel(
            id = "vmlatino",
            name = "VM Latino",
            callsign = "VM Latino",
            category = ChannelCategory.MUSICA,
            streamUrls = listOf(
                "https://59ef525c24caa.streamlock.net/vmtv/vmlatino/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/Dvo1b82.png",
            description = "El canal de la música en Costa Rica. Éxitos urbanos, pop latino, videoclips en rotación continua y VJs en vivo.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "colosaltv",
            name = "Colosal TV Canal 54",
            callsign = "Canal 54",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://5eac7b031d945.streamlock.net/COLOSAL/COLOSAL/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/0DZB3eB.png",
            description = "La voz de la Zona Sur costarricense. Noticias de Corredores, Golfito, Osa y todo el pacífico sur.",
            location = "Ciudad Neily, Zona Sur",
            quality = "720p HD"
        ),
        TvChannel(
            id = "tvsur14",
            name = "TV Sur Canal 14",
            callsign = "Canal 14 Sur",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://k20.usastreams.com:8081/tvsur/index.m3u8"
            ),
            logoUrl = "https://i.imgur.com/OpHYYZA.png",
            description = "Televisión de Pérez Zeledón y la Región Brunca. Información comunitaria, agricultura, deportes y tradiciones del valle.",
            location = "Pérez Zeledón",
            quality = "1080p Full HD"
        ),
        TvChannel(
            id = "canal14sancarlos",
            name = "Canal 14 San Carlos (TVN)",
            callsign = "Canal 14 Norte",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "http://tvn.obix.tv:1935/TVN/CH14.stream_720p/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/1aNEo6U.png",
            description = "Canal cooperativo de la Zona Norte: San Carlos, Los Chiles, Upala y Guatuso. Noticias agropecuarias y comunales.",
            location = "Ciudad Quesada, San Carlos",
            quality = "720p HD"
        ),
        TvChannel(
            id = "soyplanchatv",
            name = "SOY Plancha TV",
            callsign = "Plancha TV",
            category = ChannelCategory.MUSICA,
            streamUrls = listOf(
                "https://59ef525c24caa.streamlock.net/vmtv/soyplancha/playlist.m3u8"
            ),
            logoUrl = "https://i.ibb.co/P4kpMgk/1689344714578.jpg",
            description = "Dedicado a la música del recuerdo, baladas clásicas y grandes canciones de plancha que marcaron historia.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "urbanotv",
            name = "Urbano TV Costa Rica",
            callsign = "Urbano TV",
            category = ChannelCategory.MUSICA,
            streamUrls = listOf(
                "https://59ef525c24caa.streamlock.net/tvurbano/tvurbano/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/KVVU9PI.png",
            description = "Ritmos de reggaetón, dancehall, trap, rap tico y producciones de artistas independientes de Costa Rica.",
            location = "Costa Rica",
            quality = "720p HD"
        ),
        TvChannel(
            id = "gextv",
            name = "Gex TV",
            callsign = "Gex TV",
            category = ChannelCategory.ENTRETENIMIENTO,
            streamUrls = listOf(
                "https://live20.bozztv.com/akamaissh101/ssh101/gextvaccess/playlist.m3u8"
            ),
            logoUrl = "https://gextv.com/LOGO-WHITE.png",
            description = "Canal juvenil enfocado en cultura pop, conciertos, videojuegos, tecnología y entretenimiento contemporáneo.",
            location = "San José",
            quality = "1080p Full HD"
        ),
        TvChannel(
            id = "vintagemusic",
            name = "Vintage Music TV",
            callsign = "Vintage TV",
            category = ChannelCategory.MUSICA,
            streamUrls = listOf(
                "https://59ef525c24caa.streamlock.net/vmtv/tvvintage/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/KImqYnN.jpeg",
            description = "Los grandes clásicos del rock, pop de los 70s, 80s y 90s con la mejor fidelidad sonora y visual.",
            location = "Costa Rica",
            quality = "720p HD"
        ),
        TvChannel(
            id = "canal1cr",
            name = "Canal 1 Costa Rica",
            callsign = "Canal 1",
            category = ChannelCategory.NACIONALES,
            streamUrls = listOf(
                "https://vid.canal1cr.com:3424/multi_live/play_720.m3u8"
            ),
            logoUrl = "https://i.ibb.co/KhNS8Dy/cropped-logoo-1.png",
            description = "Información alternativa, debates políticos de coyuntura costarricense y revistas culturales.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "lossantostv",
            name = "Los Santos TV",
            callsign = "LSTV",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://tiquiciatv.com/hls/lstv.m3u8"
            ),
            logoUrl = "https://i.imgur.com/5xw86wu.png",
            description = "Canal regional de la zona de Tarrazú, Dota y León Cortés. Cultura cafetalera y vida campesina.",
            location = "Zona de Los Santos",
            quality = "1080i HD"
        ),
        TvChannel(
            id = "garabitotv",
            name = "Garabito TV",
            callsign = "GTV",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://59ef525c24caa.streamlock.net/garabitoTV/garabitotv/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/sAYNKJ8.png",
            description = "Canal del Pacífico Central: Jacó, Herradura y Puntarenas. Turismo de playa, surf y noticias locales.",
            location = "Jacó, Garabito",
            quality = "720p HD"
        ),
        TvChannel(
            id = "costaricachannel",
            name = "Costa Rica Channel",
            callsign = "CR Channel",
            category = ChannelCategory.ENTRETENIMIENTO,
            streamUrls = listOf(
                "https://video0.rogohosting.com:19360/8006/8006.m3u8"
            ),
            logoUrl = "https://i.imgur.com/N4pFVVF.png",
            description = "Promoviendo el turismo, ecología y belleza natural de Costa Rica: volcanes, parques nacionales y biodiversidad.",
            location = "Costa Rica",
            quality = "576p SD"
        ),
        TvChannel(
            id = "enlacejuvenil",
            name = "Enlace Juvenil (EJTV)",
            callsign = "EJTV",
            category = ChannelCategory.RELIGIOSOS,
            streamUrls = listOf(
                "https://livecdnusa.enlace.plus/ejtv/smil:ejtv-hd.smil/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/GfpPQ5D.png",
            description = "Música cristiana juvenil, entrevistas con cantantes y mensajes de edificación para toda la juventud.",
            location = "San José",
            quality = "1080p Full HD"
        ),
        TvChannel(
            id = "sanjosetv",
            name = "San José TV",
            callsign = "SJ TV",
            category = ChannelCategory.RELIGIOSOS,
            streamUrls = listOf(
                "https://rtmp.info/sanjosetv/envivo/playlist.m3u8"
            ),
            logoUrl = "https://i.ibb.co/mbB8hqL/blanco-crop.png",
            description = "Misas de la Catedral Metropolitana de San José, reflexión espiritual y actividades de la arquidiócesis.",
            location = "San José",
            quality = "1080p Full HD"
        ),
        TvChannel(
            id = "cristovision31",
            name = "Cristo Visión Canal 31",
            callsign = "Canal 31",
            category = ChannelCategory.RELIGIOSOS,
            streamUrls = listOf(
                "https://3a310f6ec721e6b362fcd22772b57f36a2fe6bb2.tabeelcr.com:8080/cristovision31/iptv.m3u8"
            ),
            logoUrl = "https://i.imgur.com/CFXZ8j0.png",
            description = "Espacio cristiano familiar con prédicas, adoración y contenidos de esperanza para el pueblo costarricense.",
            location = "San José",
            quality = "720p HD"
        ),
        TvChannel(
            id = "cotobrustv",
            name = "Coto Brus TV",
            callsign = "Canal 5",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://cloudvideo.servers10.com:8081/8030/index.m3u8"
            ),
            logoUrl = "https://i.ibb.co/vJZqrFY/a4ed367c69c23110d458a332af95be0be64566f288ccb5f261689344e6ba5e0d.png",
            description = "Transmisión oficial de Coto Brus TV. Noticias locales de San Vito, cultura cafetalera, información comunal y frontera sur de Costa Rica.",
            location = "Coto Brus, Puntarenas",
            quality = "720p HD"
        ),
        TvChannel(
            id = "extremakids",
            name = "Extrema Kids TV",
            callsign = "Extrema Kids",
            category = ChannelCategory.ENTRETENIMIENTO,
            streamUrls = listOf(
                "https://627bb251f23c7.streamlock.net:444/ExtremaKids/ExtremaKids/playlist.m3u8"
            ),
            logoUrl = "https://www.vivalivetv.com/public/files/shows/0/1/3027-294x165-FFFFFF.jpg",
            description = "Contenido animado infantil, valores y canciones divertidas para los más pequeños de la casa.",
            location = "Costa Rica",
            quality = "720p HD"
        ),
        TvChannel(
            id = "88stereotv",
            name = "88 Stereo TV",
            callsign = "88 Stereo",
            category = ChannelCategory.MUSICA,
            streamUrls = listOf(
                "http://k3.usastreams.com/CableLatino/88stereo/playlist.m3u8"
            ),
            logoUrl = "https://i.imgur.com/i3YwORV.png",
            description = "Transmisión visual en vivo de la popular emisora musical 88 Stereo con los mejores éxitos radiales del momento.",
            location = "Pérez Zeledón",
            quality = "720p HD"
        ),
        TvChannel(
            id = "zurquitv",
            name = "Zurquí TV",
            callsign = "Zurquí TV",
            category = ChannelCategory.REGIONALES,
            streamUrls = listOf(
                "https://videoserver.tmcreativos.com:19360/gesfnvpamn/gesfnvpamn.m3u8"
            ),
            logoUrl = "https://i.imgur.com/XvktUBB.jpg",
            description = "Transmisiones desde San Isidro de Heredia y el Valle Central con programas locales y culturales.",
            location = "Heredia",
            quality = "720p HD"
        )
    )

    fun getChannelById(id: String): TvChannel? {
        return channels.firstOrNull { it.id == id }
    }
}
