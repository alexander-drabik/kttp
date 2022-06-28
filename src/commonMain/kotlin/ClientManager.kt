// Search for declared routes and execute their functions
internal fun methodResponse(method: String, path: String, routeList: ArrayList<Route>, request: Request): Pair<String, ByteArray> {
    var defaultFunction: (request: Request) -> ResponseObject = func@{
        return@func source("<b>404</b>")
    }
    for (route in routeList) {
        if ((method == route.method || method.lowercase() == "all") && path == route.path) {
            return route.function.invoke(request).response()
        }
        if (route.path == "all") {
            defaultFunction = route.function
        }
    }
    return defaultFunction.invoke(request).response()
}