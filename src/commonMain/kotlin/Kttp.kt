internal expect interface KttpInterface {
    var routeList: ArrayList<Route>
    // Start server, anything after this command won't execute
    open fun run(address: String, port: Int)
}

// Main class for kttp functions
class Kttp : KttpInterface {
    override var routeList = ArrayList<Route>()

    // Add route to list
    fun route(path: String = "all", method: String = "GET", function: (request: Request) -> ResponseObject) {
        routeList.add(Route(path, method, function))
    }
    // Add route with multiple methods
    fun route(path: String = "all", methods: Array<String>, function: (request: Request) -> ResponseObject) {
        for (method in methods) {
            routeList.add(Route(path, method, function))
        }
    }
}