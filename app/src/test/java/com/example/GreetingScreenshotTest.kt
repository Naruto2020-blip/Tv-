package com.example

import com.example.data.repository.CostaRicaEpgData
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class EpgDataTest {

  @Test
  fun testEpgScheduleNotEmpty() {
    val schedule = CostaRicaEpgData.getScheduleForChannel("teletica", "Teletica Canal 7", "General")
    assertTrue(schedule.isNotEmpty())
  }

  @Test
  fun testGetCurrentProgramReturnsValidProgram() {
    val prog = CostaRicaEpgData.getCurrentProgram("teletica", "Teletica Canal 7", "General", 12, 30)
    assertNotNull(prog)
    assertTrue(prog.title.isNotBlank())
  }
}
