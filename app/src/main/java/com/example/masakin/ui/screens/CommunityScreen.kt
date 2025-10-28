@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.masakin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.masakin.data.model.Post
import com.example.masakin.viewmodel.CommunityUiState
import com.example.masakin.viewmodel.CommunityViewModel

// ===== Route (pakai VM) =====
@Composable
fun CommunityRoute(
    viewModel: CommunityViewModel,
    onBack: () -> Unit,
    onCreatePost: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.refresh() }
    val ui by viewModel.ui.collectAsStateWithLifecycle()

    CommunityScreen(
        ui = ui,
        onBack = onBack,
        onTabClick = viewModel::selectTab,
        onCreatePost = onCreatePost,
        onLike = { id -> viewModel.like(id) },
        onComment = { /* TODO */ },
        onShare = { /* TODO */ },
        onSave = { /* TODO */ },
        // kirim avatar lokal/url jika ada, contoh: R.drawable.my_avatar atau Uri
        profileImage = null
    )
}

// ===== Screen (stateless, preview-friendly) =====
@Composable
fun CommunityScreen(
    ui: CommunityUiState,
    onBack: () -> Unit,
    onTabClick: (Int) -> Unit,
    onCreatePost: () -> Unit,
    onLike: (String) -> Unit,
    onComment: (String) -> Unit,
    onShare: (String) -> Unit,
    onSave: (String) -> Unit,
    profileImage: Any? = null // bisa Int (drawable), Uri, File, atau String URL
) {
    val accent = Color(0xFFDB2A2A)
    val tabs = listOf("Recomended", "Recent")
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (profileImage == null) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = "Profile",
                                tint = Color.LightGray,
                                modifier = Modifier.size(28.dp)
                            )
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(profileImage)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text("Community", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = accent)
                    }
                },
                actions = {
                    IconButton(onClick = { /* search */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Gray)
                    }
                    IconButton(onClick = { /* add friends */ }) {
                        Icon(Icons.Filled.PersonAdd, contentDescription = "Add Friends", tint = accent)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreatePost, containerColor = accent) {
                Icon(Icons.Filled.Edit, contentDescription = "Create", tint = Color.White)
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(Color.White)
        ) {
            TabRow(
                selectedTabIndex = ui.selectedTab,
                containerColor = Color.White,
                contentColor = accent,
                indicator = { positions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(positions[ui.selectedTab])
                            .height(3.dp),
                        color = accent
                    )
                }
            ) {
                tabs.forEachIndexed { i, t ->
                    Tab(
                        selected = ui.selectedTab == i,
                        onClick = { onTabClick(i) },
                        text = {
                            Text(
                                text = t,
                                color = if (ui.selectedTab == i) accent else Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = if (ui.selectedTab == i) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when {
                ui.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                ui.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(ui.error!!, color = Color.Gray)
                }
                else -> {
                    val posts = if (ui.selectedTab == 0) ui.recommended else ui.recent
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                accent = accent,
                                onLike = { onLike(post.id) },
                                onComment = { onComment(post.id) },
                                onShare = { onShare(post.id) },
                                onSave = { onSave(post.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ===== Kartu post =====
@Composable
private fun PostCard(
    post: Post,
    accent: Color,
    onLike: () -> Unit,
    onComment: () -> Unit,
    onShare: () -> Unit,
    onSave: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.userAvatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(5.dp))
                Column(Modifier.weight(1f)) {
                    Text(post.userName, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("@${post.userHandle} • ${post.time}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                IconButton(onClick = { /* more */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            if (!post.imageUrl.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Action(icon = Icons.Outlined.ThumbUp,    count = post.likes,    tint = accent, onClick = onLike)
                Action(icon = Icons.Outlined.ModeComment, count = post.comments, tint = accent, onClick = onComment)
                IconButton(onClick = onSave) {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Save", tint = accent)
                }
                Action(icon = Icons.Outlined.Share,     count = post.shares,    tint = accent, onClick = onShare)
            }
        }
    }
}

@Composable
private fun Action(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int,
    tint: Color,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClick) { Icon(icon, contentDescription = null, tint = tint) }
        Text(count.toString(), style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray))
    }
}

// ===== Preview =====
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommunityPreview() {
    CommunityScreen(
        ui = CommunityUiState(
            selectedTab = 1, loading = false, error = null,
            recommended = samplePosts(), recent = samplePosts().reversed()
        ),
        onBack = {},
        onTabClick = {},
        onCreatePost = {},
        onLike = {}, onComment = {}, onShare = {}, onSave = {},
        profileImage = "https://picsum.photos/seed/profile/200/200"
        // contoh lokal: profileImage = R.drawable.my_avatar
    )
}

// ===== Dummy =====
private fun samplePosts(): List<Post> = listOf(
    Post("1", "", "Kartini", "kartini23", "30m",
        "Seperti ikan asin hangat di pagi hari! #RendangLovers #KulinerNusantara",
        "https://picsum.photos/seed/burger/800/500", 91, 25, 10),
    Post("2", "", "Jofi Salim", "jsalimp21", "12h",
        "Tidak ada yang lebih nikmat daripada ayam goreng penyet... #AyamGoreng",
        "https://picsum.photos/seed/ayam/800/500", 17, 12, 124)
)
