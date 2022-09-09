package io.athletex.client.apis.stats

import io.athletex.client.Client
import io.athletex.client.formulas.mlbPositionalAdjustments
import io.athletex.services.MLBPlayerService
import io.athletex.services.NFLPlayerService
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.utils.io.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue


internal class StatsApiTest {

    private val appConfiguration: HoconApplicationConfig = mockk()
    private val mockMlbService: MLBPlayerService = mockk()
    private val mlbPlayerResponse = this::class.java.classLoader
        .getResource("mlb_player_response.json")?.readText()
        
    private val mockNflService: NFLPlayerService = mockk()
    private val nflPlayerResponse = this::class.java.classLoader
        .getResource("nfl_player_response.json")?.readText()


    // MLB Test

    @Before
    fun setUp() {
        mockkObject(Client)
        val configValue: ApplicationConfigValue = mockk {
            every { getString() } returns "MLB_API_KEY"
        }
        every { appConfiguration.property(any()) } returns configValue
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/v3/mlb/stats/json/PlayerSeasonStats/2022" -> {
                    respond(
                        content = ByteReadChannel("""$mlbPlayerResponse"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                "/v3/nfl/stats/json/PlayerGameStatsByWeek/2022PRE/3" -> {
                    respond(
                        content = ByteReadChannel("""$nflPlayerResponse"""),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> {
                    respond(
                        content = ByteReadChannel("""{}"""),
                        status = HttpStatusCode.NotFound,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }

            }

        }
        val apiClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }

        }
        every { Client.httpClient } returns apiClient
        every { mockMlbService.insertPlayers(any()) } just Runs
        every { mockNflService.insertPlayers(any()) } just Runs
    }

    @Test
    fun `when request returns successfully, then insert stats into database`(): Unit = runBlocking {
        syncMLBStatsToDb(mockMlbService, appConfiguration)
        verify {
            mockMlbService.insertPlayers(withArg {
                assertTrue { it.isNotEmpty() }
                it.forEach { item ->
                    assertTrue { !item.price.isNaN() }
                    if (mlbPositionalAdjustments.containsKey(item.position)) {
                        assertTrue { item.price >= 0 }
                    }
                }
            })
        }

    }


    @Test
    fun `when request returns successfully, then insert stats into database NFL`(): Unit = runBlocking {
        syncNFLStatsToDb(mockNflService, appConfiguration)
        verify {
            mockNflService.insertPlayers(withArg {
                assertTrue { it.isNotEmpty() }
                it.forEach { item ->
                    assertTrue { !item.price.isNaN() }
                    assertTrue { item.price >= 0 }
                }
            })
        }

    }
}