package com.example.pokermao.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokermao.model.Pokemon
import com.example.pokermao.repository.PokemonRepository
import com.example.pokermao.view.ViewState

class DetailViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    val viewState: MutableLiveData<ViewState<Pokemon?>>
            = MutableLiveData()

    fun getPokemon(number: String) {
        viewState.value = ViewState.Loading
        pokemonRepository.getPokemon(
            number,
            onComplete = {
                viewState.value = ViewState.Success(it)
            },
            onError = {
                viewState.value = ViewState.Failed(it)
            }
        )
    }
}
