package id.feinn.feinnnearby.ui.onboarding

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.feinn.feinnnearby.R
import id.feinn.feinnnearby.ui.theme.FeinnNearbyTheme
import id.feinn.feinnnearby.ui.theme.primary400
import id.feinn.feinnnearby.ui.theme.primary600
import id.feinn.feinnnearby.ui.theme.primary700
import id.feinn.feinnnearby.ui.theme.tertiary300

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun OnboardingScreen() {
    Scaffold {  _ ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                painter = painterResource(id = R.drawable.img_illustration_onboarding),
                contentDescription = stringResource(R.string.onboarding_illustration_image_description),
                contentScale = ContentScale.FillWidth
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append(stringResource(R.string.onboarding_title_part1) + "\n")
                            }
                            withStyle(style = SpanStyle(color = primary400)) {
                                append(stringResource(R.string.onboarding_title_part2))
                            }
                        },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )

                    Text(
                        text = stringResource(R.string.onboarding_description),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Feature List
                    FeatureItem(
                        icon = {
                            FeatureIconPlaceholder(
                                color = primary700,
                                id = R.drawable.ic_offline,
                                contentDescription = stringResource(R.string.onboarding_feature_mesh)
                            )
                        },
                        text = stringResource(R.string.onboarding_feature_mesh)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FeatureItem(
                        icon = {
                            FeatureIconPlaceholder(
                                color = tertiary300,
                                id = R.drawable.ic_encrypted,
                                contentDescription = stringResource(R.string.onboarding_feature_encrypted)
                            )
                        },
                        text = stringResource(R.string.onboarding_feature_encrypted)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FeatureItem(
                        icon = {
                            FeatureIconPlaceholder(
                                color = primary600,
                                id = R.drawable.ic_decentralized,
                                contentDescription = stringResource(R.string.onboarding_feature_no_servers)
                            )
                        },
                        text = stringResource(R.string.onboarding_feature_no_servers)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Get Started Button
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.onboarding_button_get_started),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Arrow Icon Placeholder
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                stringResource(id = R.string.onboarding_button_get_started)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.onboarding_footer_text),
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )

                }

            }

            Spacer(
                modifier = Modifier
                    .height(64.dp)
            )

        }
    }
}

@Composable
fun FeatureItem(icon: @Composable () -> Unit, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FeatureIconPlaceholder(
    color: Color,
    @DrawableRes id: Int,
    contentDescription: String?
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(color.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(17.dp),
            painter = painterResource(id),
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun PreviewOnBoarding() {
    FeinnNearbyTheme {
        OnboardingScreen()
    }
}