package com.pineconeapps.paygenmanager.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.pineconeapps.paygenmanager.R


class Progress(context: Context) : Dialog(context, R.style.Library_Progress) {

    init {
        val attributes = window!!.attributes
        attributes.gravity = Gravity.CENTER_HORIZONTAL
        window!!.attributes = attributes
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val progressBar = ProgressBar(context)
        progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        layout.addView(progressBar, params)
        addContentView(layout, params)
    }
}