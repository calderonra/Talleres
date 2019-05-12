package com.naldana.ejemplo10

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.coin_layout.view.*

class CoinAdapter(val items: List<Coin> , val clickListener : (Coin) -> Unit) : RecyclerView.Adapter<CoinAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Coin , clickListener: (Coin) -> Unit) = with(itemView){

            tv_nombreMoneda.text = item.name
            tv_nombrePais.text = item.country
            tv_valorMonetario.text = item.value.toString()

            this.setOnClickListener{
                clickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_layout,null,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position] , clickListener)
    }
}