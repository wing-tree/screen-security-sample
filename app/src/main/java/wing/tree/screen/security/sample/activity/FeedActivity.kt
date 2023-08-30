package wing.tree.screen.security.sample.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import wing.tree.screen.security.sample.R
import wing.tree.screen.security.sample.model.Feed
import wing.tree.screen.security.sample.model.Feed.Companion.EXTRA_NICKNAME
import wing.tree.screen.security.sample.model.Image
import wing.tree.screen.security.sample.ui.theme.ScreenSecuritySampleTheme

class FeedActivity : ComponentActivity() {
    private val items = listOf(
        Feed(nickname = "Nakiri Ayame", image = Image(id = R.drawable.feed_nakiri_ayame)),
        Feed(nickname = "Haruhi Suzumiya")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenSecuritySampleTheme {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items) {
                        Feed(
                            feed = it,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                startActivity(
                                    Intent(
                                        this@FeedActivity,
                                        CharacterProfileActivity::class.java
                                    ).apply {
                                        putExtra(EXTRA_NICKNAME, it.nickname)
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Feed(
    feed: Feed,
    modifier: Modifier = Modifier,
    onClick: (Feed) -> Unit,
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = feed.nickname,
                    modifier = Modifier.clickable {
                        onClick(feed)
                    }
                )
            }

            feed.image?.let {
                Image(
                    painter = painterResource(id = it.id),
                    contentDescription = "Image",
                    modifier = Modifier.clickable {
                        onClick(feed)
                    }
                )
            }
        }
    }
}
