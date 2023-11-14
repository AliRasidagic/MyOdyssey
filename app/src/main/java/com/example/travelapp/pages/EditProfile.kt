package com.example.travelapp.pages

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.travelapp.R
import com.example.travelapp.ui.theme.TravelAppTheme

@Composable
fun EditProfile(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.spain), // Assuming you have the Spanish flag image in your resources
                        contentDescription = "Spanish Flag",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.25f)
                    )
                    Column(
                        modifier = Modifier
                            .clickable { /* Handle card click here */ }
                            .padding(8.dp)
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.gigachad),
                                contentDescription = "Travel Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "title",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = "continent",
                                    style = TextStyle(fontSize = 16.sp),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                ) {
                                    Text(
                                        text = "country",
                                        style = TextStyle(fontSize = 12.sp),
                                    )
                                    Text(
                                        text = "city",
                                        style = TextStyle(fontSize = 12.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "startDate",
                                        style = TextStyle(fontSize = 8.sp),
                                    )
                                    Text(
                                        text = "endDate",
                                        style = TextStyle(fontSize = 8.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "description",
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.bg), // Assuming you have the Spanish flag image in your resources
                        contentDescription = "Spanish Flag",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.2f)
                    )
                    Column(
                        modifier = Modifier
                            .clickable { /* Handle card click here */ }
                            .padding(8.dp)
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.gigachad),
                                contentDescription = "Travel Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "title",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = "continent",
                                    style = TextStyle(fontSize = 16.sp),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                ) {
                                    Text(
                                        text = "country",
                                        style = TextStyle(fontSize = 12.sp),
                                    )
                                    Text(
                                        text = "city",
                                        style = TextStyle(fontSize = 12.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "startDate",
                                        style = TextStyle(fontSize = 8.sp),
                                    )
                                    Text(
                                        text = "endDate",
                                        style = TextStyle(fontSize = 8.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "description",
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.spain), // Assuming you have the Spanish flag image in your resources
                        contentDescription = "Spanish Flag",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.2f)
                    )
                    Column(
                        modifier = Modifier
                            .clickable { /* Handle card click here */ }
                            .padding(8.dp)
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.gigachad),
                                contentDescription = "Travel Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "title",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = "continent",
                                    style = TextStyle(fontSize = 16.sp),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                ) {
                                    Text(
                                        text = "country",
                                        style = TextStyle(fontSize = 12.sp),
                                    )
                                    Text(
                                        text = "city",
                                        style = TextStyle(fontSize = 12.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "startDate",
                                        style = TextStyle(fontSize = 8.sp),
                                    )
                                    Text(
                                        text = "endDate",
                                        style = TextStyle(fontSize = 8.sp),
                                        modifier = Modifier
                                            .padding(start = 4.dp),
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "description",
                            style = TextStyle(fontSize = 12.sp),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    TravelAppTheme {
        EditProfile()
    }
}

@Composable
fun MapView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
            }
        },
        update = { webView ->
            val htmlContent = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Leaflet GeoJSON Example</title>
                        <meta charset="utf-8" />
                        <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.1/leaflet.css" />
                        <style type="text/css">
                            .leaflet-container{background-color:#c5e8ff;}
                        </style>
                    </head>
                    <body>
                        <div id="map" style="width: 100%; height: 100vh"></div>
                        <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
                        <script src="http://cdn.leafletjs.com/leaflet-0.7.1/leaflet.js"></script>
                        <script>
                            var myGeoJSONPath = 'path/to/mymap.geo.json';
                            var myCustomStyle = {
                                stroke: false,
                                fill: true,
                                fillColor: '#fff',
                                fillOpacity: 1
                            }
                            $.getJSON(myGeoJSONPath, function(data) {
                                var map = L.map('map').setView([39.74739, -105], 4);
                                L.geoJson(data, {
                                    clickable: false,
                                    style: myCustomStyle
                                }).addTo(map);
                            })
                        </script>
                    </body>
                    </html>
                """.trimIndent()
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
        }
    )
}