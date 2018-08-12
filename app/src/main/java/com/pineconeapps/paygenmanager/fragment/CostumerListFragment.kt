package com.pineconeapps.paygenmanager.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.entity.Lobby
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.activity.ConsumptionActivity
import com.pineconeapps.paygenmanager.adapter.CustomerAdapter
import com.pineconeapps.paygenmanager.entity.Consumption
import com.pineconeapps.paygenmanager.entity.Customer
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.LobbyService
import kotlinx.android.synthetic.main.fragment_costumer_list.*
import org.jetbrains.anko.startActivity

class CostumerListFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_costumer_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getCustomers()
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun getCustomers() {
        showProgress()
        LobbyService.getCustomers(prefs.providerId).applySchedulers().subscribe(
                {
                    createAdapter(it)
                },
                {
                    handleException(it)
                    closeProgress()
                },
                {
                    closeProgress()
                }
        )
    }

    private fun createAdapter(lobby: Lobby) {
        val adapter = CustomerAdapter(lobby.customerList){
            startActivity<ConsumptionActivity>("customer" to it)
        }

        recyclerView.adapter = adapter
    }


}
