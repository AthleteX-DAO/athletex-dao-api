package io.athletex.model

import kotlinx.serialization.Serializable
import java.sql.ResultSet

@Serializable
class PlayerPriceHistory(
    val name: String,
    val id: Int,
    val price_history: List<PriceRecord>
) : PlayerStats() {
    companion object {

        fun parsePriceHistory(row: ResultSet): PlayerPriceHistory {
            val records: MutableList<PriceRecord> = mutableListOf()
            var priceHistory: PlayerPriceHistory? = null

            while (row.next()) {
                if (row.isFirst) {
                    priceHistory = PlayerPriceHistory(
                        name = row.getString(Player::name.name),
                        id = row.getInt(Player::id.name),
                        price_history = records
                    )
                }
                records.add(
                    PriceRecord(
                        price = row.getDouble(Player::price.name),
                        timestamp = row.getString(Player::timestamp.name)
                    )
                )
            }
            return priceHistory!!
        }
    }

    @Serializable
    data class PriceRecord(
        val price: Double,
        val timestamp: String,
    )
}