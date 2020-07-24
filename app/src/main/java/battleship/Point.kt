package battleship

data class Point(
    val col: Int,
    val row: Int
){
    operator fun plus(wDirection: WeighedDirection): Point{
        return when(wDirection.direction){
            Direction.HORIZONTAL -> Point(col + wDirection.len, row)
            Direction.VERTICAL -> Point(col, row + wDirection.len)
        }
    }
}
