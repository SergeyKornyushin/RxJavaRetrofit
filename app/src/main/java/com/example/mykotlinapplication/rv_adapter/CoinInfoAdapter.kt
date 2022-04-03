package com.example.mykotlinapplication.rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinapplication.R
import com.example.mykotlinapplication.databinding.ItemCoinRvBinding
import com.example.mykotlinapplication.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    inner class CoinInfoViewHolder(val binding: ItemCoinRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

    var onCoinClickListener: OnCoinClickListener? = null

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder =
        CoinInfoViewHolder(
            ItemCoinRvBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_coin_rv, parent, false)
            )
        )

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder.binding) {
            with(coin) {
                with(holder.itemView.context) {
                    tvSymbols.text = String.format(
                        getString(R.string.rv_symbols),
                        fromsymbol, tosymbol
                    )
                    tvUpdateTime.text = String.format(
                        getString(R.string.rv_last_update),
                        getFormattedTime()
                    )
                }
                tvPrice.text = price.toString()
                Picasso.get().load(getFullImageUrl()).into(imageView)
            }
        }
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount(): Int = coinInfoList.size
}