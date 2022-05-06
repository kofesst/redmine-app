package me.kofesst.android.redminecomposeapp.feature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun DefaultSwipeRefresh(
    refreshState: SwipeRefreshState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
) {
    SwipeRefresh(
        state = refreshState,
        onRefresh = onRefresh,
        indicator = { state, trigger ->
            DefaultSwipeRefreshIndicator(
                state = state,
                trigger = trigger
            )
        },
        content = content,
        modifier = modifier
    )
}

@Composable
fun DefaultSwipeRefreshIndicator(
    state: SwipeRefreshState,
    trigger: Dp
) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = trigger,
        scale = true,
        contentColor = MaterialTheme.colors.primary
    )
}

@Composable
fun ValidatedDropdown(
    items: List<DropdownItem>,
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    errorMessage: String? = null
) {
    Dropdown(
        items = items,
        value = value,
        placeholder = placeholder,
        isError = errorMessage != null,
        modifier = modifier
    )
    TextFieldError(
        modifier = Modifier.fillMaxWidth(),
        message = errorMessage
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Dropdown(
    items: List<DropdownItem>,
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    hasNullValue: Boolean = false,
    onNullValueSelected: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            value = value,
            onValueChange = { },
            maxLines = 1,
            isError = isError,
            label = { Text(text = placeholder) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            items.let {
                if (hasNullValue) {
                    it + DropdownItem.getNullItem(onNullValueSelected)
                } else {
                    it
                }
            }.forEach { item ->
                DropdownMenuItem(onClick = {
                    item.onSelected()
                    isExpanded = false
                }) {
                    Text(
                        text = item.text,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

data class DropdownItem(
    val text: String,
    val onSelected: () -> Unit
) {
    companion object {
        fun getNullItem(onSelected: () -> Unit): DropdownItem {
            return DropdownItem("Нет", onSelected)
        }
    }
}

@Composable
fun ClickableCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 5.dp,
    cornerRadius: Dp = 5.dp,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    DefaultCard(
        modifier = modifier.clickable { onClick() },
        elevation = elevation,
        cornerRadius = cornerRadius,
        content = content
    )
}

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 5.dp,
    cornerRadius: Dp = 5.dp,
    content: @Composable () -> Unit
) {
    Card(
        elevation = elevation,
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier,
        content = content
    )
}

@Composable
fun OutlinedValidatedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    errorMessage: String? = null,
    onValueChange: (String) -> Unit = {},
    label: String = "",
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {}
) {
    DefaultTextField(
        value = value,
        onValueChange = onValueChange,
        errorMessage = errorMessage,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onTrailingIconClick = onTrailingIconClick,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    isReadOnly: Boolean = false,
    errorMessage: String? = null,
    label: String = "",
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    singleLine: Boolean = false
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            readOnly = isReadOnly,
            isError = errorMessage != null,
            label = { Text(text = label) },
            leadingIcon = if (leadingIcon != null) {
                { Icon(painter = leadingIcon, contentDescription = null) }
            } else {
                null
            },
            singleLine = singleLine,
            trailingIcon = if (trailingIcon != null) {
                {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            painter = trailingIcon,
                            contentDescription = null
                        )
                    }
                }
            } else {
                null
            },
            modifier = Modifier.fillMaxWidth()
        )
        TextFieldError(
            modifier = Modifier.fillMaxWidth(),
            message = errorMessage
        )
    }
}

@Composable
fun TextFieldError(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    AnimatedVisibility(visible = message != null) {
        Text(
            text = message ?: "",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Start,
            modifier = modifier
        )
    }
}