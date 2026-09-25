package com.example

import com.example.data.repository.CostaRicaChannelsData
import com.example.data.repository.CostaRicaEpgData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CostaRicaEpgScheduleTest {

    @Test
    fun testCanal6HasOnlySignal1() {
        val canal6 = CostaRicaChannelsData.getChannelById("canal6repretel")
        assertTrue("Canal 6 must exist", canal6 != null)
        assertEquals("Canal 6 must only have signal 1", 1, canal6!!.streamUrls.size)
        assertEquals("https://d3xpfoln9sj56.cloudfront.net/ts:abr.m3u8", canal6.streamUrls[0])
    }

    @Test
    fun testVerifiedChannelsHaveEpg() {
        assertTrue(CostaRicaEpgData.hasVerifiedSchedule("teletica7"))
        assertTrue(CostaRicaEpgData.hasVerifiedSchedule("canal6repretel"))
        assertTrue(CostaRicaEpgData.hasVerifiedSchedule("canal8multimedios"))
        assertTrue(CostaRicaEpgData.hasVerifiedSchedule("futv"))
        assertTrue(CostaRicaEpgData.hasVerifiedSchedule("canal1cr"))

        val t7Schedule = CostaRicaEpgData.getScheduleForChannel("teletica7", "Teletica", "Nacionales")
        assertTrue("Teletica must have verified programs", t7Schedule.isNotEmpty())
        val c6Schedule = CostaRicaEpgData.getScheduleForChannel("canal6repretel", "Canal 6", "Nacionales")
        assertTrue("Canal 6 must have verified programs", c6Schedule.isNotEmpty())
    }

    @Test
    fun testUnverifiedChannelsReturnSinProgramacion() {
        assertFalse(CostaRicaEpgData.hasVerifiedSchedule("colosaltv"))
        assertFalse(CostaRicaEpgData.hasVerifiedSchedule("tvsur14"))
        assertFalse(CostaRicaEpgData.hasVerifiedSchedule("cotobrustv"))
        assertFalse(CostaRicaEpgData.hasVerifiedSchedule("extremakids"))
        assertFalse(CostaRicaEpgData.hasVerifiedSchedule("soyplanchatv"))

        val colosalSchedule = CostaRicaEpgData.getScheduleForChannel("colosaltv", "Colosal TV", "Regionales")
        assertTrue("Colosal TV schedule should be empty to prevent incorrect EPG", colosalSchedule.isEmpty())

        val currentProg = CostaRicaEpgData.getCurrentProgram("colosaltv", "Colosal TV", "Regionales")
        assertEquals(CostaRicaEpgData.NO_PROGRAMMING_TITLE, currentProg.title)
        assertTrue(CostaRicaEpgData.isNoProgramming(currentProg))

        val nextProg = CostaRicaEpgData.getNextProgram("colosaltv", "Colosal TV", "Regionales")
        assertNull("Next program should be null when no schedule exists", nextProg)
    }
}
