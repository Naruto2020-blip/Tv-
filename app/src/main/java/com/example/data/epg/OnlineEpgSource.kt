package com.example.data.epg

/**
 * Represents a public online EPG source.
 */
data class OnlineEpgSource(
    val id: String,
    val name: String,
    val provider: String,
    val url: String,
    val isGzip: Boolean = url.endsWith(".gz")
)

object OnlineEpgSources {
    val allSources: List<OnlineEpgSource> = listOf(
        // 1. EPG.lat - Costa Rica daily XMLTV
        OnlineEpgSource(
            id = "epg_lat_cr",
            name = "EPG.lat Costa Rica",
            provider = "EPG.lat",
            url = "https://epg.lat/files/cr.xml.gz"
        ),
        // 2. EPGShare01 - Costa Rica XMLTV
        OnlineEpgSource(
            id = "epgshare01_cr",
            name = "EPGShare01 (epg_ripper_CR)",
            provider = "EPGShare01",
            url = "https://epgshare01.online/epgshare01/epg_ripper_CR1.xml.gz"
        ),
        // 3. IPTV-org / GitHub - globetvapp Costa Rica
        OnlineEpgSource(
            id = "github_globetvapp",
            name = "IPTV-org / GitHub (Costarica)",
            provider = "GitHub / IPTV-org",
            url = "https://raw.githubusercontent.com/globetvapp/epg/main/Costarica/costarica1.xml"
        ),
        // 4. Open-EPG
        OnlineEpgSource(
            id = "open_epg",
            name = "Open-EPG Costa Rica",
            provider = "Open-EPG",
            url = "https://open-epg.com/files/cr.xml"
        ),
        // 5. TDTChannels
        OnlineEpgSource(
            id = "tdtchannels",
            name = "TDTChannels EPG",
            provider = "TDTChannels",
            url = "https://www.tdtchannels.com/epg/TV.xml.gz"
        )
    )
}
