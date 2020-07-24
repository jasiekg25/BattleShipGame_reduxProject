package battleship

data class Ship(
    val id: Int,
    val startPoint: Point,
    val size: Int,
    val direction: Direction,
    val hits: Set<Point> = setOf()
) {
    val endPoint = startPoint + direction * (size - 1)
    val destroyed = (hits.size == size)

    operator fun contains(p: Point): Boolean {
        return when (direction) {
            Direction.HORIZONTAL -> startPoint.row == p.row && startPoint.col <= p.col && endPoint.col >= p.col
            Direction.VERTICAL -> startPoint.col == p.col && startPoint.row <= p.row && endPoint.row >= p.row
        }
    }

    operator fun contains(other: Ship): Boolean {
        if (other.direction == this.direction) {
            return other.startPoint in this
                    || other.endPoint in this
        }
        val vertical = if (other.direction == Direction.VERTICAL) other else this
        val horizontal = if (other.direction == Direction.HORIZONTAL) other else this

        return horizontal.startPoint.row in (vertical.startPoint.row..vertical.endPoint.row)
                && vertical.startPoint.col in (horizontal.startPoint.row..horizontal.endPoint.row)
    }
}
