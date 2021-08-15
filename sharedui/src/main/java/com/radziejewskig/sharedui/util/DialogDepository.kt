package com.radziejewskig.sharedui.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.radziejewskig.sharedui.R
import com.radziejewskig.sharedui.databinding.DialogInfoBinding
import com.radziejewskig.sharedui.databinding.DialogLoadingBinding
import com.radziejewskig.sharedui.extension.getColorFromRes
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ActivityScoped
class DialogDepository @Inject constructor(
    @ActivityContext val activityContext: Context
) {

    private lateinit var dialog: Dialog

    private val activity = activityContext as AppCompatActivity

    fun showLoadingDialog() {
        showCustomMaterialDialog(
            customView = DialogLoadingBinding.inflate(activity.layoutInflater, null, false).root,
            cancelable = false,
            styleDialogAnimation = null,
        )
    }

    fun showInfoDialog(
        title: String,
        message: String = "",
        okBtnText: String = activity.getString(R.string.ok),
        cancelBtnText: String? = null,
        @DrawableRes iconRes: Int? = null,
        @DimenRes backgroundInsetStart: Int = R.dimen.dp16,
        @DimenRes backgroundInsetEnd: Int = R.dimen.dp16,
        @DimenRes backgroundInsetTop: Int = R.dimen.dp24,
        @DimenRes backgroundInsetBottom: Int = R.dimen.dp24,
        dimAmount: Float = 0.58f,
        @StyleRes styleDialogAnimation: Int = R.style.AlertDialogThemeFadeScale,
        hideDelay: Long = HIDE_ON_CLICK_DELAY_DEFAULT,
        onCancelClick: () -> Unit = {},
        onOkClick: () -> Unit = {},
    ) {
        var canClick = true

        val infoDialog = DialogInfoBinding.inflate(activity.layoutInflater, null, false).apply {

            iconRes?.let {
                ic.setImageResource(iconRes)
                ic.isVisible = true
            }

            titleTv.text = title

            messageTv.isVisible = message.isNotEmpty()
            messageTv.text = message

            btnCancel.isVisible = !cancelBtnText.isNullOrEmpty()
            btnCancel.text = cancelBtnText

            btnOk.text = okBtnText

            btnOk.setOnClickListener {
                if(canClick) {
                    canClick = false
                    onOkClick()
                    activity.lifecycleScope.launchWhenStarted {
                        delay(hideDelay)
                        hideDialog()
                    }
                }
            }

            btnCancel.setOnClickListener {
                if(canClick) {
                    canClick = false
                    onCancelClick()
                    activity.lifecycleScope.launchWhenStarted {
                        delay(hideDelay)
                        hideDialog()
                    }
                }
            }
        }

        showCustomMaterialDialog(
            infoDialog.root,
            true,
            backgroundInsetStart = backgroundInsetStart,
            backgroundInsetEnd = backgroundInsetEnd,
            backgroundInsetTop = backgroundInsetTop,
            backgroundInsetBottom = backgroundInsetBottom,
            dimAmount,
            styleDialogAnimation = styleDialogAnimation
        )
    }

    fun showCustomMaterialDialog(
        customView: View,
        cancelable: Boolean,
        @DimenRes backgroundInsetStart: Int = R.dimen.dp16,
        @DimenRes backgroundInsetEnd: Int = R.dimen.dp16,
        @DimenRes backgroundInsetTop: Int = R.dimen.dp24,
        @DimenRes backgroundInsetBottom: Int = R.dimen.dp24,
        dimAmount: Float = 0.58f, // 0-1
        @StyleRes styleDialogAnimation: Int? = R.style.AlertDialogThemeFadeScale
    ) {
        hideDialog()

        val insetStart = activity.resources.getDimensionPixelSize(backgroundInsetStart)
        val insetEnd = activity.resources.getDimensionPixelSize(backgroundInsetEnd)
        val insetTop = activity.resources.getDimensionPixelSize(backgroundInsetTop)
        val insetBottom = activity.resources.getDimensionPixelSize(backgroundInsetBottom)

        dialog = MaterialAlertDialogBuilder(activity)
            .setView(customView)
            .setBackground(ColorDrawable(activity.getColorFromRes(R.color.transparent)))
            .setCancelable(cancelable)
            .setBackgroundInsetStart(insetStart)
            .setBackgroundInsetEnd(insetEnd)
            .setBackgroundInsetTop(insetTop)
            .setBackgroundInsetBottom(insetBottom)
            .create()

        dialog.window?.setDimAmount(dimAmount)
        styleDialogAnimation?.let { dialog.window?.setWindowAnimations(it) }

        dialog.show()
    }

    fun hideDialog() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    companion object {
        const val HIDE_ON_CLICK_DELAY_DEFAULT: Long = 100
    }

}
