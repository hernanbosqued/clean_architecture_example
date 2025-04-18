package hernanbosqued.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform