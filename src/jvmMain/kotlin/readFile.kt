public actual fun readFile(name: String): String {
    val reader = {}.javaClass.getResourceAsStream(name).bufferedReader()
    return reader.readText()
}