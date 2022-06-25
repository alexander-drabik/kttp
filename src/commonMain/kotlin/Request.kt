class Request(
    var pathVariables: HashMap<String, String> = HashMap(),
    var headers: HashMap<String, String> = HashMap()
) {
    // Returns list of path variables from given names
    fun getPathVariables(vararg names: String): ArrayList<String?> {
        val variables = ArrayList<String?>()
        for (name in names) variables.add(pathVariables[name])
        return variables
    }
}