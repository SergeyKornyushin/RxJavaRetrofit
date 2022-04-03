package com.example.mykotlinapplication.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlinapplication.databinding.ActivityCoinDetailInfoBinding
import com.example.mykotlinapplication.view_model.CoinViewModel
import com.squareup.picasso.Picasso

class CoinDetailInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinDetailInfoBinding
    private lateinit var viewModel: CoinViewModel.Base
    private lateinit var fSymFromExtra: String

    companion object {
        private const val EXTRA_KEY_SYMBOL = "fSym"

        fun getDetailActivityIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, CoinDetailInfoActivity::class.java)
            intent.putExtra(EXTRA_KEY_SYMBOL, fSym)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra(EXTRA_KEY_SYMBOL) == null) {
            finish()
            return
        } else {
            fSymFromExtra = intent.getStringExtra(EXTRA_KEY_SYMBOL)!!
        }
        viewModel = ViewModelProvider(this)[CoinViewModel.Base::class.java]

        viewModel.getDetailCoinInfo(fSymFromExtra).observe(this, {
            with(binding){
                tvCurrentPrice.text = it.price.toString()
                tvFrom.text = it.fromsymbol
                tvTo.text = it.tosymbol
                tvCurrentDayMin.text = it.lowday.toString()
                tvCurrentDayMax.text = it.highday.toString()
                tvCurrentLastTransaction.text = it.lastmarket
                tvCurrentLastUpdate.text = it.getFormattedTime()
                Picasso.get().load(it.getFullImageUrl()).into(ivCoin)
            }
            Log.i("test4", "onCreate: $it")
        })
    }
}