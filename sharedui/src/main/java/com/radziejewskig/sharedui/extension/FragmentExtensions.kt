package com.radziejewskig.sharedui.extension

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.radziejewskig.presentation.data.MessageData
import com.radziejewskig.presentation.data.MessageType
import com.radziejewskig.presentation.data.getMessageString
import com.radziejewskig.sharedui.R
import com.radziejewskig.sharedui.base.ui.BaseFragment

fun BaseFragment<*, *>.hideKeyboard() {
    ac()?.hideKeyboard()
}

fun BaseFragment<*, *>.showKeyboard(view: View) = ac()?.showKeyboard(view)

val BaseFragment<*, *>.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

fun <T> Fragment.getReturnedData(
    valueName: String,
    content: (T) -> Unit,
) {
    val backstackEntry = findNavController().currentBackStackEntry
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && backstackEntry?.savedStateHandle?.contains(valueName) == true) {
            val value = backstackEntry.savedStateHandle.get<T>(valueName)
            if(value != null) {
                content(value)
                backstackEntry.savedStateHandle.set(valueName, null)
            }
        }
    }
    backstackEntry?.lifecycle?.addObserver(observer)
    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            backstackEntry?.lifecycle?.removeObserver(observer)
        }
    })
}

fun <T> Fragment.sendBackStackValue(valueName: String, value: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(valueName, value)
}

fun <T> Fragment.popWithData(valueName: String, value: T) {
    sendBackStackValue(valueName, value)
    findNavController().popBackStack()
}

fun BaseFragment<*, *>.navigateSafe(
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    canNavigateTurnBackOnDelay: Long = 300
): Boolean {
    return if(canNavigateSafe(actionId, canNavigateTurnBackOnDelay)) {
        findNavController().navigate(actionId, args, navOptions)
        true
    } else false
}

private fun BaseFragment<*, *>.canNavigateSafe(
    @IdRes resId: Int,
    canNavigateTurnBackOnDelay: Long = 300
): Boolean {
    return if(
        (ac()?.lastNavComponentActionId == null || ac()?.lastNavComponentActionId != resId) &&
        ac()?.canNavigate() == true
    ) {
        if(canNavigateTurnBackOnDelay > 0) {
            ac()?.setCanNavigate(false, canNavigateTurnBackOnDelay)
        }

        ac()?.setLastNavComponentActionId(resId)
        true
    } else false
}

fun BaseFragment<*, *>.showMessage(messageData: MessageData) {

    val context = requireContext()

    val bgColor = when (messageData.type) {
        MessageType.ERROR -> R.color.red
        MessageType.SUCCESS -> R.color.green
        MessageType.WARNING -> R.color.orange
    }

    val defaultMessage = when (messageData.type) {
        MessageType.ERROR -> getString(R.string.an_unknown_error_occurred)
        MessageType.SUCCESS -> getString(R.string.success)
        MessageType.WARNING -> getString(R.string.warning)
    }

    val message = messageData.getMessageString(context)

    val snackbar = Snackbar.make(
        provideSnackbarAnchorView() ?: binding.root,
        if (message.isEmpty()) defaultMessage else message,
        if (messageData.durationShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    )

    val drawable = when(messageData.type) {
        MessageType.ERROR -> ContextCompat.getDrawable(context, R.drawable.error_icon)
        MessageType.SUCCESS -> ContextCompat.getDrawable(context, R.drawable.check_icon)
        MessageType.WARNING -> ContextCompat.getDrawable(context, R.drawable.warning_icon)
    }

    drawable?.setTint(context.getColorFromRes(android.R.color.white))
    drawable?.setTintMode(PorterDuff.Mode.SRC_ATOP)

    snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbar.setBackgroundTint(context.getColorFromRes(bgColor))

    val tv = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    tv.setTextColor(context.getColorFromRes(android.R.color.white))
    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    drawable?.let {
        val img = ImageView(context)
        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val layImageParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        img.setImageDrawable(it)

        (tv.parent as SnackbarContentLayout).addView(img, 0, layImageParams)
    }

    hideKeyboard()

    snackbar.show()
}
