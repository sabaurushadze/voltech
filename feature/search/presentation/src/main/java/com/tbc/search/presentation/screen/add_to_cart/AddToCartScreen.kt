package com.tbc.search.presentation.screen.add_to_cart

import android.util.Log.d
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core_ui.components.divider.Divider
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.presentation.model.cart.UiCartItem


@Composable
fun AddToCartScreen (
    viewModel: AddToCartViewModel = hiltViewModel(),
    onSetupTopBar: (TopBarState) -> Unit,
    navigateBack: () -> Unit,
){
    val onEvent = viewModel::onEvent
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onEvent(AddToCartEvent.GetCartItems)
    }

    SetupTopBar(onSetupTopBar, navigateBack)

    AddToCartContent(state)
}


@Composable
private fun  AddToCartContent(
    state: AddToCartState
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Divider()
            d("STTT", "${state.cartItems}")
        }

        items(state.cartItems){ cartItem ->
            CartItem(cartItem)
        }
    }
}

@Composable
private fun CartItem(
    cartItem: UiCartItem
) = with(cartItem){
    SellerItem(
        sellerAvatar = sellerAvatar,
        sellerUerName = sellerUserName
    )
}

@Composable
private fun SellerItem(
    sellerAvatar: String?,
    sellerUerName: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        sellerAvatar?.let {
            BaseAsyncImage(
                url = sellerAvatar,
                modifier = Modifier
                    .size(Dimen.size24)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.width(Dimen.size8))

        Text(
            text = sellerUerName,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundPrimary
        )
    }
}


@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    navigateBack: () -> Unit
) {
    val title = stringResource(id = R.string.shopping_cart)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = navigateBack
                )
            )
        )
    }
}