package com.example.mykotlinapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mykotlinapplication.databinding.ActivityMainBinding
import com.example.mykotlinapplication.pojo.CoinPriceInfo
import com.example.mykotlinapplication.rv_adapter.CoinInfoAdapter
import com.example.mykotlinapplication.view_model.CoinViewModel

private lateinit var binding: ActivityMainBinding

private lateinit var viewModel: CoinViewModel.Base

class CoinPriceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailInfoActivity.getDetailActivityIntent(
                    this@CoinPriceActivity,
                    coinPriceInfo.fromsymbol
                )
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[CoinViewModel.Base::class.java]
        viewModel.priceList.observe(this, {
            adapter.coinInfoList = it
        })
    }
}