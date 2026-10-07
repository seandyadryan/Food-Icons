package com.deploydulupulangnanti.foodicons

import android.app.LocaleManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.delay

// ─── Theme Colors ─────────────────────────────────────────────────────────────
val BgDark        = Color(0xFF0F1923)
val BgCard        = Color(0xFF1A2535)
val TextPrimary   = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFADB5BD)

data class AppThemeColor(val name: String, val color: Color, val dark: Color)
val themeColors = listOf(
    AppThemeColor("Orange",  Color(0xFFFF6B2C), Color(0xFFCC4500)),
    AppThemeColor("Red",     Color(0xFFE74C3C), Color(0xFFB03A2E)),
    AppThemeColor("Purple",  Color(0xFF9B59B6), Color(0xFF7D3C98)),
    AppThemeColor("Blue",    Color(0xFF3498DB), Color(0xFF1A6FA3)),
    AppThemeColor("Green",   Color(0xFF27AE60), Color(0xFF1E8449)),
    AppThemeColor("Pink",    Color(0xFFE91E8C), Color(0xFFB5156C)),
    AppThemeColor("Yellow",  Color(0xFFF1C40F), Color(0xFFD4AC0D)),
    AppThemeColor("Teal",    Color(0xFF1ABC9C), Color(0xFF148F77)),
)

// ─── Language Data ─────────────────────────────────────────────────────────────
data class LanguageItem(val name: String, val localName: String, val tag: String, val flag: String)
val languages = listOf(
    LanguageItem("English",    "English",          "en", "🇺🇸"),
    LanguageItem("Indonesian", "Bahasa Indonesia", "id", "🇮🇩"),
    LanguageItem("Spanish",    "Español",          "es", "🇪🇸"),
    LanguageItem("French",     "Français",         "fr", "🇫🇷"),
    LanguageItem("German",     "Deutsch",          "de", "🇩🇪"),
    LanguageItem("Japanese",   "日本語",            "ja", "🇯🇵"),
    LanguageItem("Korean",     "한국어",            "ko", "🇰🇷"),
    LanguageItem("Chinese",    "中文",              "zh", "🇨🇳"),
    LanguageItem("Arabic",     "العربية",          "ar", "🇸🇦"),
    LanguageItem("Portuguese", "Português",        "pt", "🇧🇷"),
    LanguageItem("Russian",    "Русский",          "ru", "🇷🇺"),
    LanguageItem("Thai",       "ภาษาไทย",          "th", "🇹🇭"),
    LanguageItem("Vietnamese", "Tiếng Việt",       "vi", "🇻🇳"),
    LanguageItem("Hindi",      "हिन्दी",            "hi", "🇮🇳"),
    LanguageItem("Italian",    "Italiano",         "it", "🇮🇹"),
)

// ─── Food Data ────────────────────────────────────────────────────────────────
data class FoodIcon(
    val name: String,
    val emoji: String,
    val color: Color,
    val category: String,
    val country: String,
    val countryFlag: String,
    val drawableRes: Int = R.drawable.food_burger
)

val allFoodIcons = listOf(
    // 🇮🇩 Indonesia
    FoodIcon("Nasi Goreng",   "🍳", Color(0xFFFF8C42), "Rice",     "Indonesia", "🇮🇩", R.drawable.food_egg),
    FoodIcon("Rendang",       "🥩", Color(0xFFB5451B), "Meat",     "Indonesia", "🇮🇩", R.drawable.food_meat),
    FoodIcon("Sate",          "🍢", Color(0xFFD4A017), "Grilled",  "Indonesia", "🇮🇩", R.drawable.food_meat),
    FoodIcon("Gado-Gado",     "🥗", Color(0xFF6BCB77), "Salad",    "Indonesia", "🇮🇩", R.drawable.food_avocado),
    FoodIcon("Soto Ayam",     "🍲", Color(0xFFFFD93D), "Soup",     "Indonesia", "🇮🇩", R.drawable.food_ramen),
    FoodIcon("Bakso",         "🍡", Color(0xFFFF6B9D), "Soup",     "Indonesia", "🇮🇩", R.drawable.food_ramen),
    FoodIcon("Nasi Padang",   "🍛", Color(0xFFFF6B2C), "Rice",     "Indonesia", "🇮🇩", R.drawable.food_meat),
    FoodIcon("Pempek",        "🐟", Color(0xFF4DA8DA), "Seafood",  "Indonesia", "🇮🇩", R.drawable.food_sushi),
    // 🇯🇵 Japan
    FoodIcon("Sushi",         "🍣", Color(0xFF4DA8DA), "Seafood",  "Japan",     "🇯🇵", R.drawable.food_sushi),
    FoodIcon("Ramen",         "🍜", Color(0xFFFFD93D), "Noodles",  "Japan",     "🇯🇵", R.drawable.food_ramen),
    FoodIcon("Tempura",       "🍤", Color(0xFFFF8C42), "Fried",    "Japan",     "🇯🇵", R.drawable.food_fries),
    FoodIcon("Takoyaki",      "🐙", Color(0xFFB06BFF), "Snack",    "Japan",     "🇯🇵", R.drawable.food_donut),
    FoodIcon("Onigiri",       "🍙", Color(0xFFFFFFFF), "Rice",     "Japan",     "🇯🇵", R.drawable.food_sushi),
    FoodIcon("Mochi",         "🍡", Color(0xFFFF6B9D), "Dessert",  "Japan",     "🇯🇵", R.drawable.food_donut),
    FoodIcon("Gyoza",         "🥟", Color(0xFFFF8C42), "Dumpling", "Japan",     "🇯🇵", R.drawable.food_taco),
    // 🇺🇸 USA
    FoodIcon("Burger",        "🍔", Color(0xFFFF6B2C), "Fast Food","USA",       "🇺🇸", R.drawable.food_burger),
    FoodIcon("Hot Dog",       "🌭", Color(0xFFFF8C42), "Fast Food","USA",       "🇺🇸", R.drawable.food_burger),
    FoodIcon("Fries",         "🍟", Color(0xFFFFD93D), "Fast Food","USA",       "🇺🇸", R.drawable.food_fries),
    FoodIcon("BBQ Ribs",      "🥩", Color(0xFFE74C3C), "Grilled",  "USA",       "🇺🇸", R.drawable.food_meat),
    FoodIcon("Pancakes",      "🥞", Color(0xFFFF8C42), "Breakfast","USA",       "🇺🇸", R.drawable.food_donut),
    FoodIcon("Apple Pie",     "🥧", Color(0xFFD4A017), "Dessert",  "USA",       "🇺🇸", R.drawable.food_pizza),
    FoodIcon("Donut",         "🍩", Color(0xFFFF6B9D), "Dessert",  "USA",       "🇺🇸", R.drawable.food_donut),
    // 🇮🇹 Italy
    FoodIcon("Pizza",         "🍕", Color(0xFFFF4444), "Italian",  "Italy",     "🇮🇹", R.drawable.food_pizza),
    FoodIcon("Pasta",         "🍝", Color(0xFFD4A017), "Italian",  "Italy",     "🇮🇹", R.drawable.food_ramen),
    FoodIcon("Lasagna",       "🫕", Color(0xFFFF6B2C), "Italian",  "Italy",     "🇮🇹", R.drawable.food_pizza),
    FoodIcon("Tiramisu",      "🍰", Color(0xFF8B5E3C), "Dessert",  "Italy",     "🇮🇹", R.drawable.food_cake),
    FoodIcon("Gelato",        "🍦", Color(0xFFFF6B9D), "Dessert",  "Italy",     "🇮🇹", R.drawable.food_icecream),
    FoodIcon("Espresso",      "☕", Color(0xFF5C3317), "Drinks",   "Italy",     "🇮🇹", R.drawable.food_coffee),
    // 🇲🇽 Mexico
    FoodIcon("Taco",          "🌮", Color(0xFF6BCB77), "Mexican",  "Mexico",    "🇲🇽", R.drawable.food_taco),
    FoodIcon("Burrito",       "🌯", Color(0xFFFF8C42), "Mexican",  "Mexico",    "🇲🇽", R.drawable.food_taco),
    FoodIcon("Nachos",        "🧀", Color(0xFFFFD93D), "Snack",    "Mexico",    "🇲🇽", R.drawable.food_taco),
    FoodIcon("Guacamole",     "🥑", Color(0xFF6BCB77), "Dip",      "Mexico",    "🇲🇽", R.drawable.food_avocado),
    FoodIcon("Quesadilla",    "🫓", Color(0xFFFFD93D), "Mexican",  "Mexico",    "🇲🇽", R.drawable.food_taco),
    // 🇰🇷 Korea
    FoodIcon("Bibimbap",      "🍱", Color(0xFFFF6B2C), "Rice",     "Korea",     "🇰🇷", R.drawable.food_egg),
    FoodIcon("Korean BBQ",    "🥩", Color(0xFFE74C3C), "Grilled",  "Korea",     "🇰🇷", R.drawable.food_meat),
    FoodIcon("Kimchi",        "🥬", Color(0xFFE74C3C), "Side",     "Korea",     "🇰🇷", R.drawable.food_avocado),
    FoodIcon("Tteokbokki",   "🍡", Color(0xFFFF4444), "Snack",    "Korea",     "🇰🇷", R.drawable.food_donut),
    FoodIcon("Japchae",       "🍜", Color(0xFF9B59B6), "Noodles",  "Korea",     "🇰🇷", R.drawable.food_ramen),
    // 🇨🇳 China
    FoodIcon("Dim Sum",       "🥟", Color(0xFFFF8C42), "Dumpling", "China",     "🇨🇳", R.drawable.food_taco),
    FoodIcon("Peking Duck",   "🦆", Color(0xFFD4A017), "Meat",     "China",     "🇨🇳", R.drawable.food_meat),
    FoodIcon("Fried Rice",    "🍚", Color(0xFFFFD93D), "Rice",     "China",     "🇨🇳", R.drawable.food_egg),
    FoodIcon("Hot Pot",       "🫕", Color(0xFFE74C3C), "Soup",     "China",     "🇨🇳", R.drawable.food_ramen),
    FoodIcon("Spring Roll",   "🥠", Color(0xFFFF8C42), "Fried",    "China",     "🇨🇳", R.drawable.food_taco),
    // 🇮🇳 India
    FoodIcon("Curry",         "🍛", Color(0xFFFF8C42), "Curry",    "India",     "🇮🇳", R.drawable.food_meat),
    FoodIcon("Samosa",        "🔺", Color(0xFFD4A017), "Snack",    "India",     "🇮🇳", R.drawable.food_taco),
    FoodIcon("Naan",          "🫓", Color(0xFFD4A017), "Bread",    "India",     "🇮🇳", R.drawable.food_taco),
    FoodIcon("Biryani",       "🍛", Color(0xFFFF6B2C), "Rice",     "India",     "🇮🇳", R.drawable.food_meat),
    FoodIcon("Mango Lassi",   "🥭", Color(0xFFFF8C42), "Drinks",   "India",     "🇮🇳", R.drawable.food_drink),
    // 🇹🇭 Thailand
    FoodIcon("Pad Thai",      "🍜", Color(0xFFFF8C42), "Noodles",  "Thailand",  "🇹🇭", R.drawable.food_ramen),
    FoodIcon("Tom Yum",       "🍲", Color(0xFFE74C3C), "Soup",     "Thailand",  "🇹🇭", R.drawable.food_ramen),
    FoodIcon("Mango Sticky Rice","🍚",Color(0xFFFFD93D),"Dessert", "Thailand",  "🇹🇭", R.drawable.food_donut),
    FoodIcon("Green Curry",   "🟢", Color(0xFF6BCB77), "Curry",    "Thailand",  "🇹🇭", R.drawable.food_meat),
    // 🇫🇷 France
    FoodIcon("Croissant",     "🥐", Color(0xFFD4A017), "Breakfast","France",    "🇫🇷", R.drawable.food_donut),
    FoodIcon("Crepe",         "🫓", Color(0xFFFF8C42), "Dessert",  "France",    "🇫🇷", R.drawable.food_donut),
    FoodIcon("Baguette",      "🥖", Color(0xFFD4A017), "Bread",    "France",    "🇫🇷", R.drawable.food_taco),
    FoodIcon("Macarons",      "🍬", Color(0xFFFF6B9D), "Dessert",  "France",    "🇫🇷", R.drawable.food_donut),
    FoodIcon("Croquet Monsieur","🥪",Color(0xFFFFD93D),"Sandwich", "France",    "🇫🇷", R.drawable.food_burger),
)

val allCountries = listOf("All Countries", "Indonesia", "Japan", "USA", "Italy", "Mexico", "Korea", "China", "India", "Thailand", "France")
val allCategories = listOf("All", "Fast Food", "Rice", "Noodles", "Meat", "Soup", "Seafood", "Dessert", "Drinks", "Snack", "Breakfast", "Grilled", "Italian", "Mexican", "Curry")

// ─── Launcher Apply Definitions ────────────────────────────────────────────────
enum class LauncherApplyMethod {
    INTENT_EXTRA,
    LAUNCHER_SETTINGS,
    SAMSUNG_THEME_PARK,
    SYSTEM_THEMES,
    GENERAL_GUIDE
}

data class LauncherInfo(
    val name: String,
    val emoji: String,
    val packageNames: List<String>,
    val applyMethod: LauncherApplyMethod,
    val action: String? = null,
    val extraKey: String? = null,
    val guide: String? = null
)

val supportedLaunchers = listOf(
    LauncherInfo(
        name = "Nova Launcher",
        emoji = "🚀",
        packageNames = listOf("com.teslacoilsw.launcher"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "com.teslacoilsw.launcher.APPLY_ICON_THEME",
        extraKey = "com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE"
    ),
    LauncherInfo(
        name = "Smart Launcher",
        emoji = "✨",
        packageNames = listOf("ginlemon.flowerfree", "smartlauncher.net"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "ginlemon.smartlauncher.setGSLTHEME",
        extraKey = "package"
    ),
    LauncherInfo(
        name = "Action Launcher",
        emoji = "⚡",
        packageNames = listOf("com.actionlauncher.playstore"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "com.actionlauncher.playstore.APPLY_ICON_THEME",
        extraKey = "package"
    ),
    LauncherInfo(
        name = "Lawnchair Launcher",
        emoji = "🌿",
        packageNames = listOf("app.lawnchair", "ch.deletescape.lawnchair.plah", "ch.deletescape.lawnchair"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "ch.deletescape.lawnchair.APPLY_ICONS",
        extraKey = "package"
    ),
    LauncherInfo(
        name = "Apex Launcher",
        emoji = "🔺",
        packageNames = listOf("com.anddoes.launcher"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "com.anddoes.launcher.SET_THEME",
        extraKey = "com.anddoes.launcher.THEME_PACKAGE_NAME"
    ),
    LauncherInfo(
        name = "ADW Launcher",
        emoji = "📱",
        packageNames = listOf("org.adw.launcher", "org.adwfreak.launcher"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "org.adw.launcher.SET_THEME",
        extraKey = "org.adw.launcher.theme.NAME"
    ),
    LauncherInfo(
        name = "Niagara Launcher",
        emoji = "🌊",
        packageNames = listOf("bitpit.launcher"),
        applyMethod = LauncherApplyMethod.INTENT_EXTRA,
        action = "bitpit.launcher.APPLY_ICONS",
        extraKey = "package"
    ),
    LauncherInfo(
        name = "Samsung One UI (Theme Park)",
        emoji = "🌌",
        packageNames = listOf("com.samsung.android.themedesigner", "com.sec.android.app.launcher"),
        applyMethod = LauncherApplyMethod.SAMSUNG_THEME_PARK,
        guide = "Buka Theme Park > Icon > Create New > Iconpack > Pilih Food Icons > Apply."
    ),
    LauncherInfo(
        name = "Xiaomi / POCO / HyperOS",
        emoji = "🎯",
        packageNames = listOf("com.mi.android.globalminusscreen", "com.miui.home"),
        applyMethod = LauncherApplyMethod.SYSTEM_THEMES,
        guide = "Buka Pengaturan > Tema/Personalisasi > Ikon > Pilih Food Icons."
    ),
    LauncherInfo(
        name = "OnePlus / Oppo / Realme / Nothing",
        emoji = "🔴",
        packageNames = listOf("net.oneplus.launcher", "com.oppo.launcher", "com.nothing.launcher"),
        applyMethod = LauncherApplyMethod.SYSTEM_THEMES,
        guide = "Buka Pengaturan HP > Personalisasi > Icon Pack > Pilih Food Icons."
    )
)

// ─── Activity ─────────────────────────────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var accentIndex by remember { mutableStateOf(0) }
            val accent = themeColors[accentIndex]
            FoodIconsApp(accent, accentIndex) { accentIndex = it }
        }
    }
}

// ─── App Root ─────────────────────────────────────────────────────────────────
@Composable
fun FoodIconsApp(
    accentTheme: AppThemeColor,
    accentIndex: Int,
    onAccentChange: (Int) -> Unit
) {
    val accent = accentTheme.color
    MaterialTheme(
        colorScheme = darkColorScheme(
            background   = BgDark,
            surface      = BgCard,
            primary      = accent,
            onBackground = TextPrimary,
            onSurface    = TextPrimary
        )
    ) {
        var selectedTab by remember { mutableStateOf(0) }
        var showApplySheet by remember { mutableStateOf(false) }

        Scaffold(
            containerColor = BgDark,
            bottomBar = { BottomNavBar(selectedTab, accent) { selectedTab = it } }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTab) {
                    0 -> HomeScreen(accent) { showApplySheet = true }
                    1 -> IconsScreen(accent)
                    2 -> SearchScreen(accent)
                    3 -> SettingsScreen(accent, accentIndex, onAccentChange)
                }
            }
        }

        if (showApplySheet) {
            ApplyBottomSheet(accent) { showApplySheet = false }
        }
    }
}

// ─── Bottom Nav ───────────────────────────────────────────────────────────────
@Composable
fun BottomNavBar(selected: Int, accent: Color, onSelect: (Int) -> Unit) {
    val items = listOf(
        Icons.Default.Home to "Home",
        Icons.Default.GridView to "Icons",
        Icons.Default.Search to "Search",
        Icons.Default.Settings to "Settings"
    )
    NavigationBar(containerColor = BgCard, tonalElevation = 0.dp) {
        items.forEachIndexed { i, (icon, label) ->
            NavigationBarItem(
                selected = selected == i, onClick = { onSelect(i) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = accent, selectedTextColor = accent,
                    unselectedIconColor = TextSecondary, unselectedTextColor = TextSecondary,
                    indicatorColor = accent.copy(alpha = 0.15f)
                )
            )
        }
    }
}

// ─── HOME SCREEN ──────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(accent: Color, onApplyClick: () -> Unit) {
    var emojiIndex by remember { mutableStateOf(0) }
    val heroEmojis = listOf("🍔","🍕","🍣","🍜","🌮","🍩","🧋","🍗","🥑","🍛")
    LaunchedEffect(Unit) { while (true) { delay(2000); emojiIndex = (emojiIndex + 1) % heroEmojis.size } }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Hero
        Box(
            modifier = Modifier.size(140.dp).clip(RoundedCornerShape(36.dp))
                .background(Brush.radialGradient(listOf(accent, accent.copy(red = accent.red * 0.6f)))),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = emojiIndex, transitionSpec = {
                (slideInVertically { it } + fadeIn()) togetherWith (slideOutVertically { -it } + fadeOut())
            }, label = "hero") { idx ->
                Text(heroEmojis[idx], fontSize = 72.sp)
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("Food Icons Pack", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        Text("${allFoodIcons.size}+ Food Icons • Dukung Semua Aplikasi",
            fontSize = 14.sp, color = TextSecondary, textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp, bottom = 24.dp))

        // Stats
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard("${allFoodIcons.size}+", "Icons",   accent, Modifier.weight(1f))
            StatCard("${allCountries.size - 1}", "Countries", Color(0xFF6BCB77), Modifier.weight(1f))
            StatCard("100%",    "All Apps Mask", Color(0xFFFFD93D), Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // Country quick picks
        Text("🌍 By Country", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.align(Alignment.Start))
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            allCountries.drop(1).forEach { country ->
                val flag = allFoodIcons.firstOrNull { it.country == country }?.countryFlag ?: "🌐"
                val count = allFoodIcons.count { it.country == country }
                Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = BgCard)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(flag, fontSize = 28.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(country, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("$count icons", fontSize = 10.sp, color = TextSecondary)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Apply button
        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accent),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Download, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Apply Icons Now (Semua Aplikasi)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = BgCard)
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF2ECC71), modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Auto Icon Masking Aktif", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Aplikasi lain yang belum ada ikon khusus akan otomatis dibungkus piring tema makanan!", color = TextSecondary, fontSize = 11.sp)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun StatCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier.height(80.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BgCard)) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(label, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

// ─── ICONS SCREEN ─────────────────────────────────────────────────────────────
@Composable
fun IconsScreen(accent: Color) {
    var filterMode by remember { mutableStateOf("Category") }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedCountry by remember { mutableStateOf("All Countries") }

    val filtered = remember(filterMode, selectedCategory, selectedCountry) {
        when (filterMode) {
            "Country" -> if (selectedCountry == "All Countries") allFoodIcons else allFoodIcons.filter { it.country == selectedCountry }
            else -> if (selectedCategory == "All") allFoodIcons else allFoodIcons.filter { it.category == selectedCategory }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text("Icons", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary, modifier = Modifier.weight(1f))
            Text("${filtered.size} icons", fontSize = 13.sp, color = accent, fontWeight = FontWeight.Bold)
        }

        // Toggle filter mode
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Category", "Country").forEach { mode ->
                val isSelected = filterMode == mode
                FilterChip(selected = isSelected, onClick = { filterMode = mode },
                    label = { Text(if (mode == "Country") "🌍 $mode" else "🏷️ $mode", fontSize = 13.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = accent, selectedLabelColor = Color.White,
                        containerColor = BgCard, labelColor = TextSecondary
                    )
                )
            }
        }

        // Chips
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (filterMode == "Country") {
                allCountries.forEach { country ->
                    val isSelected = country == selectedCountry
                    val flag = allFoodIcons.firstOrNull { it.country == country }?.countryFlag ?: "🌍"
                    FilterChip(selected = isSelected, onClick = { selectedCountry = country },
                        label = { Text(if (country == "All Countries") "🌍 All" else "$flag $country", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = accent, selectedLabelColor = Color.White,
                            containerColor = BgCard, labelColor = TextSecondary
                        )
                    )
                }
            } else {
                allCategories.forEach { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(selected = isSelected, onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = accent, selectedLabelColor = Color.White,
                            containerColor = BgCard, labelColor = TextSecondary
                        )
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered) { food -> IconGridItem(food) }
        }
    }
}

@Composable
fun IconGridItem(food: FoodIcon) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.85f else 1f, label = "scale")
    LaunchedEffect(pressed) { if (pressed) { delay(120); pressed = false } }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(78.dp).scale(scale)
                .clip(RoundedCornerShape(20.dp))
                .background(food.color.copy(alpha = 0.2f))
                .clickable { pressed = true },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = food.drawableRes),
                contentDescription = food.name,
                modifier = Modifier.size(54.dp)
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(food.name, color = TextSecondary, fontSize = 11.sp, textAlign = TextAlign.Center,
            maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(food.countryFlag, fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}

// ─── SEARCH SCREEN ────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(accent: Color) {
    var query by remember { mutableStateOf("") }
    val results = remember(query) {
        if (query.isBlank()) emptyList()
        else allFoodIcons.filter { it.name.contains(query, true) || it.category.contains(query, true) || it.country.contains(query, true) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Spacer(Modifier.height(12.dp))
        Text("Search", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = query, onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by food, country, category...", color = TextSecondary) },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = accent) },
            trailingIcon = if (query.isNotEmpty()) {{ IconButton({ query = "" }) { Icon(Icons.Default.Clear, null, tint = TextSecondary) } }} else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accent, unfocusedBorderColor = BgCard,
                focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                cursorColor = accent, unfocusedContainerColor = BgCard, focusedContainerColor = BgCard
            ),
            shape = RoundedCornerShape(16.dp), singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        if (query.isBlank()) {
            Text("🌍 Browse by Country", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                allCountries.drop(1).forEach { country ->
                    val flag = allFoodIcons.firstOrNull { it.country == country }?.countryFlag ?: "🌐"
                    val count = allFoodIcons.count { it.country == country }
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = BgCard),
                        onClick = { query = country }) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(flag, fontSize = 24.sp)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(country, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("$count food icons", color = TextSecondary, fontSize = 12.sp)
                            }
                            Icon(Icons.Default.ChevronRight, null, tint = TextSecondary)
                        }
                    }
                }
            }
        } else {
            Text("${results.size} results for \"$query\"", fontSize = 13.sp, color = TextSecondary)
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(results) { food -> IconGridItem(food) }
            }
        }
    }
}

// ─── SETTINGS SCREEN ──────────────────────────────────────────────────────────
@Composable
fun SettingsScreen(accent: Color, accentIndex: Int, onAccentChange: (Int) -> Unit) {
    val context = LocalContext.current
    var showLanguagePicker by remember { mutableStateOf(false) }
    val currentLang = languages.firstOrNull {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales.toLanguageTags().startsWith(it.tag)
        } else {
            AppCompatDelegate.getApplicationLocales().toLanguageTags().startsWith(it.tag)
        }
    } ?: languages[0]

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(16.dp))
        Text("Settings", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
        Spacer(Modifier.height(24.dp))

        // Custom Colors
        Text("🎨 Custom Theme Color", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(12.dp))
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BgCard)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                themeColors.forEachIndexed { idx, theme ->
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape)
                        .background(theme.color)
                        .clickable { onAccentChange(idx) }
                        .then(if (idx == accentIndex) Modifier.border(3.dp, Color.White, CircleShape) else Modifier),
                        contentAlignment = Alignment.Center) {
                        if (idx == accentIndex) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Multi-language
        Text("🌐 Language / Bahasa", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(12.dp))
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BgCard),
            onClick = { showLanguagePicker = !showLanguagePicker }) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(currentLang.flag, fontSize = 24.sp)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(currentLang.name, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(currentLang.localName, color = TextSecondary, fontSize = 12.sp)
                }
                Icon(if (showLanguagePicker) Icons.Default.ExpandLess else Icons.Default.ExpandMore, null, tint = TextSecondary)
            }
        }

        AnimatedVisibility(visible = showLanguagePicker) {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BgCard),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    languages.forEach { lang ->
                        val isSelected = lang.tag == currentLang.tag
                        Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) accent.copy(alpha = 0.15f) else Color.Transparent)
                            .clickable {
                                val locales = LocaleListCompat.forLanguageTags(lang.tag)
                                AppCompatDelegate.setApplicationLocales(locales)
                                showLanguagePicker = false
                            }.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(lang.flag, fontSize = 20.sp)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(lang.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(lang.localName, color = TextSecondary, fontSize = 11.sp)
                            }
                            if (isSelected) Icon(Icons.Default.Check, null, tint = accent, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // App info section
        Text("ℹ️ About", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(12.dp))

        listOf(
            Triple(Icons.Default.Policy, "Privacy Policy", "https://amarlo.online/food-icons/kebijakan-privasi/"),
            Triple(Icons.Default.Star,   "Rate Us on Google Play", "market://details?id=com.deploydulupulangnanti.foodicons"),
            Triple(Icons.Default.Share,  "Share App", "")
        ).forEach { (icon, title, url) ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = BgCard),
                onClick = {
                    when {
                        url.isEmpty() -> {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Download Food Icons Pack di Play Store!")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share"))
                        }
                        else -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp))
                        .background(accent.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Text(title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, null, tint = TextSecondary)
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = BgCard),
            modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🍔", fontSize = 36.sp)
                Text("Food Icons Pack", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Version 1.0.0 • by Deploy Dulu", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
                Text("© 2026 All rights reserved.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

// ─── APPLY BOTTOM SHEET ───────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyBottomSheet(accent: Color, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pm = context.packageManager

    // Detect Current Default Home Launcher
    val defaultHome = remember {
        val homeIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
        pm.resolveActivity(homeIntent, PackageManager.MATCH_DEFAULT_ONLY)
    }

    val currentLauncherPkg = defaultHome?.activityInfo?.packageName ?: ""
    val currentLauncherName = defaultHome?.loadLabel(pm)?.toString() ?: "Default Launcher"

    fun applyToLauncher(launcher: LauncherInfo, targetPkg: String) {
        try {
            when (launcher.applyMethod) {
                LauncherApplyMethod.INTENT_EXTRA -> {
                    if (launcher.action != null && launcher.extraKey != null) {
                        val intent = Intent(launcher.action).apply {
                            putExtra(launcher.extraKey, context.packageName)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(intent)
                        Toast.makeText(context, "Membuka ${launcher.name} untuk terapkan ikon...", Toast.LENGTH_SHORT).show()
                    } else {
                        pm.getLaunchIntentForPackage(targetPkg)?.let { context.startActivity(it) }
                    }
                }
                LauncherApplyMethod.SAMSUNG_THEME_PARK -> {
                    val themeParkIntent = pm.getLaunchIntentForPackage("com.samsung.android.themedesigner")
                    if (themeParkIntent != null) {
                        context.startActivity(themeParkIntent)
                        Toast.makeText(context, "Buka Theme Park > Icon > Pilih Food Icons > Apply", Toast.LENGTH_LONG).show()
                    } else {
                        // Open Galaxy Store / Play Store for Theme Park
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("samsungapps://ProductDetail/com.samsung.android.themedesigner")))
                        } catch (e: Exception) {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.samsung.android.themedesigner")))
                        }
                    }
                }
                LauncherApplyMethod.SYSTEM_THEMES -> {
                    try {
                        context.startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
                    } catch (e: Exception) {
                        Toast.makeText(context, launcher.guide ?: "Silakan buka pengaturan tema di HP Anda.", Toast.LENGTH_LONG).show()
                    }
                }
                LauncherApplyMethod.LAUNCHER_SETTINGS -> {
                    pm.getLaunchIntentForPackage(targetPkg)?.let { context.startActivity(it) }
                }
                LauncherApplyMethod.GENERAL_GUIDE -> {
                    Toast.makeText(context, launcher.guide ?: "", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            // Fallback to opening Launcher or Play Store
            try {
                pm.getLaunchIntentForPackage(targetPkg)?.let { context.startActivity(it) }
                    ?: context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$targetPkg")))
            } catch (ex: Exception) {
                Toast.makeText(context, "Gagal membuka launcher: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BgCard,
        dragHandle = {
            Box(
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    .width(40.dp).height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(TextSecondary.copy(alpha = 0.4f))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp)
        ) {
            Text("⚡ Terapkan ke Semua Aplikasi", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
            Text(
                "Pilih launcher HP Anda di bawah untuk mengaktifkan tema Food Icons ke seluruh aplikasi.",
                fontSize = 13.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Current Launcher Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BgDark)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📱", fontSize = 24.sp)
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Launcher Aktif Saat Ini", fontSize = 11.sp, color = TextSecondary)
                            Text(currentLauncherName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (currentLauncherPkg.contains("sec.android") || currentLauncherPkg.contains("samsung")) {
                        Text(
                            "HP Samsung menggunakan 'Theme Park' untuk menerapkan icon pack ke launcher bawaan One UI.",
                            fontSize = 12.sp, color = accent
                        )
                    } else if (currentLauncherPkg.contains("nexuslauncher") || currentLauncherPkg.contains("google")) {
                        Text(
                            "Pixel Launcher bawaan membatasi kustomisasi ikon. Disarankan pakai Nova atau Lawnchair (Gratis) agar semua aplikasi berubah.",
                            fontSize = 12.sp, color = Color(0xFFFFD93D)
                        )
                    } else {
                        Text(
                            "Semua aplikasi (termasuk yang belum punya ikon khusus) akan otomatis terbungkus latar belakang makanan berkat fitur Icon Masking!",
                            fontSize = 12.sp, color = Color(0xFF2ECC71)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Pilih Launcher Anda", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(10.dp))

            supportedLaunchers.forEach { launcher ->
                val installedPkg = launcher.packageNames.firstOrNull { pkg ->
                    try { pm.getPackageInfo(pkg, 0); true } catch (e: Exception) { false }
                }
                val isInstalled = installedPkg != null

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isInstalled) BgDark else BgDark.copy(alpha = 0.5f)
                    ),
                    onClick = {
                        val targetPkg = installedPkg ?: launcher.packageNames.first()
                        if (isInstalled) {
                            applyToLauncher(launcher, targetPkg)
                        } else {
                            // Download from Play Store
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$targetPkg")))
                            } catch (e: Exception) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$targetPkg")))
                            }
                        }
                    }
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(launcher.emoji, fontSize = 24.sp)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(launcher.name, color = if (isInstalled) TextPrimary else TextSecondary,
                                fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(
                                if (isInstalled) "Terpasang – Klik untuk terapkan" else "Belum terpasang – Klik untuk download gratis",
                                color = if (isInstalled) accent else TextSecondary, fontSize = 11.sp
                            )
                            if (launcher.guide != null && isInstalled) {
                                Text(launcher.guide, fontSize = 10.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))
                            }
                        }
                        if (isInstalled) {
                            Icon(Icons.Default.Check, null, tint = accent, modifier = Modifier.size(20.dp))
                        } else {
                            Icon(Icons.Default.Download, null, tint = TextSecondary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Guide Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = accent.copy(alpha = 0.12f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, null, tint = accent, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Cara Kerja di Sistem Android", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "1. Android memerlukan Launcher (seperti Nova, Lawnchair, Smart Launcher, atau Theme Park di Samsung) untuk mengganti ikon aplikasi di layar utama.\n" +
                        "2. Begitu diterapkan di launcher, SEMUA aplikasi yang terpasang di HP Anda akan otomatis memiliki tema makanan (termasuk ikon adaptif otomatis).",
                        color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
