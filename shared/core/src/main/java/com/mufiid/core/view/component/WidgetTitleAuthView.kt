package com.mufiid.core.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class WidgetTitleAuthView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val componentRootView: View by findIdByLazy(R.id.component_widget_title_auth_container_view)
    private val componentTitleView: TextView by findIdByLazy(R.id.component_widget_title_auth_title)
    private val componentDivView: View by findIdByLazy(R.id.component_widget_title_auth_div)

    private var _isAuthSelected: Boolean = false
    var isAuthSelected: Boolean
        get() = _isAuthSelected
        set(value) {
            setAuthSelected(value)
        }

    private var _title: String = ""
    var title: String
        get() = _title
        set(value) {
            setTitle(value)
        }

    private var _authType: AuthType = AuthType.SIGN_IN
    var authType: AuthType
        get() = _authType
        set(value) {
            setAuthType(value)
        }

    enum class AuthType {
        SIGN_IN, SIGN_UP;
    }

    init {
        inflate(context, R.layout.component_widget_title_auth, this)
        context.obtainStyledAttributes(attributeSet, R.styleable.WidgetTitleAuthView, 0, 0).apply {
            val title = getString(R.styleable.WidgetTitleAuthView_authTitle).orEmpty()
            val isAuthSelected =
                getBoolean(R.styleable.WidgetTitleAuthView_isSelected, false)
            val authTypeIndex = getInt(R.styleable.WidgetTitleAuthView_authType, _authType.ordinal)
            val authType = AuthType.values()[authTypeIndex]

            setTitle(title)
            setAuthType(authType)
            setAuthSelected(isAuthSelected)
        }.recycle()
    }

    @JvmName("setTransportType1")
    private fun setAuthType(authType: AuthType) {
        _authType = authType
        val title = when (authType) {
            AuthType.SIGN_IN -> {
                "Sign In"
            }
            AuthType.SIGN_UP -> {
                "Sign Up"
            }
        }

        setTitle(title)
    }

    @JvmName("setTitle1")
    private fun setTitle(title: String) {
        _title = title
        componentTitleView.text = title
    }

    @JvmName("setAuthSelected1")
    private fun setAuthSelected(isAuthSelected: Boolean) {
        _isAuthSelected = isAuthSelected
        if (isAuthSelected) {
            componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.black))
            componentDivView.visibility = View.VISIBLE
        } else {
            componentTitleView.setTextColor(ContextCompat.getColor(context, R.color.grey))
            componentDivView.visibility = View.INVISIBLE
        }
    }


}