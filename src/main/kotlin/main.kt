val currency = "RUB"   // валюта
const val payType1: String = "Maestro"
const val payType2: String = "MasterCard"
const val payType3: String = "Visa"
const val payType4: String = "МИР"
const val payType5: String = "VK Pay"

const val fixCommissionType_1_2 = 20  // минимальная комиссия по картам Maestro и MasterCard
const val fixCommissionType_3_4 = 35  // минимальная комиссия по картам Visa и МИР
const val startCommissionType_1_2 = 75_000  // порог, после которого исчисляется комиссия по картам Maestro и MasterCard
const val commissionPercentType_1_2 = 0.006 // процентная ставка комиссии по картам Maestro и MasterCard
const val commissionPercentType_3_4 = 0.0075  //  процентная ставка комиссии по картам Visa и МИР
const val monthLimitOfTransferAllCard = 600_000   // лимит по переводу средств за месяц по всем типам карт
const val dayLimitOfTransferAllCard = 150_000  // лимит по переводу средств за день по всем типам карт
const val monthLimitOfTransferType_5 = 40_000    // лимит по переводу средств за месяц через VK Pay
const val dayLimitOfTransferType_5 = 15_000   // лимит по переводу средств за день через VK Pay
const val ERROR_LIMIT = -1 //ошибка по превышению лимитов по карте (разового и накопительного за месяц)

fun main() {
    printInfo(payType5, 0, 5444)

}
fun printInfo(type: String, amountOfPrevTransfers: Int, amount: Int) {
    println(
        "Метод оплаты: $type \n Сумма перевода: $amount $currency \n Комиссия составит: " + calcCommission(
            type,
            amountOfPrevTransfers,
            amount
        ) +" $currency"
    )
    println("----------------------------------------------------------")
}

fun calcCommission(
    type: String,
    amountOfPrevTransfers: Int,
    amount: Int
): Int {
    return when (type) {
        payType1, payType2 -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) {
            if (amount <= startCommissionType_1_2) 0
            else (amount * commissionPercentType_1_2 + fixCommissionType_1_2).toInt()
        } else {
            ERROR_LIMIT
        }
        payType3, payType4 -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) {
            if (amount * commissionPercentType_3_4 < fixCommissionType_3_4) fixCommissionType_3_4
            else (amount * commissionPercentType_3_4 + fixCommissionType_3_4).toInt()
        } else {
            ERROR_LIMIT
        }
        payType5 -> {
            if (amount + amountOfPrevTransfers <= monthLimitOfTransferType_5 && amount < dayLimitOfTransferType_5) 0
            else ERROR_LIMIT
        }
        else -> {
            return 0
        }
    }
}
