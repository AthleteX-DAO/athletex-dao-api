package io.athletex.client.apis.stats

import io.athletex.client.Client
import io.athletex.model.mlb.MLBPlayer
import io.athletex.services.MLBPlayerService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json



internal class StatsApiTest {

    private val appConfiguration: HoconApplicationConfig = mockk()
    private val mockHttpClient: HttpClient = mockk()
    private val mockMlbService: MLBPlayerService = mockk()
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Before
    fun setUp() {
        mockkObject(Client)
        val configValue: ApplicationConfigValue = mockk() {
            every { getString() } returns "Test Config Value"
        }
        every { appConfiguration.property(any()) } returns configValue
        every { Client.httpClient } returns mockHttpClient

    }

    @Test
    fun `when request returns successfully, then insert stats into database`(): Unit =  runBlocking {
        val response = json.decodeFromString<List<MLBPlayer>>(sampleMlbStatsJson)
        val mockHttpResponse: HttpResponse = mockk {
            every { status } returns HttpStatusCode.OK
            coEvery { body<List<MLBPlayer>>() } returns response
        }
        coEvery { mockHttpClient.request(any<String>(), any()) } returns mockHttpResponse
        syncMlbStatsToDb(appConfiguration, mockMlbService)
        coVerify {
            mockMlbService.insertStatsUpdate(any())
        }
    }

}