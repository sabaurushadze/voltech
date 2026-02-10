package com.tbc.selling.presentation.screen.add_item

import android.net.Uri
import com.tbc.core.domain.model.category.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location


sealed class AddItemEvent {
//    Navigation
    data object NavigateBackToMyItems : AddItemEvent()
//    Category
    data class SelectCategory(val category: Category) : AddItemEvent()
    data class SelectCondition(val condition: Condition) : AddItemEvent()
    data class SelectLocation(val location: Location) : AddItemEvent()

//    Images
    data class OnImagesSelected(val uris: List<Uri>) : AddItemEvent()
    data class DeleteImageFromPreview(val uri: Uri) : AddItemEvent()
    data object UploadImages : AddItemEvent()
    data object LaunchGallery : AddItemEvent()
    data object ResetImageError : AddItemEvent()

//
    data class TitleChanged(val title: String) : AddItemEvent()
    data class DescriptionChanged(val description: String) : AddItemEvent()
    data class PriceChanged(val price: String) : AddItemEvent()
    data class QuantityChanged(val quantity: String) : AddItemEvent()
    data object ClearDescription : AddItemEvent()

//
    data object AddItem : AddItemEvent()

}