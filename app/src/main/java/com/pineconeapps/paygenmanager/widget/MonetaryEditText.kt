package com.pineconeapps.paygenmanager.widget

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.pineconeapps.paygenmanager.util.StringUtils.currency


class MonetaryEditText : AppCompatEditText {
    private val empty = "R$ 0,00"

    private lateinit var monetaryEditText: MonetaryEditText

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        monetaryEditText = this
        this.addTextChangedListener(CurrencyMask(this))
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context) : super(context) {}

    fun clear() {
        this.setText(empty)
    }

    fun getValue(): Double {
        val value = monetaryEditText.text.toString()
        return when {
            value.isNullOrEmpty() -> 0.0
            else -> value.replace("[R$,.]"
                    .toRegex(), "").replace(" ", "")
                    .replace("-".toRegex(), "").toDouble()
        }
    }

    internal inner class CurrencyMask(private val editTextValor: EditText) : TextWatcher {
        private var current = ""
        private var formatted = ""

        override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, after: Int) {
            if (charSequence.toString() != this.current) {
                this.editTextValor.removeTextChangedListener(this)
                val cleanString = charSequence.toString().replace("[R$,.]"
                        .toRegex(), "").replace(" ", "")
                        .replace("-".toRegex(), "")
                if (!cleanString.isEmpty()) {
                    val parsed = java.lang.Double.parseDouble(cleanString)
                    this.formatted = (parsed / 100.0).currency()
                    if (cleanString.length > 11) {
                        this.formatted = this.current
                    }
                } else {
                    this.formatted = empty
                }

                this.editTextValor.setText(this.formatted)
                this.editTextValor.setSelection(this.formatted.length)
                this.editTextValor.addTextChangedListener(this)
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            this.current = s.toString()
        }

        override fun afterTextChanged(s: Editable) {
            this.current = s.toString()
        }
    }

}
