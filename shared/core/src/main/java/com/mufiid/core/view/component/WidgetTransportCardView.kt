package com.mufiid.core.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class WidgetTransportCardView(context: Context, attributeSet: AttributeSet) :

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

    private var _isTransportEnabled: Boolean = false
    var isTransportEnabled: Boolean
        get() = _isTransportEnabled
        set(value) {
            setTransportEnabled(value)
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
        inflate(context, R.layout.component_widget_transport_card, this)
        context.obtainStyledAttributes(attributeSet, R.styleable.WidgetTransportCardView, 0, 0).apply {
            val image = getResourceId(R.styleable.WidgetTransportCardView_image, -1)
            val title = getString(R.styleable.WidgetTransportCardView_title).orEmpty()
            val isTransportSelected =
                getBoolean(R.styleable.WidgetTransportCardView_isTransportSelected, false)
            val transportTypeIndex = getInt(R.styleable.WidgetTransportCardView_transportType, _transportType.ordinal)
            val transportType = TransportType.values()[transportTypeIndex]
            val isTransportEnabled = getBoolean(R.styleable.WidgetTransportCardView_setEnabled, false)

            setTitle(title)
            setImageView(image)
            setTransportType(transportType)
            setTransportSelected(isTransportSelected)
            setTransportEnabled(isTransportEnabled)
        }.recycle()

    }

    enum class TransportType {
        BIKE, CAR, TAXI;
    }

    @JvmName("setTransportType1")
    private fun setTransportType(transportType: TransportType) {
        _transportType = transportType
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

    @JvmName("setTransportEnabled1")
    private fun setTransportEnabled(enabled: Boolean) {
        if (enabled) {
            componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.grey))
            when (_transportType) {
                TransportType.BIKE -> {
                    setImageView(R.drawable.ic_transport_bike_disable)
                }
                TransportType.CAR -> {
                    setImageView(R.drawable.ic_transport_car_disable)
                }
                TransportType.TAXI -> {
                    setImageView(R.drawable.ic_transport_taxi_disable)
                }
            }
        } else {
            componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.black))
            when (_transportType) {
                TransportType.BIKE -> {
                    setImageView(R.drawable.ic_transport_bike)
                }
                TransportType.CAR -> {
                    setImageView(R.drawable.ic_transport_car)
                }
                TransportType.TAXI -> {
                    setImageView(R.drawable.ic_transport_taxi)
                }
            }
        }
    }

}
