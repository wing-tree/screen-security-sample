package wing.tree.screen.security.sample.model

data class Feed(
    val nickname: String,
    val image: Image? = null
) {
    companion object {
        const val EXTRA_NICKNAME = "nickname"
    }
}
