package humazed.github.com.kotlinandroidutils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.*


fun LocalDate.toDate(): Date = Date(atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)

fun Date.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()