package com.mufiid.core.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class TransportCardView(context: Context, attributeSet: AttributeSet) :

    FrameLayout(context, attributeSet) {

    private val componentRootView: View by findIdByLazy(R.id.component_transport_card_root_view)
    private val componentTitleView: TextView by findIdByLazy(R.id.component_transport_card_title)
    private val componentImageView: AppCompatImageView by findIdByLazy(R.id.component_transport_card_image)

    private var _imageRes: Int = -1
    var imageRes: Int
        get() = _imageRes
        set(value) {
            setImageView(value)
        }

    private var _isTransportSelected: Boolean = false
    var isTransportSelected: Boolean
        get() = _isTransportSelected
        set(value) {
            setTransportSelected(value)
        }

    private var _title: String = ""
    var title: String
        get() = _title
        set(value) {
            setTitle(value)
        }

    private var _transportType: TransportType = TransportType.BIKE
    var transportType: TransportType
        get() = _transportType
        set(value) {
            setTransportType(value)
        }


    init {
        inflate(context, R.layout.component_transport_card, this)
        context.obtainStyledAttributes(attributeSet, R.styleable.TransportCardView, 0, 0).apply {
            val image = getResourceId(R.styleable.TransportCardView_image, -1)
            val title = getString(R.styleable.TransportCardView_title).orEmpty()
            val isTransportSelected =
                getBoolean(R.styleable.TransportCardView_isTransportSelected, false)
            val transportTypeIndex = getInt(R.styleable.TransportCardView_transportType, _transportType.ordinal)
            val transportType = TransportType.values()[transportTypeIndex]

            setTitle(title)
            setImageView(image)
            setTransportType(transportType)
            setTransportSelected(isTransportSelected)
        }.recycle()

    }

    enum class TransportType {
        BIKE, CAR, TAXI;
    }

    @JvmName("setTransportType1")
    private fun setTransportType(transportType: TransportType) {
        val (imageRes, title) = when (transportType) {
            TransportType.BIKE -> {
                Pair(R.drawable.ic_transport_bike, "TransBike")
            }
            TransportType.CAR -> {
                Pair(R.drawable.ic_transport_car, "TransCar")
            }
            TransportType.TAXI -> {
                Pair(R.drawable.ic_transport_taxi, "TransTaxi")
            }
        }

        setTitle(title)
        setImageView(imageRes)
    }

    @JvmName("setTitle1")
    private fun setTitle(title: String) {
        _title = title
        componentTitleView.text = title
    }

    private fun setImageView(imageRes: Int) {
        _imageRes = imageRes
        if (imageRes != -1) {
            componentImageView.setImageResource(imageRes)
        }
    }

    @JvmName("setTransportSelected1")
    private fun setTransportSelected(isSelected: Boolean) {
        _isTransportSelected = isSelected
        val backgroundRootView = if (isSelected) {
            R.drawable.bg_component_transport_card_selected
        } else {
            R.drawable.bg_component_transport_card_unselected
        }
        componentRootView.setBackgroundResource(backgroundRootView)
    }

}
