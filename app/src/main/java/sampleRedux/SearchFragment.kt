package sampleRedux

// Render
class SearchFragment: Fragment() {
    private var dispatch: Dispatch? = null
    private lateinit var unsubscribe: Unsubscribe

    override fun onViewCreated(View view){
        super.onViewCreated(view)
        unsubscribe = AppStore.instance.subscribe ( ::render )

        searchButton.setOnClickListener {
            dispatch?.invoke(Search(queryEditText.getText().toString()))
        }

        clearButton.setOnClickListener {
            dispatch?.invoke(ClearSearch)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unsubscribe.invoke()
        dispatch = null
    }
    private fun render(state: SearchState){
        this.dispatch = dispatch
        progressbar.show(state.loading)
        clearButton.show(state.movies.isNotEmpty())
        searchButton.show(!state.loading)
        queryEdittext.setText(state.query)
        adapter.setMovies(state.movies)
    }

}
