// This class holds every information, that needs to be known, to create new route
class Route(var path: String, var method: String, var function: () -> String)