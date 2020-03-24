package com.hotmob.preloaddemo.home.dummy

/**
 * A dummy item representing a piece of content.
 */
data class DummyItem(val id: String, val content: String, val details: String) {
    override fun toString(): String = content
}