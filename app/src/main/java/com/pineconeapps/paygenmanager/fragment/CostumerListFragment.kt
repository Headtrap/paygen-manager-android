package com.pineconeapps.paygenmanager.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.entity.Lobby
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.activity.ConsumptionActivity
import com.pineconeapps.paygenmanager.adapter.UserAdapter
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
        swipeRefresh.setOnRefreshListener { getCustomers() }
    }

    private fun getCustomers() {
        swipeRefresh.isRefreshing = false
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
        if (lobby.customerList.isEmpty()) {
            tvEmptyRecycler.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {

            tvEmptyRecycler.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            tvCustomers.text = lobby.customerList.size.toString()
            val adapter = UserAdapter(lobby.customerList) {
                startActivity<ConsumptionActivity>("customer" to it)
            }
            recyclerView.adapter = adapter
        }
    }


}
