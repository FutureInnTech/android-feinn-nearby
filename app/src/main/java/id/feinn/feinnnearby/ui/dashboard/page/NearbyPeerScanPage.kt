package id.feinn.feinnnearby.ui.dashboard.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme

data class PeerNode(
    val id: String,
    val name: String,
    val distance: String,
    val isDirect: Boolean,
    val statusColor: Color,
    val canMessage: Boolean = false
)

@Composable
fun NearbyPeerScanPage(
    modifier: Modifier = Modifier,
    nodes: List<PeerNode> = emptyList(),
    onConnectClick: (PeerNode) -> Unit = {},
    onMessageClick: (PeerNode) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0E1A))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        
        ScanRadarSection()
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = stringResource(id = R.string.nearby_scan_available_nodes),
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(nodes) { node ->
                NodeItem(
                    node = node,
                    onConnectClick = { onConnectClick(node) },
                    onMessageClick = { onMessageClick(node) }
                )
            }
        }
        
        ScanFooterSection()
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ScanRadarSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .border(1.dp, Color(0xFF1E293B), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(1.dp, Color(0xFF334155), CircleShape)
            )
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = Color(0xFF1E293B)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_nearby),
                    contentDescription = null,
                    modifier = Modifier.padding(14.dp),
                    tint = Color(0xFF7DD3FC)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = stringResource(id = R.string.nearby_scan_title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(id = R.string.nearby_scan_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
        )
    }
}

@Composable
private fun NodeItem(
    node: PeerNode,
    modifier: Modifier = Modifier,
    onConnectClick: () -> Unit = {},
    onMessageClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF0F172A),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = Color(0xFF1E293B)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(node.statusColor, CircleShape)
                        .border(2.dp, Color(0xFF0F172A), CircleShape)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = node.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    SignalStrengthIndicator()
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.nearby_scan_distance_away, node.distance),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    ConnectionTypeBadge(isDirect = node.isDirect)
                }
            }
            
            Button(
                onClick = if (node.canMessage) onMessageClick else onConnectClick,
                modifier = Modifier.height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E293B),
                    contentColor = Color(0xFF7DD3FC)
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = if (node.canMessage) 
                        stringResource(id = R.string.nearby_scan_button_message) 
                    else 
                        stringResource(id = R.string.nearby_scan_button_connect),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SignalStrengthIndicator(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height((8 + index * 3).dp)
                    .background(Color(0xFF7DD3FC), RoundedCornerShape(1.dp))
            )
        }
    }
}

@Composable
private fun ConnectionTypeBadge(isDirect: Boolean, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = if (isDirect) Color(0xFF065F46).copy(alpha = 0.2f) else Color(0xFF1E293B),
        shape = RoundedCornerShape(12.dp),
        border = if (isDirect) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF065F46)) else null
    ) {
        Text(
            text = if (isDirect) 
                stringResource(id = R.string.nearby_scan_direct) 
            else 
                stringResource(id = R.string.nearby_scan_via_mesh),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (isDirect) Color(0xFF34D399) else Color(0xFF7DD3FC),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ScanFooterSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.nearby_scan_footer_title),
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF94A3B8)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = stringResource(id = R.string.nearby_scan_footer_description),
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF0A0E1A)
private fun PreviewNearbyPeerScanPage() {
    val sampleNodes = listOf(
        PeerNode("1", "Alex Rivera", "12m", true, Color(0xFF22C55E)),
        PeerNode("2", "Elena K.", "45m", false, Color(0xFF3B82F6), canMessage = true),
        PeerNode("3", "Device_882", "102m", false, Color(0xFF64748B))
    )
    FeinnNearbyTheme {
        NearbyPeerScanPage(nodes = sampleNodes)
    }
}