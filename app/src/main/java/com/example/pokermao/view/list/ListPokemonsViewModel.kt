package com.example.pokermao.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokermao.model.Pokemon
import com.example.pokermao.repository.PokemonRepository
import com.example.pokermao.view.ViewState

class ListPokemonsViewModel (val pokemonRepository: PokemonRepository) : ViewModel() {

    val viewState: MutableLiveData<ViewState<List<Pokemon>>>
            = MutableLiveData()

    fun getPokemons() {
        viewState.value = ViewState.Loading

        pokemonRepository.getPokemons(
            150, "number,asc", {listaPokemons ->
                viewState.value = ViewState.Success(listaPokemons)

            }, { error ->
                viewState.value = ViewState.Failed(error)
            }
        )
    }
}