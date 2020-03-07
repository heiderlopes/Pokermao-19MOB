package com.example.pokermao.view.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokermao.model.Pokemon
import com.example.pokermao.repository.PokemonRepository
import com.example.pokermao.view.ViewState

class FormPokemonViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    val viewState: MutableLiveData<ViewState<String>>
            = MutableLiveData()

    fun updatePokemon(pokemon: Pokemon) {

        viewState.value = ViewState.Loading

        pokemonRepository.updatePokemon(
            pokemon = pokemon,
            onComplete = {

                viewState.value = ViewState.Success("Dados gravado com sucesso")
            },
            onError = {

                viewState.value = ViewState.Failed(it)
            }
        )
    }
}