package id.feinn.feinnnearby.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.dashboard.page.MyProfilePage
import id.feinn.feinnnearby.ui.dashboard.page.NearbyPeerScanPage
import id.feinn.feinnnearby.ui.dashboard.page.SecureChatsPage
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme

@Composable
fun DashboardScreen() {

    Scaffold(
        topBar = {
            StatusBanner()
        },
        bottomBar = {
            SecureBottomNavigation()
        },
        floatingActionButton = {
            ScanNearbyButton()
        }

    ) { paddingValues ->

        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pagerState
        ) { page ->

            when(page) {
                0 -> SecureChatsPage()
                1 -> NearbyPeerScanPage ()
                2 -> MyProfilePage()
            }

        }

    }

}

@Composable
private fun StatusBanner(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        color = Color(0xFF0F172A).copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_offline),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.secure_chats_status_banner),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun SecureBottomNavigation() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = MaterialTheme.colorScheme.background,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1F2937).copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                iconId = R.drawable.ic_app,
                label = stringResource(id = R.string.secure_chats_nav_messages),
                isSelected = false
            )
            NavItem(
                iconId = R.drawable.ic_nearby,
                label = stringResource(id = R.string.secure_chats_nav_nearby),
                isSelected = true
            )
            NavItem(
                iconId = R.drawable.ic_person,
                label = stringResource(id = R.string.secure_chats_nav_profile),
                isSelected = false
            )
        }
    }
}

@Composable
private fun NavItem(
    iconId: Int,
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Box(
            modifier = if (isSelected) {
                Modifier
                    .size(width = 64.dp, height = 36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            } else {
                Modifier.size(width = 64.dp, height = 36.dp)
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = label,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ScanNearbyButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .height(56.dp)
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_nearby),
            contentDescription = null,
            tint = Color(0xFF0A0E1A),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.secure_chats_scan_nearby),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A0E1A)
            )
        )
    }
}

@Composable
@Preview
private fun PreviewDashboardScreen() {
    FeinnNearbyTheme {
        DashboardScreen()
    }
}