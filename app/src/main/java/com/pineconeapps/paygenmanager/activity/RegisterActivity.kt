package com.pineconeapps.paygenmanager.activity

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TimePicker
import com.example.gustavobatista.paygen.entity.Lobby
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.pineconeapps.paygenmanager.R
import com.pineconeapps.paygenmanager.entity.OpenHours
import com.pineconeapps.paygenmanager.entity.Point
import com.pineconeapps.paygenmanager.entity.Provider
import com.pineconeapps.paygenmanager.entity.ProviderInfo
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity : BaseActivity() {
    var provider: Provider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (provider == null) {
            provider = Provider(Provider.Status.PENDING, Lobby(), Point(0.0, 0.0),
                    mutableListOf(), mutableListOf(), mutableListOf(),
                    ProviderInfo("", "", "", ProviderInfo.Type.HAMBURGUER, mutableListOf()))
        }

        btRegister.setOnClickListener { onClickRegister() }

        spTypes.adapter = ArrayAdapter<ProviderInfo.Type>(this,
                android.R.layout.simple_spinner_dropdown_item, ProviderInfo.Type.values())

        spTypes.onItemSelectedListener = onSelectType()

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

    private fun onSelectType(): OnItemSelectedListener {
        return object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                provider!!.info.type = ProviderInfo.Type.values()[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
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
                provider!!.location = Point(place.latLng.latitude, place.latLng.longitude)
                provider!!.info.address = place.address.toString()
                tvAddress.setText(place.address.toString() )
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

    private fun onClickRegister() {
        when {
            tvName.text.toString().isEmpty() -> showWarning("Informe o nome do estabelecimento")
            getOpenHours().size == 0 -> showWarning("Selecione ao menos uma data e horário")
            tvAbout.text.toString().isEmpty() -> showWarning("Informe um texto dobre sua empresa")
            provider!!.info.address.isNullOrEmpty() -> showWarning("Informe um endereço")
            else -> buildprovider()
        }
    }

    private fun buildprovider() {
        provider!!.info.openHours = getOpenHours()
        provider!!.info.about = tvAbout.text.toString()
        provider!!.name = tvName.text.toString()

    }

    private fun getOpenHours(): MutableList<OpenHours> {
        val list: MutableList<OpenHours> = mutableListOf()
        if (cbAbreSeg.isChecked) {
            list.add(OpenHours("SEG", etAbreSeg.text.toString(), etFechaSeg.text.toString()))
        }

        if (cbAbreTer.isChecked) {
            list.add(OpenHours("TER", etAbreTer.text.toString(), etFechaTer.text.toString()))
        }

        if (cbAbreQuar.isChecked) {
            list.add(OpenHours("QUA", etAbreQua.text.toString(), etFechaQua.text.toString()))
        }

        if (cbAbreQui.isChecked) {
            list.add(OpenHours("QUI", etAbreQui.text.toString(), etFechaQui.text.toString()))
        }

        if (cbAbreSex.isChecked) {
            list.add(OpenHours("SEX", etAbreSex.text.toString(), etFechaSex.text.toString()))
        }

        if (cbAbreSab.isChecked) {
            list.add(OpenHours("SAB", etAbreSab.text.toString(), etFechaSab.text.toString()))
        }

        if (cbAbreDom.isChecked) {
            list.add(OpenHours("DOM", etAbreDom.text.toString(), etFechaDom.text.toString()))
        }
        return list
    }

}
