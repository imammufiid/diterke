package com.mufiid.core.view.component

import android.content.Context
import android.location.Location
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class WidgetInputLocationView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val editTextFrom: EditText by findIdByLazy(R.id.et_from)
    private val editTextDest: EditText by findIdByLazy(R.id.et_destination)

    data class InputLocationData(
        val location: Location = Location(""),
        val name: String = "Select location"
    ) {
        fun isEmpty(): Boolean = name == "Select location"
    }

    private var _inputLocationFromData: InputLocationData = InputLocationData()

    var inputLocationFromData: InputLocationData
        get() = _inputLocationFromData
        set(value) {
            editTextFrom.setText(value.name)
            _inputLocationFromData = value
        }

    private var _inputLocationDestData: InputLocationData = InputLocationData()
    var inputLocationDestData: InputLocationData
        get() = _inputLocationDestData
        set(value) {
            editTextDest.setText(value.name)
            _inputLocationDestData = value
        }

    init {
        inflate(context, R.layout.component_widget_input_location_view, this)

        editTextFrom.hint = _inputLocationFromData.name
        editTextDest.hint = _inputLocationDestData.name
    }

    companion object {
        fun locationDataEmpty() = InputLocationData()
        fun locationDataLoading() = InputLocationData(name = "Loading...")
        fun locationDataFail() = InputLocationData(name = "Failure, try again!")
    }

    fun onFromClick(action: () -> Unit) {
        editTextFrom.apply {
            setText(_inputLocationFromData.name)
            isFocusable = false
            isClickable = true
        }
        editTextFrom.setOnClickListener {
            action.invoke()
        }
    }

    fun onDestClick(action: () -> Unit) {
        editTextDest.apply {
            setText(_inputLocationFromData.name)
            isFocusable = false
            isClickable = true
        }
        editTextDest.setOnClickListener {
            action.invoke()
        }
    }

    fun setFocus(form: Int) {
        when (form) {
            1 -> {
                editTextFrom.isFocusable = true
                editTextFrom.isClickable = false
                editTextFrom.requestFocus()
            }
            2 -> {
                editTextDest.isFocusable = true
                editTextDest.isClickable = false
                editTextDest.requestFocus()
            }
        }
    }
}