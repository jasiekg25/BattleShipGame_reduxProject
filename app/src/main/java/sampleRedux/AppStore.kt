package sampleRedux

data class AppState(val searchState: SearchState = SearchState())

class AppStore : SimpleStore<AppState>(
    initialState = AppState(),
    reducers = listOf(::reduceSearchState),
    middleware = listOf(::loggerMiddleware, ::searchMiddleware)
) {

    companion object {
        val instance by lazy {
            AppStore()
        }
    }
}

