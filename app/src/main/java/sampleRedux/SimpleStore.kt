package sampleRedux

abstract class SimpleStore<State>(
    private val initialState: State,
    private val reducers: List<Reducer<State>>,
    private val middleware: List<Middleware<State>>
) : Store<State> {
    private var currentState: State = initialState
    private val subscriptions = arrayListOf<Subscription<State>>()

    override fun getState() = currentState

    override fun dispatch(action: Action) {
        val newAction = applyMiddleware(currentState, action)
        val newState = applyReducers(currentState, action)
        if (newState == currentState)
            return
        currentState = newState
        subscriptions.forEach { it(currentState, ::dispatch) }
    }

    private fun applyMiddleware(state: State, action: Action): Action {
        return next(0)(state, action, ::dispatch)
    }

    private fun next(index: Int): Next<State> {
        if (index == middleware.size) {
            return { _,
                     action,
                     _ -> action
            }
        }
        return { state,
                 action,
                 dispatch -> middleware[index].invoke(state, action, dispatch, next(index + 1))
        }
    }

    private fun applyReducers(currentState: State, action: Action): State {
        var newState = currentState
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }
        return newState
    }

    override fun subscribe(subscription: Subscription<State>): Unsubscribe {
        subscriptions.add(subscription)
        subscription(currentState, ::dispatch) // notify subscription of the current state
        return { subscriptions.remove(subscription) }
    }

    override fun unsubscribe(subscription: Subscription<State>) {
        subscriptions.remove(subscription)
    }

}
