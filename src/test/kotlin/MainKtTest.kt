import org.junit.Assert.assertEquals
import org.junit.Test


class MainKtTest {

    @Test
    fun calcCommissionNormalType_3_4() {
        val type = "Visa"
        val amount = 50000
        val amountOfPrevTransfers = 0

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(410, result)
    }

    @Test
    fun calcCommissionLimitType_3_4() {
        val type = "Visa"
        val amount = 50000
        val amountOfPrevTransfers = 650000

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(ERROR_LIMIT, result)
    }

    @Test
    fun calcCommissionFixCommissionType_3_4() {
        val type = "МИР"
        val amount = 20
        val amountOfPrevTransfers = 0

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(fixCommissionType_3_4, result)
    }

    @Test
    fun calcCommissionNormalType_1_2() {
        val type = payType1
        val amount = 50000
        val amountOfPrevTransfers = 0

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(0, result)
    }

    @Test
    fun calcCommissionLimitType_1_2() {
        val type = payType2
        val amount = 50000
        val amountOfPrevTransfers = 650000

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(ERROR_LIMIT, result)
    }

    @Test
    fun calcCommissionFixCommissionType_1_2() {
        val type = "Maestro"
        val amount = 100000
        val amountOfPrevTransfers = 0

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(620, result)
    }

    @Test
    fun calcCommissionFixCommissionType_5() {
        val type = "VK Pay"
        val amount = 1500
        val amountOfPrevTransfers = 0

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(0, result)
    }

    @Test
    fun calcCommissionLimitErrorType_5() {
        val type = "VK Pay"
        val amount = 1500
        val amountOfPrevTransfers = 40000

        val result = calcCommission(type, amountOfPrevTransfers, amount)

        assertEquals(ERROR_LIMIT, result)

    }
}