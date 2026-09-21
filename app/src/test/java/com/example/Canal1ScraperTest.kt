package com.example

import com.example.data.epg.Canal1Scraper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Canal1ScraperTest {

    private val sampleHtml = """
        <html>
        <body>
        <table>
            <tr>
                <td>Inicio</td><td>Fin</td><td>Lunes</td><td>Martes</td><td>Miércoles</td><td>Jueves</td><td>Viernes</td><td>Sábado</td><td>Domingo</td><td>Inicio</td><td>Fin</td>
            </tr>
            <tr>
                <td>00:00</td><td>01:00</td><td>SERIE RETRO</td><td>SERIE RETRO</td><td>SERIE RETRO</td><td>SERIE RETRO</td><td>SERIE RETRO</td><td>FIESTA LA TICA</td><td>FIESTA LA TICA</td><td>00:00</td><td>01:00</td>
            </tr>
            <tr>
                <td>01:00</td><td>02:00</td><td>DRAMAS</td><td>DRAMAS</td><td>DRAMAS</td><td>DRAMAS</td><td>DRAMAS</td><td>DRAMAS</td><td>DRAMAS</td><td>01:00</td><td>02:00</td>
            </tr>
            <tr>
                <td>18:00</td><td>18:30</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>COMO HAN PASADO LOS AÑOS</td><td>SERIE RETRO</td><td>18:00</td><td>18:30</td>
            </tr>
            <tr>
                <td>18:30</td><td>19:00</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>COMO HAN PASADO LOS AÑOS</td><td>SERIE RETRO</td><td>18:30</td><td>19:00</td>
            </tr>
            <tr>
                <td>19:30</td><td>20:00</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>PRIMERO NOTICIAS</td><td>MARCELO CASTRO PRESENTA</td><td>SORTEO JPS</td><td>19:30</td><td>20:00</td>
            </tr>
        </table>
        </body>
        </html>
    """.trimIndent()

    @Test
    fun testParseCanal1Monday() {
        val programs = Canal1Scraper.parseCanal1ScheduleForColumn(sampleHtml, 2)
        assertTrue(programs.isNotEmpty())
        assertEquals("Serie Retro", programs[0].title)
        assertEquals(0, programs[0].startHour)
        assertEquals(1, programs[0].endHour)
        assertEquals("Series", programs[0].category)

        assertEquals("Dramas", programs[1].title)
        assertEquals(1, programs[1].startHour)
        assertEquals(2, programs[1].endHour)

        // Verify that 18:00-18:30 and 18:30-19:00 Primero Noticias merged into 18:00-19:00
        val noticias = programs.find { it.title.startsWith("Primero Noticias") && it.startHour == 18 }
        assertTrue("Primero Noticias should be present", noticias != null)
        assertEquals(18, noticias!!.startHour)
        assertEquals(0, noticias.startMinute)
        assertEquals(19, noticias.endHour)
        assertEquals(0, noticias.endMinute)
    }

    @Test
    fun testParseCanal1Sunday() {
        // Column 8 is Domingo
        val programs = Canal1Scraper.parseCanal1ScheduleForColumn(sampleHtml, 8)
        assertTrue(programs.isNotEmpty())
        assertEquals("Fiesta la Tica", programs[0].title)
        assertEquals("Música", programs[0].category)

        val jps = programs.find { it.title.contains("Sorteo Jps", ignoreCase = true) }
        assertTrue("Sorteo JPS should be found", jps != null)
        assertEquals(19, jps!!.startHour)
        assertEquals(30, jps.startMinute)
        assertEquals(20, jps.endHour)
        assertEquals(0, jps.endMinute)
        assertEquals("Especiales", jps.category)
    }
}
