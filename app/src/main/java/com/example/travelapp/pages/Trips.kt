package com.example.travelapp.pages

import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.travelapp.Navigation
import com.example.travelapp.R
import com.example.travelapp.data.TravelInfo
import com.example.travelapp.view_models.AddTripViewModel
import com.example.travelapp.view_models.ProfileAchievementViewModel

@Composable
fun Trips(
    modifier: Modifier = Modifier,
    viewModel: AddTripViewModel,
    navController: NavController
) {
    val uiState = viewModel.state
    var travelInfoList by remember { mutableStateOf<List<TravelInfo>>(emptyList()) }

    LaunchedEffect(key1 = viewModel) {
        travelInfoList = viewModel.getTrips()
    }

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
                if (uiState.showDialog) {
                    Dialog(
                        onDismissRequest = { viewModel.onShowChange(false) }
                    ) {
                        AddTrip(viewModel = viewModel)
                    }
                }
                travelInfoList.forEach { travelInfo ->
                    TravelCard(
                        title = travelInfo.title,
                        continent = travelInfo.continent,
                        country = travelInfo.country,
                        city = travelInfo.city,
                        startDate = travelInfo.startDate,
                        endDate = travelInfo.endDate,
                        mainImage = travelInfo.image,
                        description = travelInfo.info,
                        backgroundImage = travelInfo.country,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun TravelCard(
    title: String,
    continent: String,
    country: String,
    city: String,
    startDate: String,
    endDate: String,
    mainImage: String,
    backgroundImage: String,
    description: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .clickable {
                    navController.navigate(Navigation.TripInfo.name)
                }
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .background(Color.DarkGray)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Image(
                        painter = painterResource(countryToFlag(backgroundImage)),
                        contentDescription = "Travel Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(100.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Text(
                        text = country,
                        style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    Text(
                        text = city,
                        style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.7f)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = Uri.parse(mainImage)
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.5f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.africa),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.TopEnd)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(
                            text = startDate,
                            style = TextStyle(fontSize = 8.sp),
                        )
                        Text(
                            text = endDate,
                            style = TextStyle(fontSize = 8.sp),
                            modifier = Modifier
                                .padding(start = 4.dp),
                        )
                    }
                }
            }
        }
    }
}

fun countryToFlag(country: String): Int {
    val allCountries = mapOf(
        "Afghanistan" to R.drawable.af,
        "Albania" to R.drawable.al,
        "Algeria" to R.drawable.dz,
        "Andorra" to R.drawable.ad,
        "Angola" to R.drawable.ao,
        "Antigua and Barbuda" to R.drawable.ag,
        "Argentina" to R.drawable.ar,
        "Armenia" to R.drawable.am,
        "Australia" to R.drawable.au,
        "Austria" to R.drawable.at,
        "Azerbaijan" to R.drawable.az,
        "Bahamas" to R.drawable.bs,
        "Bahrain" to R.drawable.bh,
        "Bangladesh" to R.drawable.bd,
        "Barbados" to R.drawable.bb,
        "Belarus" to R.drawable.by,
        "Belgium" to R.drawable.be,
        "Belize" to R.drawable.bz,
        "Benin" to R.drawable.bj,
        "Bhutan" to R.drawable.bt,
        "Bolivia" to R.drawable.bo,
        "Bosnia and Herzegovina" to R.drawable.ba,
        "Botswana" to R.drawable.bw,
        "Brazil" to R.drawable.br,
        "Brunei" to R.drawable.bn,
        "Bulgaria" to R.drawable.bg,
        "Burkina Faso" to R.drawable.bf,
        "Burundi" to R.drawable.bi,
        "Cape Verde" to R.drawable.cv,
        "Cambodia" to R.drawable.kh,
        "Cameroon" to R.drawable.cm,
        "Canada" to R.drawable.ca,
        "Central African Republic" to R.drawable.cf,
        "Chad" to R.drawable.td,
        "Chile" to R.drawable.cl,
        "China" to R.drawable.cn,
        "Colombia" to R.drawable.co,
        "Comoros" to R.drawable.km,
        "Congo" to R.drawable.cg,
        "Costa Rica" to R.drawable.cr,
        "Croatia" to R.drawable.hr,
        "Cuba" to R.drawable.cu,
        "Cyprus" to R.drawable.cy,
        "Czech Republic" to R.drawable.cz,
        "North Korea" to R.drawable.kp,
        "DR Congo" to R.drawable.cd,
        "Denmark" to R.drawable.dk,
        "Djibouti" to R.drawable.dj,
        "Dominica" to R.drawable.dm,
        "Dominican Republic" to R.drawable.empty,
        "East Timor" to R.drawable.tl,
        "Ecuador" to R.drawable.ec,
        "Egypt" to R.drawable.eg,
        "El Salvador" to R.drawable.sv,
        "Equatorial Guinea" to R.drawable.gq,
        "Eritrea" to R.drawable.er,
        "Estonia" to R.drawable.ee,
        "Eswatini" to R.drawable.sz,
        "Ethiopia" to R.drawable.et,
        "Fiji" to R.drawable.fj,
        "Finland" to R.drawable.fi,
        "France" to R.drawable.fr,
        "Gabon" to R.drawable.ga,
        "Gambia" to R.drawable.gm,
        "Georgia" to R.drawable.ge,
        "Germany" to R.drawable.de,
        "Ghana" to R.drawable.gh,
        "Greece" to R.drawable.gr,
        "Grenada" to R.drawable.gd,
        "Guatemala" to R.drawable.gt,
        "Guinea" to R.drawable.gn,
        "Guinea-Bissau" to R.drawable.gw,
        "Guyana" to R.drawable.gy,
        "Haiti" to R.drawable.ht,
        "Honduras" to R.drawable.hn,
        "Hungary" to R.drawable.hu,
        "Iceland" to R.drawable.`is`,
        "India" to R.drawable.`in`,
        "Indonesia" to R.drawable.id,
        "Iran" to R.drawable.ir,
        "Iraq" to R.drawable.iq,
        "Ireland" to R.drawable.ie,
        "Israel" to R.drawable.il,
        "Italy" to R.drawable.it,
        "Ivory Coast" to R.drawable.ci,
        "Jamaica" to R.drawable.jm,
        "Japan" to R.drawable.jp,
        "Jordan" to R.drawable.jo,
        "Kazakhstan" to R.drawable.kz,
        "Kenya" to R.drawable.ke,
        "Kiribati" to R.drawable.ki,
        "Kosovo" to R.drawable.xk,
        "Kuwait" to R.drawable.kw,
        "Kyrgyzstan" to R.drawable.kg,
        "Laos" to R.drawable.la,
        "Latvia" to R.drawable.lv,
        "Lebanon" to R.drawable.lb,
        "Lesotho" to R.drawable.ls,
        "Liberia" to R.drawable.lr,
        "Libya" to R.drawable.ly,
        "Liechtenstein" to R.drawable.li,
        "Lithuania" to R.drawable.lt,
        "Luxembourg" to R.drawable.lu,
        "Madagascar" to R.drawable.mg,
        "Malawi" to R.drawable.mw,
        "Malaysia" to R.drawable.my,
        "Maldives" to R.drawable.mv,
        "Mali" to R.drawable.ml,
        "Malta" to R.drawable.mt,
        "Marshall Islands" to R.drawable.mh,
        "Mauritania" to R.drawable.mr,
        "Mauritius" to R.drawable.mu,
        "Mexico" to R.drawable.mx,
        "Micronesia" to R.drawable.fm,
        "Moldova" to R.drawable.md,
        "Monaco" to R.drawable.mc,
        "Mongolia" to R.drawable.mn,
        "Montenegro" to R.drawable.me,
        "Morocco" to R.drawable.ma,
        "Mozambique" to R.drawable.mz,
        "Myanmar" to R.drawable.mm,
        "Namibia" to R.drawable.na,
        "Nauru" to R.drawable.nr,
        "Nepal" to R.drawable.np,
        "Netherlands" to R.drawable.nl,
        "New Zealand" to R.drawable.nz,
        "Nicaragua" to R.drawable.ni,
        "Niger" to R.drawable.ne,
        "Nigeria" to R.drawable.ng,
        "North Macedonia" to R.drawable.mk,
        "Norway" to R.drawable.no,
        "Oman" to R.drawable.om,
        "Pakistan" to R.drawable.pk,
        "Palau" to R.drawable.pw,
        "Panama" to R.drawable.pa,
        "Papua New Guinea" to R.drawable.pg,
        "Paraguay" to R.drawable.py,
        "Peru" to R.drawable.pe,
        "Philippines" to R.drawable.ph,
        "Poland" to R.drawable.pl,
        "Portugal" to R.drawable.pt,
        "Qatar" to R.drawable.qa,
        "Romania" to R.drawable.ro,
        "Russia" to R.drawable.ru,
        "Rwanda" to R.drawable.rw,
        "Saint Kitts and Nevis" to R.drawable.kn,
        "Saint Lucia" to R.drawable.lc,
        "Saint Vincent and the Grenadines" to R.drawable.vc,
        "Samoa" to R.drawable.ws,
        "San Marino" to R.drawable.sm,
        "Sao Tome and Principe" to R.drawable.st,
        "Saudi Arabia" to R.drawable.sa,
        "Senegal" to R.drawable.sn,
        "Serbia" to R.drawable.rs,
        "Seychelles" to R.drawable.sc,
        "Sierra Leone" to R.drawable.sl,
        "Singapore" to R.drawable.sg,
        "Slovakia" to R.drawable.sk,
        "Slovenia" to R.drawable.si,
        "Solomon Islands" to R.drawable.sb,
        "Somalia" to R.drawable.so,
        "South Africa" to R.drawable.za,
        "South Korea" to R.drawable.kr,
        "South Sudan" to R.drawable.ss,
        "Spain" to R.drawable.es,
        "Sri Lanka" to R.drawable.lk,
        "Sudan" to R.drawable.sd,
        "Suriname" to R.drawable.sr,
        "Sweden" to R.drawable.se,
        "Switzerland" to R.drawable.ch,
        "Syria" to R.drawable.sy,
        "Tajikistan" to R.drawable.tj,
        "Tanzania" to R.drawable.tz,
        "Thailand" to R.drawable.th,
        "Togo" to R.drawable.tg,
        "Tonga" to R.drawable.to,
        "Trinidad and Tobago" to R.drawable.tt,
        "Tunisia" to R.drawable.tn,
        "Turkey" to R.drawable.tr,
        "Turkmenistan" to R.drawable.tm,
        "Tuvalu" to R.drawable.tv,
        "Uganda" to R.drawable.ug,
        "Ukraine" to R.drawable.ua,
        "United Arab Emirates" to R.drawable.ae,
        "United Kingdom" to R.drawable.gb,
        "United States" to R.drawable.us,
        "Uruguay" to R.drawable.uy,
        "Uzbekistan" to R.drawable.uz,
        "Vanuatu" to R.drawable.vu,
        "Vatican City" to R.drawable.va,
        "Venezuela" to R.drawable.ve,
        "Vietnam" to R.drawable.vn,
        "Yemen" to R.drawable.ye,
        "Zambia" to R.drawable.zm,
        "Zimbabwe" to R.drawable.zw
    )
    return allCountries.getOrDefault(country, R.drawable.ex_yugoslavia)
}