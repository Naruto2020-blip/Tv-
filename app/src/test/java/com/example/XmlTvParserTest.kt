package com.example

import com.example.data.epg.XmlTvParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.Calendar

class XmlTvParserTest {

    @Test
    fun testChannelIdMatching() {
        assertEquals("teletica7", XmlTvParser.matchChannelId("Canal.7.de.Costa.Rica.(Teletica).cr", "Teletica"))
        assertEquals("canal6repretel", XmlTvParser.matchChannelId("Canal.6.de.Costa.Rica.cr", "Repretel 6"))
        assertEquals("canal11repretel", XmlTvParser.matchChannelId("Canal.11.de.Costa.Rica.cr", "Canal 11"))
        assertEquals("canal4repretel", XmlTvParser.matchChannelId("Canal.4.de.Costa.Rica.cr", "Repretel 4"))
        assertEquals("canal8multimedios", XmlTvParser.matchChannelId("Canal.Multimedios.(Costa.Rica).cr", "Multimedios CR"))
        assertEquals("canal13sinart", XmlTvParser.matchChannelId("Canal.13.de.Costa.Rica.cr", "Trece Costa Rica"))
        assertEquals("extratv42", XmlTvParser.matchChannelId("Canal.Extra.TV.42.de.Costa.Rica.cr", "Extra TV 42"))
        assertEquals("futv", XmlTvParser.matchChannelId("futv.cr", "FUTV"))
        assertEquals("canal14sanvito", XmlTvParser.matchChannelId("coto_brus_tv", "Coto Brus TV Canal 14"))
    }

    @Test
    fun testParseXmlTvDate() {
        val cal = XmlTvParser.parseXmlTvDate("20260913120000 -0600")
        assertNotNull(cal)
        assertEquals(12, cal!!.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
    }
}
