// Search for declared routes and execute their functions
internal fun methodResponse(method: String, path: String, routeList: ArrayList<Route>): String {
    var defaultFunction: () -> String = func@{
        return@func "<b>404</b>"
    }
    for (route in routeList) {
        if ((method == route.method || method.lowercase() == "all") && path == route.path) {
            return route.function.invoke()
        }
        if (route.path == "all") {
            defaultFunction = route.function
        }
    }
    return defaultFunction.invoke()
}