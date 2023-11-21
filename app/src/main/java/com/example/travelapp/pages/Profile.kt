package com.example.travelapp.pages

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.example.travelapp.R
import com.example.travelapp.view_models.ProfileAchievementViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Profile(
    viewModel: ProfileAchievementViewModel,
    modifier: Modifier = Modifier
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Box(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = if (selectedImageUri == null) painterResource(R.drawable.empty) else rememberAsyncImagePainter(
                        selectedImageUri
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                )

                selectedImageUri?.let {
                    LocalContext.current.contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                Text(
                    text = viewModel.username ?: "",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(
                    text = determineUserLevel(viewModel.trips),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    viewModel.countriesList.forEach { country ->
                        FlagPicker(country)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = viewModel.continents.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                            Text(
                                text = "Continents",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(50.dp))
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = viewModel.countries.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                            Text(
                                text = "Countries",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = viewModel.cities.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                            Text(
                                text = "Cities",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(50.dp))
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = viewModel.trips.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                            Text(
                                text = "Trips",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
                Text(
                    text = "See Your Map!",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .clickable { }
                )
            }
        }
    }
}

@Composable
fun FlagPicker(country: String) {
    val countryFlags = mapOf(
        "Afghanistan" to R.drawable.afghanistan,
        "Albania" to R.drawable.albania,
        "Algeria" to R.drawable.algeria,
        "Andorra" to R.drawable.andorra,
        "Angola" to R.drawable.angola,
        "Antigua and Barbuda" to R.drawable.antigua_and_barbuda,
        "Argentina" to R.drawable.argentina,
        "Armenia" to R.drawable.armenia,
        "Australia" to R.drawable.australia,
        "Austria" to R.drawable.austria,
        "Azerbaijan" to R.drawable.azerbaijan,
        "Bahamas" to R.drawable.bahamas,
        "Bahrain" to R.drawable.bahrain,
        "Bangladesh" to R.drawable.bangladesh,
        "Barbados" to R.drawable.barbados,
        "Belarus" to R.drawable.belarus,
        "Belgium" to R.drawable.belgium,
        "Belize" to R.drawable.belize,
        "Benin" to R.drawable.benin,
        "Bhutan" to R.drawable.bhutan,
        "Bolivia" to R.drawable.bolivia,
        "Bosnia and Herzegovina" to R.drawable.bosnia_and_herzegovina,
        "Botswana" to R.drawable.botswana,
        "Brazil" to R.drawable.brazil,
        "Brunei" to R.drawable.brunei,
        "Bulgaria" to R.drawable.bulgaria,
        "Burkina Faso" to R.drawable.burkina_faso,
        "Burundi" to R.drawable.burundi,
        "Cape Verde" to R.drawable.cape_verde,
        "Cambodia" to R.drawable.cambodia,
        "Cameroon" to R.drawable.cameroon,
        "Canada" to R.drawable.canada,
        "Central African Republic" to R.drawable.central_african_republic,
        "Chad" to R.drawable.chad,
        "Chile" to R.drawable.chile,
        "China" to R.drawable.china,
        "Colombia" to R.drawable.colombia,
        "Comoros" to R.drawable.comoros,
        "Congo" to R.drawable.congo,
        "Costa Rica" to R.drawable.costa_rica,
        "Croatia" to R.drawable.croatia,
        "Cuba" to R.drawable.cuba,
        "Cyprus" to R.drawable.cyprus,
        "Czech Republic" to R.drawable.czech_republic,
        "North Korea" to R.drawable.north_korea,
        "DR Congo" to R.drawable.congo,
        "Denmark" to R.drawable.denmark,
        "Djibouti" to R.drawable.djibouti,
        "Dominica" to R.drawable.empty,
        "Dominican Republic" to R.drawable.dominican_republic,
        "East Timor" to R.drawable.east_timor,
        "Ecuador" to R.drawable.ecuador,
        "Egypt" to R.drawable.egypt,
        "El Salvador" to R.drawable.el_salvador,
        "Equatorial Guinea" to R.drawable.equatorial_guinea,
        "Eritrea" to R.drawable.eritrea,
        "Estonia" to R.drawable.estonia,
        "Eswatini" to R.drawable.swaziland,
        "Ethiopia" to R.drawable.ethiopia,
        "Fiji" to R.drawable.fiji,
        "Finland" to R.drawable.finland,
        "France" to R.drawable.france,
        "Gabon" to R.drawable.gabon,
        "Gambia" to R.drawable.gambia,
        "Georgia" to R.drawable.georgia,
        "Germany" to R.drawable.germany,
        "Ghana" to R.drawable.ghana,
        "Greece" to R.drawable.greece,
        "Grenada" to R.drawable.grenada,
        "Guatemala" to R.drawable.guatemala,
        "Guinea" to R.drawable.guinea,
        "Guinea-Bissau" to R.drawable.guinea_bissau,
        "Guyana" to R.drawable.guyana,
        "Haiti" to R.drawable.haiti,
        "Honduras" to R.drawable.honduras,
        "Hungary" to R.drawable.hungary,
        "Iceland" to R.drawable.iceland,
        "India" to R.drawable.india,
        "Indonesia" to R.drawable.indonesia,
        "Iran" to R.drawable.iran,
        "Iraq" to R.drawable.iraq,
        "Ireland" to R.drawable.ireland,
        "Israel" to R.drawable.israel,
        "Italy" to R.drawable.italy,
        "Ivory Coast" to R.drawable.ivory_coast,
        "Jamaica" to R.drawable.jamaica,
        "Japan" to R.drawable.japan,
        "Jordan" to R.drawable.jordan,
        "Kazakhstan" to R.drawable.kazakhstan,
        "Kenya" to R.drawable.kenya,
        "Kiribati" to R.drawable.kiribati,
        "Kosovo" to R.drawable.kosovo,
        "Kuwait" to R.drawable.kuwait,
        "Kyrgyzstan" to R.drawable.kyrgyzstan,
        "Laos" to R.drawable.laos,
        "Latvia" to R.drawable.latvia,
        "Lebanon" to R.drawable.lebanon,
        "Lesotho" to R.drawable.lesotho,
        "Liberia" to R.drawable.liberia,
        "Libya" to R.drawable.libya,
        "Liechtenstein" to R.drawable.liechtenstein,
        "Lithuania" to R.drawable.lithuania,
        "Luxembourg" to R.drawable.luxembourg,
        "Madagascar" to R.drawable.madagascar,
        "Malawi" to R.drawable.malawi,
        "Malaysia" to R.drawable.malaysia,
        "Maldives" to R.drawable.maldives,
        "Mali" to R.drawable.mali,
        "Malta" to R.drawable.malta,
        "Marshall Islands" to R.drawable.marshall_islands,
        "Mauritania" to R.drawable.mauritania,
        "Mauritius" to R.drawable.mauritius,
        "Mexico" to R.drawable.mexico,
        "Micronesia" to R.drawable.micronesia,
        "Moldova" to R.drawable.moldova,
        "Monaco" to R.drawable.monaco,
        "Mongolia" to R.drawable.mongolia,
        "Montenegro" to R.drawable.montenegro,
        "Morocco" to R.drawable.morocco,
        "Mozambique" to R.drawable.mozambique,
        "Myanmar" to R.drawable.myanmar,
        "Namibia" to R.drawable.namibia,
        "Nauru" to R.drawable.nauru,
        "Nepal" to R.drawable.nepal,
        "Netherlands" to R.drawable.netherlands,
        "New Zealand" to R.drawable.new_zealand,
        "Nicaragua" to R.drawable.nicaragua,
        "Niger" to R.drawable.niger,
        "Nigeria" to R.drawable.nigeria,
        "North Macedonia" to R.drawable.macedonia,
        "Norway" to R.drawable.norway,
        "Oman" to R.drawable.oman,
        "Pakistan" to R.drawable.pakistan,
        "Palau" to R.drawable.palau,
        "Panama" to R.drawable.panama,
        "Papua New Guinea" to R.drawable.papua_new_guinea,
        "Paraguay" to R.drawable.paraguay,
        "Peru" to R.drawable.peru,
        "Philippines" to R.drawable.philippines,
        "Poland" to R.drawable.poland,
        "Portugal" to R.drawable.portugal,
        "Qatar" to R.drawable.qatar,
        "Romania" to R.drawable.romania,
        "Russia" to R.drawable.russia,
        "Rwanda" to R.drawable.rwanda,
        "Saint Kitts and Nevis" to R.drawable.saint_kitts_and_nevis,
        "Saint Lucia" to R.drawable.saint_lucia,
        "Saint Vincent and the Grenadines" to R.drawable.saint_vincent_and_the_grenadines,
        "Samoa" to R.drawable.samoa,
        "San Marino" to R.drawable.san_marino,
        "Sao Tome and Principe" to R.drawable.sao_tome_and_principe,
        "Saudi Arabia" to R.drawable.saudi_arabia,
        "Senegal" to R.drawable.senegal,
        "Serbia" to R.drawable.serbia,
        "Seychelles" to R.drawable.seychelles,
        "Sierra Leone" to R.drawable.sierra_leone,
        "Singapore" to R.drawable.singapore,
        "Slovakia" to R.drawable.slovakia,
        "Slovenia" to R.drawable.slovenia,
        "Solomon Islands" to R.drawable.solomon_islands,
        "Somalia" to R.drawable.somalia,
        "South Africa" to R.drawable.south_africa,
        "South Korea" to R.drawable.south_korea,
        "South Sudan" to R.drawable.south_sudan,
        "Spain" to R.drawable.spain,
        "Sri Lanka" to R.drawable.sri_lanka,
        "Sudan" to R.drawable.sudan,
        "Suriname" to R.drawable.suriname,
        "Sweden" to R.drawable.sweden,
        "Switzerland" to R.drawable.switzerland,
        "Syria" to R.drawable.syria,
        "Tajikistan" to R.drawable.tajikistan,
        "Tanzania" to R.drawable.tanzania,
        "Thailand" to R.drawable.thailand,
        "Togo" to R.drawable.togo,
        "Tonga" to R.drawable.tonga,
        "Trinidad and Tobago" to R.drawable.trinidad_and_tobago,
        "Tunisia" to R.drawable.tunisia,
        "Turkey" to R.drawable.turkey,
        "Turkmenistan" to R.drawable.turkmenistan,
        "Tuvalu" to R.drawable.tuvalu,
        "Uganda" to R.drawable.uganda,
        "Ukraine" to R.drawable.ukraine,
        "United Arab Emirates" to R.drawable.united_arab_emirates,
        "United Kingdom" to R.drawable.united_kingdom,
        "United States" to R.drawable.usa,
        "Uruguay" to R.drawable.uruguay,
        "Uzbekistan" to R.drawable.uzbekistan,
        "Vanuatu" to R.drawable.vanuatu,
        "Vatican City" to R.drawable.vatican_city,
        "Venezuela" to R.drawable.venezuela,
        "Vietnam" to R.drawable.vietnam,
        "Yemen" to R.drawable.yemen,
        "Zambia" to R.drawable.zambia,
        "Zimbabwe" to R.drawable.zimbabwe
    )

    val imageResId = countryFlags[country] ?: R.drawable.ex_yugoslavia

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

fun determineUserLevel(numberOfTrips: Int): String {
    return when {
        numberOfTrips < 5 -> "Sedentary"
        numberOfTrips < 10 -> "Hitchhiker"
        numberOfTrips < 20 -> "Nomad"
        numberOfTrips < 50 -> "Explorer"
        numberOfTrips < 100 -> "Globetrotter"
        else -> "Odysseus"
    }
}