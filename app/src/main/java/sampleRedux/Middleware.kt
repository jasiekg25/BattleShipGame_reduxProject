package sampleRedux

import sampleRedux.LoadingSearch as LoadingSearch1

typealias Middleware<State> = (State, Action, Dispatch, Next<State>) -> Action
typealias Next<State> = (State, Action, Dispatch) -> Action

fun searchMiddlewareSample(state: AppState, action: Action, dispatch: Dispatch, next: Next<AppState>): Action {
    return when(action){
        is Search -> {
            SearchApi.search(action.query)
            LoadingSearch1
        }
        else ->
            next(state, action, dispatch)
    }
}
