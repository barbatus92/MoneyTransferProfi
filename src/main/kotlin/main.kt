val currency = "RUB"   // валюта
val amount: Double = 149000.1      // сумма к переводу
val paymentMethod = "Visa"  // метод оплаты
val amountOfPrevTransfers = 0  // сумма предыдущих переводов в этом месяце

const val fixCommissionType1 = 20  // минимальная комиссия по картам Maestro и MasterCard
const val fixCommissionType2 = 35  // минимальная комиссия по картам Visa и МИР
const val startCommissionType1 = 75_000  // порог, после которого исчисляется комиссия по картам Maestro и MasterCard
const val commissionPercentType1 = 0.006 // процентная ставка комиссии по картам Maestro и MasterCard
const val commissionPercentType2 = 0.0075  //  процентная ставка комиссии по картам Visa и МИР
const val monthLimitOfTransferAllCard = 600_000   // лимит по переводу средств за месяц по всем типам карт
const val dayLimitOfTransferAllCard = 150_000  // лимит по переводу средств за день по всем типам карт
const val monthLimitOfTransferOther = 40_000    // лимит по переводу средств за месяц через VK Pay
const val dayLimitOfTransferOther = 15_000   // лимит по переводу средств за день через VK Pay
var totalCommission = 0.0  // сумма комиссии

fun main() {

    when (paymentMethod) {
        "Maestro" -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) calculateCommissionForCardType1() else printError()
        "Mastercard" -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) calculateCommissionForCardType1() else printError()
        "Visa" -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) calculateCommissionForCardType2() else printError()
        "МИР" -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferAllCard && amount < dayLimitOfTransferAllCard) calculateCommissionForCardType2() else printError()
        "VK pay" -> if (amount + amountOfPrevTransfers <= monthLimitOfTransferOther && amount < dayLimitOfTransferOther) calculateCommissionForOther() else printError()
        else -> throw Exception("Такого метода оплаты нет")
    }

    println("Метод оплаты: $paymentMethod \n Сумма перевода: $amount $currency \n Комиссия составит: ${roundOff()} $currency")
}

fun printError() {
    throw Exception("Операция недоступна по превышению лимитов")
}

fun calculateCommissionForCardType1() {
    totalCommission = if (amount > startCommissionType1) amount * commissionPercentType1 + fixCommissionType1
    else 0.0
}

fun calculateCommissionForCardType2() {
    totalCommission = amount * commissionPercentType2 + fixCommissionType2
}

fun calculateCommissionForOther() {
    totalCommission = amount * 0.0
}

fun roundOff(): String {
    return String.format("%.2f", totalCommission)
}


