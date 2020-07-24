package battleship

data class WeighedDirection(val direction: Direction, val len: Int)

enum class Direction {
    HORIZONTAL,
    VERTICAL;

    operator fun times(n: Int): WeighedDirection {
        return WeighedDirection(this, n)
    }
}
