
import platform.posix.fopen

public actual fun readFile(name: String): String {
    val returnBuffer = StringBuilder()
    val file = fopen("", "r")
    return ""
}