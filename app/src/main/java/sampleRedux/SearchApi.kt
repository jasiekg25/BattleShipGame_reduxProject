package sampleRedux

fun searchMiddleware(state: AppState, action: Action, dispatch: Dispatch, next: Next<AppState>): Action {
    return when(action) {
        is Search -> {
            SearchApi.search(query, dispatch)
            LoadingSearch
        }
        else -> this
    }
}

class SearchApi {
    fun search(query: String, dispatch: Dispatch){
        doAsync {
            val result = magicSearch.search(query)
            uiThread {
                if(!result.success){
                    dispatch(SearchError)
                } else {
                    dispatch(SearchResultsLoaded(result))
                }
            }
        }
    }
}
