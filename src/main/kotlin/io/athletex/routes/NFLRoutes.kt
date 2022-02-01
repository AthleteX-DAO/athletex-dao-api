package io.athletex.routes

import io.athletex.services.NFLPlayerService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NullPointerException

fun Application.nflPlayers(nflPlayerService: NFLPlayerService) {
    routing {
        route("/nfl") {
            get("/players") {
                val team: String = call.request.queryParameters["team"] ?: ""
                val position: String = call.request.queryParameters["position"] ?: ""
                try {
                    when {
                        team.isNotBlank() && position.isBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersByTeam(team))
                        }
                        team.isBlank() && position.isNotBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersByPosition(position))
                        }
                        team.isNotBlank() && position.isNotBlank() -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getPlayersOnTeamByPosition(position, team))
                        }
                        else -> {
                            call.respond(HttpStatusCode.OK, nflPlayerService.getAllPlayers())
                        }
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }
            
            get("/players/{id}") {
                try {
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerById(playerId))
                }catch (e: NullPointerException) {
                    call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
                }catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }

            get("/players/{id}/history") {
                try {
                    val playerId: Int = call.parameters["id"]!!.toInt()
                    call.respond(HttpStatusCode.OK, nflPlayerService.getPlayerHistory(playerId))
                }catch (e: NullPointerException) {
                    call.respond(HttpStatusCode.NotFound, "Player by this id is not found")
                }catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "${e.message}")
                }
            }
        }
    }
}
