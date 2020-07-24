package sampleRedux

data class SearchState(val loading: Boolean = false, val query: String = "", val movies: List<Movie> = listOf())
object ClearSearch: Action
data class Search(val query: String): Action
data class LoadingSearch(val query: String): Action
data class SearchResultsLoaded(val query: String, val movies: List<Movie>):
    Action

fun reduceSearchState(state: AppState, action: Action): AppState {
    return when(action){
        is ClearSearch -> state.copy(searchState = SearchState())
        is LoadingSearch -> state.copy(searchState = SearchState(
            loading = true,
            query = action.query
        )
        )
        is SearchResultsLoaded -> state.copy(searchState = SearchState(
            query = action.query,
            loading = false,
            movies = action.movies
        )
        )
        else -> state
    }
}
