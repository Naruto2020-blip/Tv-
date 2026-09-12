package com.example.data.model

enum class ChannelCategory(val displayName: String, val iconName: String) {
    TODOS("Todos", "All"),
    NACIONALES("Nacionales", "Tv"),
    NOTICIAS("Noticias", "News"),
    DEPORTES("Deportes", "Sports"),
    ENTRETENIMIENTO("Variedades", "Theater"),
    MUSICA("Música", "Music"),
    REGIONALES("Regionales", "Location"),
    RELIGIOSOS("Religiosos", "Faith")
}

data class TvChannel(
    val id: String,
    val name: String,
    val callsign: String,
    val category: ChannelCategory,
    val streamUrls: List<String>,
    val logoUrl: String,
    val description: String,
    val location: String = "Costa Rica",
    val quality: String = "HD",
    val isFavorite: Boolean = false
)
