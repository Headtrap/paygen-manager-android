package com.pineconeapps.paygenmanager.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Employee
import com.pineconeapps.paygenmanager.prefs
import com.pineconeapps.paygenmanager.service.EmployeeService
import kotlinx.android.synthetic.main.activity_add_employee.*
import kotlinx.android.synthetic.main.activity_employees.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class AddEmployeeActivity : BaseActivity() {

    var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        setupActionBar()
        employee = intent.getSerializableExtra("employee") as Employee?
        if (employee == null) {
            employee = Employee(Employee.Role.values()[0], "")
        } else {
            populate()
        }

        spRoles.adapter = ArrayAdapter<Employee.Role>(this,
                android.R.layout.simple_spinner_dropdown_item, Employee.Role.values())
        spRoles.onItemSelectedListener = onSelectRole()
        btSave.setOnClickListener { saveEmployee() }
    }

    private fun populate() {
        etEmail.setText(employee?.email)
        etName.setText(employee?.name)
        etPin.setText(employee?.password)
        spRoles.setSelection(Employee.Role.values().indexOf(employee?.role))
    }

    private fun onSelectRole(): AdapterView.OnItemSelectedListener? {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                employee?.role = Employee.Role.values()[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun saveEmployee() {
        if (etEmail.text.isNullOrEmpty() || etName.text.isNullOrEmpty() || etPin.text.isNullOrEmpty()) {
            showWarning(R.string.warning_empty_fields)
            return
        }
        if (etPin.text.toString().length < 6) {
            showWarning(R.string.pin_size)
            return
        }

        employee?.email = etEmail.text.toString()
        employee?.name = etName.text.toString()
        employee?.password = etPin.text.toString()
        employee?.providerId = prefs.providerId

        showProgress()
        EmployeeService.addEmployee(employee).applySchedulers().subscribe(
                {
                    handleResult(it)
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

    private fun handleResult(message: String) {
        alert(message, getString(R.string.title_success))
        {
            yesButton {
                finish()
            }
        }.show()
    }

}
