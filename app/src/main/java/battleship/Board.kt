package battleship

data class Board(
    val id: Int,
    val user: User,
    val width: Int, val height: Int,
    val ships: List<Ship> = listOf(),
    val hits: Set<Point> = setOf(),
    val misses: Set<Point> = setOf(),
    val opponentHits: Set<Point> = setOf(),
    val opponentMisses: Set<Point> = setOf()
) {
    val activeShips: List<Int> = ships.filter { !it.destroyed }.map { it.id }
    val destroyedShips: List<Int> = ships.filter { it.destroyed }.map { it.id }
    val lost = (ships.isNotEmpty() && activeShips.isEmpty())

    fun fits(ship: Ship) = ship.startPoint in this && ship.endPoint in this
    fun isOverlap(ship: Ship): Boolean {
        return ships.any { ship in it }
    }

    operator fun contains(p: Point): Boolean {
        return p.col >= 0 && p.row >= 0
                && p.col < width && p.row < height
    }

    //  Extension functions with List<Ship>

    //  check if any of the ships are at the given point
    fun List<Ship>.contains(p: Point): Boolean {
        forEach {
            if (p in it) {
                return true
            }
        }
        return false
    }

    //  check the direction of the ship at the given point
    fun List<Ship>.getShipDirection(p: Point): Direction? {
        forEach {
            if (p in it) {
                return it.direction
            }
        }
        return null
    }

    //    update the ship and return new list
    fun List<Ship>.update(ship: Ship): List<Ship> {
        var index = -1
        forEachIndexed { i, s ->
            if (s.id == ship.id){
                index = i
                return@forEachIndexed
            }
        }
        if(index != -1){
            val mutable = ArrayList(this)
            mutable.removeAt(index)
            mutable.add(index, ship)
            return mutable
        }

        return this
    }


}

