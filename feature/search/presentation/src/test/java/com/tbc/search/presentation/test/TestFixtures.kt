package com.tbc.search.presentation.test

import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.model.user.User
import com.tbc.search.domain.model.cart.Cart
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.model.review.response.ReviewRating
import com.tbc.search.domain.model.review.response.ReviewResponse
import com.tbc.selling.domain.model.SellerResponse

object TestFixtures {

    val user = User(uid = "user1", name = "Test", photoUrl = null)

    val feedItem = FeedItem(
        id = 10,
        uid = "seller1",
        title = "Item",
        category = Category.GPU,
        condition = Condition.NEW,
        price = 100.0,
        images = listOf("img1", "img2"),
        quantity = 1,
        location = Location.DIDI_DIGHOMI,
        userDescription = "desc",
        active = true,
    )

    val feedItemEmptyImages = feedItem.copy(images = emptyList())

    val feedItemMinimal = FeedItem(
        id = 1,
        uid = "uid1",
        title = "Test Item",
        category = Category.GPU,
        condition = Condition.NEW,
        price = 100.0,
        images = emptyList(),
        quantity = 1,
        location = Location.DIDI_DIGHOMI,
        userDescription = "desc",
        active = true,
    )

    val sellerResponse = SellerResponse(
        id = 1,
        uid = "seller1",
        positive = 8,
        neutral = 1,
        negative = 1,
        sellerPhotoUrl = "photo",
        sellerName = "Seller",
    )

    val sellerResponseZeroFeedback = SellerResponse(
        id = 1,
        uid = "seller1",
        positive = 0,
        neutral = 0,
        negative = 0,
        sellerPhotoUrl = "photo",
        sellerName = "Seller",
    )

    val review = ReviewResponse(
        id = 1,
        itemId = 10,
        uid = "seller1",
        reviewerUid = "user1",
        reviewerUserName = "User1",
        comment = "Good",
        rating = ReviewRating.POSITIVE,
        reviewAt = "2024-01-02T10:00:00Z",
        title = "Title1",
    )

    val review2 = ReviewResponse(
        id = 2,
        itemId = 10,
        uid = "seller1",
        reviewerUid = "user2",
        reviewerUserName = "User2",
        comment = "Bad",
        rating = ReviewRating.NEGATIVE,
        reviewAt = "2024-01-01T10:00:00Z",
        title = "Title2",
    )

    val review3 = ReviewResponse(
        id = 3,
        itemId = 10,
        uid = "seller1",
        reviewerUid = "user3",
        reviewerUserName = "User3",
        comment = "Ok",
        rating = ReviewRating.NEUTRAL,
        reviewAt = "2024-01-03T10:00:00Z",
        title = "Title3",
    )

    val cart = Cart(id = 1, uid = "user1", itemId = 10)

    val recently = Recently(id = 1, uid = "user1", itemId = 10)
}
