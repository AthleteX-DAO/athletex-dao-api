package io.athletex.services

import io.athletex.routes.payloads.PlayerIds

interface PlayerService {
    suspend fun getAllPlayers(): List<Any>
    suspend fun getPlayersByTeam(team: String): List<Any>
    suspend fun getPlayersByPosition(position: String): List<Any>
    suspend fun getPlayersOnTeamByPosition(position: String, team: String): List<Any>
    suspend fun getPlayersById(playerIds: PlayerIds): List<Any>
}