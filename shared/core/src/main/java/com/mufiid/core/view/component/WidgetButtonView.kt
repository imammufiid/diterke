package com.mufiid.core.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class WidgetButtonView(context: Context, attributeSet: AttributeSet): FrameLayout(context, attributeSet) {
    private val componentRootView: ConstraintLayout by findIdByLazy(R.id.component_button_root_view)
    private val componentTitleView: TextView by findIdByLazy(R.id.component_button_title)
    private val componentImageView: AppCompatImageView by findIdByLazy(R.id.component_button_icon)
    private var listener : OnClickListener? = null

    enum class ButtonType {
        DEFAULT, FACEBOOK, GOOGLE
    }

    private var _title: String = ""
    var title: String
        get() = _title
        set(value) {
            setTitle(value)
        }

    private var _buttonType: ButtonType = ButtonType.DEFAULT
    var buttonType: ButtonType
        get() = _buttonType
        set(value) {
            setButtonType(value)
        }

    init {
        inflate(context, R.layout.component_widget_button, this)
        context.obtainStyledAttributes(attributeSet, R.styleable.WidgetButtonView, 0, 0).apply {
            val title = getString(R.styleable.WidgetButtonView_buttonTitle).orEmpty()
            val transportTypeIndex = getInt(R.styleable.WidgetButtonView_buttonType, _buttonType.ordinal)
            val transportType = ButtonType.values()[transportTypeIndex]

            setTitle(title)
            setButtonType(transportType)

            componentRootView.setOnClickListener {
                listener?.onClickListener(it)
            }
        }.recycle()
    }


    @JvmName("setTitle1")
    private fun setTitle(title: String) {
        _title = title
        componentTitleView.text = title
    }

    private fun setImageView(imageRes: Int) {
        if (imageRes != -1) {
            componentImageView.setImageResource(imageRes)
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.listener = onClickListener
    }

    @JvmName("setTransportType1")
    private fun setButtonType(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.DEFAULT -> {
                componentImageView.visibility = View.GONE
                componentRootView.background = ContextCompat.getDrawable(context, R.drawable.bg_component_button_default_selector)
                componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            ButtonType.FACEBOOK -> {
                componentImageView.visibility = View.VISIBLE
                setImageView(R.drawable.ic_facebook)
                componentRootView.background = ContextCompat.getDrawable(context, R.drawable.bg_component_button_facebook_selector)
                componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            ButtonType.GOOGLE -> {
                componentImageView.visibility = View.VISIBLE
                setImageView(R.drawable.ic_google)
                componentRootView.background = ContextCompat.getDrawable(context, R.drawable.bg_component_button_google_selector)
                componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

    fun interface OnClickListener {
        fun onClickListener(view: View)
    }
}