package com.pineconeapps.paygenmanager.activity

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TimePicker
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.Provider
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        spTypes.adapter = ArrayAdapter<Provider.Type>(this, android.R.layout.simple_spinner_dropdown_item, Provider.Type.values())


        tvAddress.setOnClickListener { getPlaces() }

        etAbreSeg.setOnClickListener { getOpenTime(etAbreSeg) }
        etFechaSeg.setOnClickListener { getOpenTime(etFechaSeg) }

        etAbreTer.setOnClickListener { getOpenTime(etAbreTer) }
        etFechaTer.setOnClickListener { getOpenTime(etFechaTer) }

        etAbreQua.setOnClickListener { getOpenTime(etAbreQua) }
        etFechaQua.setOnClickListener { getOpenTime(etFechaQua) }

        etAbreQui.setOnClickListener { getOpenTime(etAbreQui) }
        etFechaQui.setOnClickListener { getOpenTime(etFechaQui) }

        etAbreSex.setOnClickListener { getOpenTime(etAbreSex) }
        etFechaSex.setOnClickListener { getOpenTime(etFechaSex) }

        etAbreSab.setOnClickListener { getOpenTime(etAbreSab) }
        etFechaSab.setOnClickListener { getOpenTime(etFechaSab) }

        etAbreDom.setOnClickListener { getOpenTime(etAbreDom) }
        etFechaDom.setOnClickListener { getOpenTime(etFechaDom) }
    }

    private fun getPlaces() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this)
            startActivityForResult(intent, 1)
        } catch (e: GooglePlayServicesRepairableException) {
            handleException(e)
        } catch (e: GooglePlayServicesNotAvailableException) {
            handleException(e)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(this, data)
                tvAddress.setText(place.address)
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(this, data)
                showWarning(status.statusMessage!!)
            }
        }
    }

    private fun getOpenTime(view: EditText) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(getActivity(), TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
            val hourStr: String = if (hour < 10) {
                "0" + hour.toString()
            } else {
                hour.toString()
            }

            val minuteStr: String = if (minute < 10) {
                "0" + minute.toString()
            } else {
                minute.toString()
            }
            view.setText("$hourStr:$minuteStr")

        }, hour, minute,
                DateFormat.is24HourFormat(getActivity())).show()
    }

}
