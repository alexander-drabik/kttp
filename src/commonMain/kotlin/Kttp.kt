expect interface KttpInterface {
    var routeList: ArrayList<Route>

    // Start server, anything after this command won't execute
    public open fun run(address: String, port: Int)
}

class Kttp : KttpInterface {
    override var routeList = ArrayList<Route>()
    // Add route to list
    public fun route(path: String, method: String, function: () -> String) {
        routeList.add(Route(path, method, function))
    }
}