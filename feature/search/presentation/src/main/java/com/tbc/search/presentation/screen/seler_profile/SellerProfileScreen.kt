package com.tbc.search.presentation.screen.seler_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
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
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.image.ProfilePicturePlaceholder
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.presentation.model.seller_profile.seller.SellerProfileTab
import com.tbc.search.presentation.model.seller_profile.seller_product.UiSellerProductItem

@Composable
fun SellerProfileScreen(
    viewModel: SellerProfileViewModel = hiltViewModel(),
    sellerUid: String,
    navigateToFeedWithUid: (String) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.UpdateSellerUid(sellerUid))
        onEvent(SellerProfileEvent.GetSeller)
    }

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.GetReviews)
    }

    LaunchedEffect(Unit) {
        onEvent(SellerProfileEvent.GetSellerLimitedItems)
    }

    SellerProfileContent(
        state = state,
        onEvent = onEvent,
        navigateToFeedWithUid = navigateToFeedWithUid
    )
}

@Composable
private fun SellerProfileContent(
    state: SellerProfileState,
    navigateToFeedWithUid: (String) -> Unit,
    onEvent: (SellerProfileEvent) -> Unit,
){
    TopBarNavigation(
        state = state,
        onEvent = onEvent,
        navigateToFeedWithUid = navigateToFeedWithUid,
    )
}

@Composable
private fun TopBarNavigation(
    state: SellerProfileState,
    onEvent: (SellerProfileEvent) -> Unit,
    navigateToFeedWithUid: (String) -> Unit
) {
    val tabs = SellerProfileTab.entries
    val pagerState = rememberPagerState { tabs.size }

    LaunchedEffect(state.selectedTab) {
        val index = state.selectedTab.ordinal
        if (pagerState.currentPage != index) {
            pagerState.animateScrollToPage(index)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val currentTab = tabs[pagerState.currentPage]
        if (state.selectedTab != currentTab) {
            onEvent(SellerProfileEvent.SelectTab(currentTab))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.seller?.let {
            SellerItem(
                sellerAvatar = state.seller.sellerPhotoUrl,
                sellerUserName = state.seller.sellerName
            )
        }

        PrimaryTabRow(
            selectedTabIndex = state.selectedTab.ordinal,
            contentColor = VoltechColor.foregroundPrimary,
            containerColor = VoltechColor.backgroundPrimary,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex = state.selectedTab.ordinal
                    ),
                    color = VoltechColor.foregroundAccent
                )
            }
        ) {
            tabs.forEach { tab ->
                val isSelected = state.selectedTab == tab
                Tab(
                    selected = state.selectedTab == tab,
                    onClick = {
                        onEvent(SellerProfileEvent.SelectTab(tab))
                    },
                    text = {
                        Text(
                            text = tab.title,
                            style = if (isSelected){
                                VoltechTextStyle.title3
                            }else{
                                VoltechTextStyle.body
                            }
                        )
                    },
                    selectedContentColor = VoltechColor.foregroundPrimary,
                    unselectedContentColor = VoltechColor.foregroundSecondary
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            when (tabs[page]) {
                SellerProfileTab.STORE -> {
                    StoreContent(
                        state = state,
                        navigateToFeedWithUid = navigateToFeedWithUid
                    )
                }

                SellerProfileTab.REVIEWS -> {
                    Text("Reviews List for ${state.seller?.sellerName}")
                }
            }
        }
    }
}

@Composable
private fun StoreContent(
    state: SellerProfileState,
    navigateToFeedWithUid: (String) -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.size16)
    ) {

        Spacer(Modifier.height(Dimen.size16))

        Text(
            text = stringResource(R.string.latest),
            style = VoltechTextStyle.title2,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(Modifier.height(Dimen.size16))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = Dimen.size16),
            horizontalArrangement = Arrangement.spacedBy(Dimen.size12),
            verticalArrangement = Arrangement.spacedBy(Dimen.size12)
        ) {
            items(state.sellerProductItem) { sellerProduct ->
                RecentlyViewedItem(
                    recentlyViewedItems = sellerProduct
                )
            }
        }

        Spacer(Modifier.height(Dimen.size16))

        if (state.sellerProductItem.size >= 4 ){
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.see_all),
                onClick = { navigateToFeedWithUid(state.sellerUid)}
            )
        }
    }

}

@Composable
private fun RecentlyViewedItem(
    recentlyViewedItems: UiSellerProductItem,
//    navigateToItemDetails: (Int) -> Unit,
) = with(recentlyViewedItems) {
    Column(
        modifier = Modifier
            .width(Dimen.size150)
//            .clickable { navigateToItemDetails(id) }
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.size150)
                .clip(VoltechRadius.radius24)
                .background(VoltechFixedColor.lightGray)
        ) {
            images.firstOrNull()?.let { firstImageUrl ->
                BaseAsyncImage(
                    url = firstImageUrl,
                )
            }
        }

        Spacer(Modifier.height(Dimen.size24))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = VoltechTextStyle.body,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(Modifier.height(Dimen.size4))

        Text(
            text = price,
            style = VoltechTextStyle.title3,
            color = VoltechColor.foregroundPrimary
        )
    }
}

@Composable
private fun SellerItem(
    sellerAvatar: String?,
    sellerUserName: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VoltechColor.backgroundTertiary)
            .padding(all = Dimen.size16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BaseAsyncImage(
            modifier = Modifier
                .size(Dimen.size48)
                .clip(CircleShape),
            url = sellerAvatar,
            fallback = {
                ProfilePicturePlaceholder(
                    text = sellerUserName,
                )
            }
        )

        Spacer(Modifier.width(Dimen.size8))

        sellerUserName?.let {
            Text(
                text = sellerUserName,
                style = VoltechTextStyle.title3,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}
