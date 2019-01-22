package com.hotmob.sdk.hotmobsdkshowcase.banner.recycleview.dummy

/**
 * A dummy item representing a piece of content.
 */
data class DummyItem(val id: String, val content: String) {
    override fun toString(): String = content
}
