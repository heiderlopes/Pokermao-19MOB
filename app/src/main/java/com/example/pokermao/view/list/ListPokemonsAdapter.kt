package com.example.pokermao.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokermao.R
import com.example.pokermao.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class ListPokemonsAdapter(
    val picasso: Picasso
) : RecyclerView.Adapter<ListPokemonsAdapter.PokemonViewHolder>() {

    private lateinit var clickListener : (Pokemon) -> Unit
    private var pokemons: List<Pokemon> = listOf()

    fun setItems(pokemons: List<Pokemon>) {
        this.pokemons = pokemons
        notifyDataSetChanged()
    }

    fun setOnClickListener(clickListener: (Pokemon) -> Unit) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PokemonViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_list_item,
            parent, false)

        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.bindView(pokemon, picasso, clickListener)
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(pokemon: Pokemon,
                     picasso: Picasso,
                     clickListener: (Pokemon) -> Unit) = with(itemView) {
            tvPokemonName.text = pokemon.nome
            tvPokemonNumber.text = pokemon.numero

            picasso.load("https://pokedexdx.herokuapp.com${pokemon.urlImagem}")
                .into(ivPokemon)
            setOnClickListener { clickListener(pokemon) }
        }

    }
}