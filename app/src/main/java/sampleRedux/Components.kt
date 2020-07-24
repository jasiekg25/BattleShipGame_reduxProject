package sampleRedux


interface Action
typealias Dispatch = (Action) -> Unit
typealias Reducer<State> = (State, Action) -> State
typealias Subscription<State> = (State, Dispatch) -> Unit
typealias Unsubscribe = () -> Unit

interface Store<State> {
    fun getState() : State
    fun dispatch(action: Action)
    fun subscribe(subscription: Subscription<State>): Unsubscribe
    fun unsubscribe(subscribe: Subscription<State>)
}



