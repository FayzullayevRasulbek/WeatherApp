package uz.thespecialone.weather.data

object Data {
    private val CITIES_ID = listOf(
        1484839,
        1514588,
        1513157,
        1514019,
        1513966,
        1513886,
        1216311,
        1217662,
        1216265,
        1538229
    )

    internal fun getGroupsId(): String {
        var s = ""
        for (i in 0 until  CITIES_ID.size-1) {
            s += "${CITIES_ID[i]},"
        }

        s += "${CITIES_ID[CITIES_ID.size - 1]}"
        return s
    }

}