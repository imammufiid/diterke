package com.mufiid.core.view.component

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.mufiid.core.R
import com.mufiid.core.extensions.findIdByLazy

class WidgetFormAuthView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {
    private val componentRootView: ConstraintLayout by findIdByLazy(R.id.component_widget_form_auth_container_view)
    private val componentTitleSignInView: WidgetTitleAuthView by findIdByLazy(R.id.component_widget_form_auth_tv_signin)
    private val componentTitleSignUpView: WidgetTitleAuthView by findIdByLazy(R.id.component_widget_form_auth_tv_signup)
    private val componentButtonView: WidgetButtonView by findIdByLazy(R.id.component_widget_form_auth_button)
    private val componentEtUsernameView: EditText by findIdByLazy(R.id.component_widget_form_auth_et_username)
    private val componentEtSecondaryView: EditText by findIdByLazy(R.id.component_widget_form_auth_et_secondary)
    private var listener : OnClickListener? = null
    private var textChangeListener : AddTextChangedListener? = null

    private var _isSignIn: Boolean = false
    var isSignIn: Boolean
        get() = _isSignIn
        set(value) {
            setIsSignIn(value)
        }

        init {
            inflate(context, R.layout.component_widget_form_auth, this)
            context.obtainStyledAttributes(attributeSet, R.styleable.WidgetFormAuthView, 0, 0).apply {
                val isSignIn = getBoolean(R.styleable.WidgetFormAuthView_isSignIn, true)

                setIsSignIn(isSignIn)

                componentButtonView.setOnClickListener {
                    listener?.onClickListener(it)
                }

                componentTitleSignInView.setOnClickListener {
                    setIsSignIn(true)
                }

                componentTitleSignUpView.setOnClickListener {
                    setIsSignIn(false)
                }

                componentEtUsernameView.addTextChangedListener {
                    textChangeListener?.addTextChangedListener(it, EDIT_TEXT_USERNAME)
                }

                componentEtSecondaryView.addTextChangedListener {
                    textChangeListener?.addTextChangedListener(it, EDIT_TEXT_SECONDARY)
                }
            }.recycle()
        }

    fun setOnButtonClickListener(onClickListener: OnClickListener) {
        this.listener = onClickListener
    }

    fun setAddTextChangedListener(textChangeListener: AddTextChangedListener) {
        this.textChangeListener = textChangeListener
    }

    @JvmName("setIsSignIn1")
    private fun setIsSignIn(isSignIn: Boolean) {
        _isSignIn = isSignIn
        componentTitleSignInView.isAuthSelected = _isSignIn
        componentTitleSignUpView.isAuthSelected = !_isSignIn
        componentButtonView.title = if (_isSignIn) "Sign In" else "Sign Up"
        componentEtSecondaryView.setText("")
    }

    fun interface OnClickListener {
        fun onClickListener(view: View)
    }

    fun interface AddTextChangedListener {
        fun addTextChangedListener(editable: Editable?, id: Int)
    }

    companion object {
        const val EDIT_TEXT_USERNAME = 101
        const val EDIT_TEXT_SECONDARY = 102
    }

}