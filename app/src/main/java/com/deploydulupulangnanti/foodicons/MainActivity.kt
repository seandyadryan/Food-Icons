package com.deploydulupulangnanti.foodicons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ─── Colors ───────────────────────────────────────────────────────────────────
private val BgDark     = Color(0xFF0F1923)
private val BgCard     = Color(0xFF1A2535)
private val AccentOrange = Color(0xFFFF6B2C)
private val AccentYellow = Color(0xFFFFD93D)
private val AccentGreen  = Color(0xFF6BCB77)
private val AccentPink   = Color(0xFFFF6B9D)
private val AccentPurple = Color(0xFFB06BFF)
private val AccentBlue   = Color(0xFF4DA8DA)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFADB5BD)

// ─── Data Models ──────────────────────────────────────────────────────────────
data class FoodIcon(
    val name: String,
    val emoji: String,
    val color: Color,
    val category: String
)

data class FeatureItem(
    val icon: ImageVector,
    val title: String,
    val desc: String,
    val color: Color
)

// ─── Sample Data ──────────────────────────────────────────────────────────────
val allFoodIcons = listOf(
    FoodIcon("Burger",     "🍔", Color(0xFFFF6B2C), "Fast Food"),
    FoodIcon("Pizza",      "🍕", Color(0xFFFF4444), "Fast Food"),
    FoodIcon("Sushi",      "🍣", Color(0xFF4DA8DA), "Japanese"),
    FoodIcon("Ramen",      "🍜", Color(0xFFFFD93D), "Japanese"),
    FoodIcon("Taco",       "🌮", Color(0xFF6BCB77), "Mexican"),
    FoodIcon("Hot Dog",    "🌭", Color(0xFFFF8C42), "Fast Food"),
    FoodIcon("Fries",      "🍟", Color(0xFFFFD93D), "Fast Food"),
    FoodIcon("Donut",      "🍩", Color(0xFFFF6B9D), "Dessert"),
    FoodIcon("Ice Cream",  "🍦", Color(0xFFB06BFF), "Dessert"),
    FoodIcon("Cake",       "🎂", Color(0xFFFF6B9D), "Dessert"),
    FoodIcon("Coffee",     "☕", Color(0xFF8B5E3C), "Drinks"),
    FoodIcon("Bubble Tea", "🧋", Color(0xFF9B59B6), "Drinks"),
    FoodIcon("Juice",      "🧃", Color(0xFF6BCB77), "Drinks"),
    FoodIcon("Salad",      "🥗", Color(0xFF6BCB77), "Healthy"),
    FoodIcon("Avocado",    "🥑", Color(0xFF6BCB77), "Healthy"),
    FoodIcon("Steak",      "🥩", Color(0xFFE74C3C), "Meat"),
    FoodIcon("Chicken",    "🍗", Color(0xFFFF8C42), "Meat"),
    FoodIcon("Shrimp",     "🍤", Color(0xFFFF6B9D), "Seafood"),
    FoodIcon("Lobster",    "🦞", Color(0xFFE74C3C), "Seafood"),
    FoodIcon("Pancakes",   "🥞", Color(0xFFFFD93D), "Breakfast"),
    FoodIcon("Waffle",     "🧇", Color(0xFFFF8C42), "Breakfast"),
    FoodIcon("Egg",        "🍳", Color(0xFFFFD93D), "Breakfast"),
    FoodIcon("Croissant",  "🥐", Color(0xFFD4A017), "Breakfast"),
    FoodIcon("Bento",      "🍱", Color(0xFF4DA8DA), "Japanese"),
)

val categories = listOf("All", "Fast Food", "Japanese", "Dessert", "Drinks", "Healthy", "Meat", "Seafood", "Breakfast", "Mexican")

val featureItems = listOf(
    FeatureItem(Icons.Default.Collections, "1500+ Icons", "Premium food icons in every category", AccentOrange),
    FeatureItem(Icons.Default.Palette, "Custom Colors", "Match icons to any theme or wallpaper", AccentPurple),
    FeatureItem(Icons.Default.Search, "Fast Search", "Find your icon instantly by name", AccentBlue),
    FeatureItem(Icons.Default.Download, "Easy Apply", "One-tap apply via any launcher", AccentGreen),
    FeatureItem(Icons.Default.Update, "Regular Updates", "New icons added every week", AccentYellow),
    FeatureItem(Icons.Default.Translate, "Multi-Language", "Available in 50+ languages", AccentPink),
)

// ─── Activity ─────────────────────────────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodIconsApp()
        }
    }
}

@Composable
fun FoodIconsApp() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background   = BgDark,
            surface      = BgCard,
            primary      = AccentOrange,
            onBackground = TextPrimary,
            onSurface    = TextPrimary
        )
    ) {
        var selectedTab by remember { mutableStateOf(0) }

        Scaffold(
            containerColor = BgDark,
            bottomBar = {
                BottomNavBar(selectedTab) { selectedTab = it }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTab) {
                    0 -> HomeScreen()
                    1 -> IconsScreen()
                    2 -> SearchScreen()
                    3 -> AboutScreen()
                }
            }
        }
    }
}

// ─── Bottom Nav ───────────────────────────────────────────────────────────────
@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        Pair(Icons.Default.Home, "Home"),
        Pair(Icons.Default.GridView, "Icons"),
        Pair(Icons.Default.Search, "Search"),
        Pair(Icons.Default.Info, "About")
    )
    NavigationBar(containerColor = BgCard, tonalElevation = 0.dp) {
        items.forEachIndexed { i, (icon, label) ->
            NavigationBarItem(
                selected = selected == i,
                onClick  = { onSelect(i) },
                icon     = { Icon(icon, contentDescription = label) },
                label    = { Text(label, fontSize = 11.sp) },
                colors   = NavigationBarItemDefaults.colors(
                    selectedIconColor   = AccentOrange,
                    selectedTextColor   = AccentOrange,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = AccentOrange.copy(alpha = 0.15f)
                )
            )
        }
    }
}

// ─── HOME SCREEN ──────────────────────────────────────────────────────────────
@Composable
fun HomeScreen() {
    // Animated hero emoji
    var emojiIndex by remember { mutableStateOf(0) }
    val heroEmojis = listOf("🍔","🍕","🍣","🍜","🍩","🧋","🍗","🥑")
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            emojiIndex = (emojiIndex + 1) % heroEmojis.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Hero Section
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(36.dp))
                .background(
                    Brush.radialGradient(listOf(AccentOrange, Color(0xFFFF3D00)))
                )
                .shadow(24.dp, RoundedCornerShape(36.dp)),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = emojiIndex, label = "emoji") { idx ->
                Text(heroEmojis[idx], fontSize = 72.sp)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Food Icons Pack",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            "1500+ Premium Food Icons for your Home Screen",
            fontSize = 15.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp, bottom = 28.dp)
        )

        // Stats Row
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard("1500+", "Icons",     AccentOrange, Modifier.weight(1f))
            StatCard("50+",   "Languages", AccentPurple, Modifier.weight(1f))
            StatCard("4.9★",  "Rating",    AccentYellow, Modifier.weight(1f))
        }

        Spacer(Modifier.height(28.dp))

        // Features Grid
        Text(
            "✨ Features",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            featureItems.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { feature ->
                        FeatureCard(feature, Modifier.weight(1f))
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // CTA Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors  = ButtonDefaults.buttonColors(containerColor = AccentOrange),
            shape   = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Download, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Apply Icons Now", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun StatCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = BgCard)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(label, fontSize = 12.sp, color = TextSecondary)
        }
    }
}

@Composable
fun FeatureCard(feature: FeatureItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = BgCard)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(feature.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(feature.icon, contentDescription = null, tint = feature.color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(10.dp))
            Text(feature.title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
            Text(feature.desc, color = TextSecondary, fontSize = 11.sp, maxLines = 2)
        }
    }
}

// ─── ICONS SCREEN ─────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsScreen() {
    var selectedCategory by remember { mutableStateOf("All") }

    val filtered = remember(selectedCategory) {
        if (selectedCategory == "All") allFoodIcons
        else allFoodIcons.filter { it.category == selectedCategory }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Icons", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary, modifier = Modifier.weight(1f))
            Text("${allFoodIcons.size}+ icons", fontSize = 13.sp, color = AccentOrange, fontWeight = FontWeight.Bold)
        }

        // Category chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick  = { selectedCategory = cat },
                    label    = { Text(cat, fontSize = 13.sp) },
                    colors   = FilterChipDefaults.filterChipColors(
                        selectedContainerColor    = AccentOrange,
                        selectedLabelColor        = Color.White,
                        containerColor            = BgCard,
                        labelColor                = TextSecondary
                    )
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement   = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered) { icon ->
                IconGridItem(icon)
            }
        }
    }
}

@Composable
fun IconGridItem(food: FoodIcon) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.88f else 1f, label = "iconScale")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(78.dp)
                .scale(scale)
                .clip(RoundedCornerShape(20.dp))
                .background(food.color.copy(alpha = 0.18f))
                .clickable {
                    pressed = true
                },
            contentAlignment = Alignment.Center
        ) {
            Text(food.emoji, fontSize = 38.sp, textAlign = TextAlign.Center)
        }
        Spacer(Modifier.height(6.dp))
        Text(
            food.name,
            color     = TextSecondary,
            fontSize  = 11.sp,
            textAlign = TextAlign.Center,
            maxLines  = 1,
            overflow  = TextOverflow.Ellipsis
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) { delay(150); pressed = false }
    }
}

// ─── SEARCH SCREEN ────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    val results = remember(query) {
        if (query.isBlank()) emptyList()
        else allFoodIcons.filter { it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Text("Search", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value         = query,
            onValueChange = { query = it },
            modifier      = Modifier.fillMaxWidth(),
            placeholder   = { Text("Search food icons...", color = TextSecondary) },
            leadingIcon   = { Icon(Icons.Default.Search, null, tint = AccentOrange) },
            trailingIcon  = if (query.isNotEmpty()) {{ IconButton({ query = "" }) { Icon(Icons.Default.Clear, null, tint = TextSecondary) } }} else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = AccentOrange,
                unfocusedBorderColor = BgCard,
                focusedTextColor     = TextPrimary,
                unfocusedTextColor   = TextPrimary,
                cursorColor          = AccentOrange,
                unfocusedContainerColor = BgCard,
                focusedContainerColor   = BgCard
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        if (query.isBlank()) {
            // Show popular categories
            Text("Popular Categories", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("🍔 Fast Food","🍱 Japanese","🍩 Dessert","☕ Drinks","🥗 Healthy","🥩 Meat").forEach { cat ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(12.dp),
                        colors   = CardDefaults.cardColors(containerColor = BgCard),
                        onClick  = { query = cat.substring(2).trim() }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(cat, color = TextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ChevronRight, null, tint = TextSecondary)
                        }
                    }
                }
            }
        } else {
            Text("${results.size} results for \"$query\"", fontSize = 14.sp, color = TextSecondary)
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement   = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { icon -> IconGridItem(icon) }
            }
        }
    }
}

// ─── ABOUT SCREEN ─────────────────────────────────────────────────────────────
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(listOf(AccentOrange, Color(0xFFFF3D00)))),
            contentAlignment = Alignment.Center
        ) {
            Text("🍔", fontSize = 52.sp)
        }

        Spacer(Modifier.height(16.dp))
        Text("Food Icons Pack", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        Text("Version 1.0.0", fontSize = 13.sp, color = TextSecondary)
        Text("by Deploy Dulu", fontSize = 13.sp, color = AccentOrange, modifier = Modifier.padding(top = 2.dp))

        Spacer(Modifier.height(28.dp))

        // Info cards
        listOf(
            Triple(Icons.Default.Email,   "Contact", "seandyadryan@gmail.com"),
            Triple(Icons.Default.Policy,  "Privacy Policy", "Available on GitHub"),
            Triple(Icons.Default.Star,    "Rate Us", "Rate on Google Play"),
            Triple(Icons.Default.Share,   "Share", "Share with friends")
        ).forEach { (icon, title, sub) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                shape    = RoundedCornerShape(14.dp),
                colors   = CardDefaults.cardColors(containerColor = BgCard)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AccentOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, null, tint = AccentOrange, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(sub, color = TextSecondary, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = TextSecondary)
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("© 2026 Deploy Dulu. All rights reserved.", fontSize = 12.sp, color = TextSecondary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
    }
}
