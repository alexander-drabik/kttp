
import platform.posix.*

public actual fun readFile(name: String): String {
    val returnBuffer = StringBuilder()
    val file = fopen("", "r")
    return ""
}

actual fun file(name: String, chunkSize: Int): ResponseObject {
    val file = fopen(name, "r")
    fseek(file, 0, SEEK_END)
    val size = ftell(file)
    fseek(file, 0, SEEK_SET)
    while (ftell(file) <= size) {

    }
    return ResponseObject(arrayListOf(""), "")
}