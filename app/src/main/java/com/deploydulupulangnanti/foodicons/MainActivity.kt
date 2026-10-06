package com.deploydulupulangnanti.foodicons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodIconsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1E2B3C) // Warna background biru gelap seperti di referensi
                ) {
                    IconsScreen()
                }
            }
        }
    }
}

@Composable
fun FoodIconsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color(0xFF1E2B3C),
            surface = Color(0xFF1E2B3C),
            onBackground = Color.White,
            onSurface = Color.White
        ),
        content = content
    )
}

data class AppIcon(val name: String, val drawableRes: Int = android.R.drawable.sym_def_app_icon)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsScreen() {
    // List dummy untuk icon pack. Nanti bisa diganti dengan drawable assets makanan Anda.
    val icons = listOf(
        AppIcon("Adobe Acrobat"), AppIcon("Airbnb"), AppIcon("Aliexpress"),
        AppIcon("Amazon Audible"), AppIcon("Amazon Music"), AppIcon("Amazon Shopping"),
        AppIcon("Among Us"), AppIcon("Apple Music"), AppIcon("Bank Of America"),
        AppIcon("Bereal"), AppIcon("Best Buy"), AppIcon("WhatsApp"),
        AppIcon("Facebook"), AppIcon("Instagram"), AppIcon("LinkedIn")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Icons", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White
            )
        )
        
        // Tab "ALL APPS"
        Text(
            text = "ALL APPS",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(40.dp)
                .height(2.dp)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(icons) { icon ->
                IconItem(icon)
            }
        }
    }
}

@Composable
fun IconItem(appIcon: AppIcon) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Kotak placeholder untuk icon.
        // Nanti ganti dengan Image(painter = painterResource(id = R.drawable.icon_makanan_anda), ...)
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2C3E50)), // Warna placeholder
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = appIcon.drawableRes),
                contentDescription = appIcon.name,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = appIcon.name,
            color = Color.LightGray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconsScreenPreview() {
    FoodIconsTheme {
        IconsScreen()
    }
}
