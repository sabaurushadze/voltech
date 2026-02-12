package com.tbc.search.presentation.screen.add_to_cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.divider.Divider
import com.tbc.core_ui.components.empty_state.EmptyState
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.loading.LoadingScreen
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
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

    if (state.isLoading) {
        LoadingScreen()
    } else if (state.cartItems.isEmpty()) {
        EmptyState(
            title = stringResource(R.string.there_s_nothing_here),
            subtitle = stringResource(R.string.recently_empty_state),
            icon = R.drawable.ic_history
        )
    } else {
        AddToCartContent(
            state = state,
        )
    }
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
        }

        item {
            Spacer(Modifier.height(Dimen.size16))
        }

        items(state.cartItems){ cartItem ->
            CartItem(cartItem)
        }

        item {
            SubtotalAndCheckout(state.subtotal)
        }
    }
}

@Composable
private fun CartItem(
    cartItem: UiCartItem
) = with(cartItem){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.size16)
    ) {
        ItemDetails(cartItem)

        Spacer(Modifier.height(Dimen.size16))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.clickable{

                },
                text = stringResource(R.string.buy_it_now),
                style = VoltechTextStyle.bodyBold,
                color = VoltechColor.foregroundAccent
            )

            Text(
                modifier = Modifier
                    .clickable{ },
                text = stringResource(R.string.remove),
                style = VoltechTextStyle.bodyBold,
                color = VoltechColor.foregroundAccent
            )
        }

        Spacer(Modifier.height(Dimen.size16))

        Divider(
            dividerColor = VoltechColor.backgroundTertiary
        )

        Spacer(Modifier.height(Dimen.size16))
    }
}

@Composable
private fun ItemDetails(
    cartItem: UiCartItem
) = with(cartItem){

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        BaseAsyncImage(
            url = sellerAvatar.orEmpty(),
            modifier = Modifier
                .size(Dimen.size132)
                .clip(VoltechRadius.radius16),
        )

        Spacer(Modifier.width(Dimen.size12))

        Column {
            Text(
                text = title,
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.body,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimen.size4))

            Text(
                text = price,
                color = VoltechColor.foregroundPrimary,
                style = VoltechTextStyle.title2,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(Dimen.size12))

            SellerItem(
                sellerAvatar = sellerAvatar,
                sellerUerName = sellerUserName
            )
        }
    }
}



@Composable
private fun SubtotalAndCheckout(
    subtotal: Double
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.size24),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.subtotal),
                style = VoltechTextStyle.title3,
                color = VoltechColor.foregroundPrimary
            )

            Text(
                text = subtotal.toPriceUsStyle(),
                style = VoltechTextStyle.title2,
                color = VoltechColor.foregroundPrimary
            )
        }

        Spacer(Modifier.height(Dimen.size24))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.go_to_checkout),
            onClick = { }
        )
    }
}

@Composable
private fun SellerItem(
    sellerAvatar: String?,
    sellerUerName: String?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseAsyncImage(
            url = sellerAvatar.orEmpty(),
            modifier = Modifier
                .size(Dimen.size40)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(Dimen.size8))

        sellerUerName?.let {
            Text(
                text = sellerUerName,
                style = VoltechTextStyle.bodyBold,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}


@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    navigateBack: () -> Unit
) {
    val title = stringResource(id = R.string.voltech_shopping_cart)

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