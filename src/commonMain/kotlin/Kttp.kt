internal expect interface KttpInterface {
    var routeList: ArrayList<Route>

    // Start server, anything after this command won't execute
    open fun run(address: String, port: Int)
}

class Kttp : KttpInterface {
    override var routeList = ArrayList<Route>()
    // Add route to list
    fun route(path: String ="all", method: String = "GET", function: () -> String) {
        routeList.add(Route(path, method, function))
    }
}

internal fun methodResponse(method: String, path: String, routeList: ArrayList<Route>): String {
    var defaultFunction: () -> String = func@{
        return@func "<b>404</b>"
    }
    for (route in routeList) {
        if (method == route.method && path == route.path) {
            return route.function.invoke()
        }
        if (route.path == "all") { 
            defaultFunction = route.function
        }
    }
    return defaultFunction.invoke()
}