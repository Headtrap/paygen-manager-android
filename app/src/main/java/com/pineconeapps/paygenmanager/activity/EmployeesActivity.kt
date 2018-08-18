package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ActionMode
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.adapter.UserAdapter
import com.pineconeapps.paygenmanager.entity.Employee
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.EmployeeService
import kotlinx.android.synthetic.main.activity_employees.*
import org.jetbrains.anko.startActivity

class EmployeesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees)
        setupActionBar()
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getEmployees()
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        fabAddEmployee.setOnClickListener { startActivity<AddEmployeeActivity>("employee" to null) }
    }

    private fun getEmployees() {
        showProgress()
        EmployeeService.listEmployee(prefs.providerId).applySchedulers().subscribe(
                {
                    handleList(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun handleList(employees: List<Employee>) {
        val adapter = UserAdapter(employees) {
            startActivity<AddEmployeeActivity>("employee" to it)
        }
        recyclerView.adapter = adapter
    }
}
