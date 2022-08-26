import java.text.Normalizer

fun String.removeNonSpacingMarks() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")

fun String.isNumeric(): Boolean {
    return toDoubleOrNull() != null
}