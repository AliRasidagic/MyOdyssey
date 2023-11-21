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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.travelapp.R
import com.example.travelapp.data.TravelInfo
import com.example.travelapp.view_models.AddTripViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrip(
    modifier: Modifier = Modifier,
    viewModel: AddTripViewModel
) {
    val uiState = viewModel.state

    var title by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val isButtonEnabled = title.isNotEmpty() &&
            uiState.selectedContinent != "Continent" &&
            uiState.selectedCountry != "Country" &&
            city.isNotEmpty() &&
            uiState.startDate != "Start Date" &&
            uiState.endDate != "End Date" &&
            info.isNotEmpty() &&
            selectedImageUri != null

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(600.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.Black)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = modifier.padding(top = 20.dp),
                label = {
                    Text(
                        text = "Title",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )
            Image(
                painter = if (selectedImageUri == null) painterResource(R.drawable.empty) else rememberAsyncImagePainter(
                    selectedImageUri
                ),
                contentDescription = null,
                modifier = modifier
                    .padding(20.dp)
                    .size(100.dp)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentScale = ContentScale.Crop
            )

            selectedImageUri?.let {
                LocalContext.current.contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            Row {
                StartDatePicker(viewModel)
                Spacer(modifier = Modifier.width(10.dp))
                EndDatePicker(viewModel)
            }
            Row {
                ContinentDropdown(viewModel)
                CountryDropdown(uiState.selectedContinent, viewModel)
            }
            TextField(
                value = city,
                onValueChange = { city = it },
                modifier = modifier
                    .padding(top = 8.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(20.dp)),
                label = {
                    Text(
                        text = "City",
                        color = Color.Black
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                singleLine = true,
                textStyle = TextStyle(fontSize = 12.sp)
            )
            OutlinedTextField(
                value = info,
                onValueChange = { info = it },
                modifier = modifier
                    .padding(top = 10.dp)
                    .height(100.dp),
                label = {
                    Text(
                        text = "Description",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            val travelInfo = TravelInfo(
                title = title,
                continent = uiState.selectedContinent,
                country = uiState.selectedCountry,
                city = city,
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                info = info,
                image = selectedImageUri.toString()
            )

            Button(
                onClick = {
                    viewModel.addTrip(travelInfo)
                    viewModel.onShowChange(false)
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = modifier
                    .padding(top = 15.dp)
                    .clip(RoundedCornerShape(20.dp)),
                enabled = isButtonEnabled
            ) {
                Text(
                    text = "Add Trip",
                    color = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentDropdown(
    viewModel: AddTripViewModel
) {
    val uiState = viewModel.state

    var expanded by remember { mutableStateOf(false) }
    val continents = listOf(
        "Europe",
        "Asia",
        "North America",
        "South America",
        "Africa",
        "Oceania"
    )

    Box(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            var isTextFieldClicked by remember { mutableStateOf(false) }
            TextField(
                value = uiState.selectedContinent,
                onValueChange = { text ->
                    viewModel.onContinentChange(text)
                },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .height(50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        isTextFieldClicked = true
                    },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                singleLine = true,
                textStyle = TextStyle(fontSize = 12.sp)
            )
            LazyColumn {
                item {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                            isTextFieldClicked = false
                        },
                        modifier = Modifier
                            .background(color = Color.White)
                    ) {
                        continents.forEach { continents ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = continents
                                    )
                                },
                                onClick = {
                                    viewModel.onContinentChange(continents)
                                    viewModel.onCountryChange("Country")
                                    expanded = false
                                },
                                modifier = Modifier.background(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(
    selectedContinent: String,
    viewModel: AddTripViewModel
) {
    val uiState = viewModel.state

    var expanded by remember { mutableStateOf(false) }
    val allCountries = listOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Brazil",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cape Verde",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Comoros",
        "Congo",
        "Costa Rica",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "North Korea",
        "DR Congo",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "East Timor",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Eswatini",
        "Ethiopia",
        "Fiji",
        "Finland",
        "France",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Greece",
        "Grenada",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Israel",
        "Italy",
        "Ivory Coast",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kosovo",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Morocco",
        "Mozambique",
        "Myanmar",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "North Macedonia",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Qatar",
        "Romania",
        "Russia",
        "Rwanda",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Vincent and the Grenadines",
        "Samoa",
        "San Marino",
        "Sao Tome and Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Korea",
        "South Sudan",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Sweden",
        "Switzerland",
        "Syria",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Togo",
        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "Uruguay",
        "Uzbekistan",
        "Vanuatu",
        "Vatican City",
        "Venezuela",
        "Vietnam",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )

    val filteredCountries = when (selectedContinent) {
        "Europe" -> filterCountriesByContinent(allCountries, "Europe")
        "Asia" -> filterCountriesByContinent(allCountries, "Asia")
        "North America" -> filterCountriesByContinent(allCountries, "North America")
        "South America" -> filterCountriesByContinent(allCountries, "South America")
        "Africa" -> filterCountriesByContinent(allCountries, "Africa")
        "Oceania" -> filterCountriesByContinent(allCountries, "Oceania")
        else -> allCountries
    }
    filteredCountries.sorted()

    Box(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            var isTextFieldClicked by remember { mutableStateOf(false) }
            TextField(
                value = uiState.selectedCountry,
                onValueChange = { text ->
                    viewModel.onCountryChange(text)
                },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(20.dp))
                    .height(50.dp)
                    .clickable {
                        isTextFieldClicked = true
                    },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                singleLine = true,
                textStyle = TextStyle(fontSize = 12.sp)
            )
            LazyColumn {
                item {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                            isTextFieldClicked = false
                        },
                        modifier = Modifier
                            .background(color = Color.White)
                    ) {
                        filteredCountries.forEach { countries ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = countries
                                    )
                                },
                                onClick = {
                                    viewModel.onCountryChange(countries)
                                    expanded = false
                                },
                                modifier = Modifier.background(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun filterCountriesByContinent(countries: List<String>, continent: String): List<String> {
    return countries.filter { isCountryInContinent(it, continent) }
}

private fun isCountryInContinent(country: String, continent: String): Boolean {
    return when (continent) {
        "Europe" -> country in listOf(
            "Albania",
            "Andorra",
            "Austria",
            "Belarus",
            "Belgium",
            "Bosnia & Herzegovina",
            "Bulgaria",
            "Croatia",
            "Cyprus",
            "Czechia",
            "Denmark",
            "Estonia",
            "Finland",
            "France",
            "Germany",
            "Greece",
            "Hungary",
            "Iceland",
            "Ireland",
            "Italy",
            "Latvia",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Malta",
            "Moldova",
            "Monaco",
            "Montenegro",
            "North Macedonia",
            "Netherlands",
            "Norway",
            "Poland",
            "Portugal",
            "Romania",
            "Russia",
            "San Marino",
            "Serbia",
            "Slovakia",
            "Slovenia",
            "Spain",
            "Sweden",
            "Switzerland",
            "Ukraine",
            "United Kingdom",
            "Vatican City"
        )

        "Asia" -> country in listOf(
            "China",
            "India",
            "Japan",
            "South Korea",
            "Afghanistan",
            "Bangladesh",
            "Bhutan",
            "Brunei",
            "Cambodia",
            "Indonesia",
            "Iran",
            "Iraq",
            "Israel",
            "Jordan",
            "Kazakhstan",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Lebanon",
            "Malaysia",
            "Maldives",
            "Mongolia",
            "Myanmar",
            "Nepal",
            "Oman",
            "Pakistan",
            "Palestine",
            "Philippines",
            "Qatar",
            "Saudi Arabia",
            "Singapore",
            "Sri Lanka",
            "Syria",
            "Tajikistan",
            "Thailand",
            "Timor-Leste",
            "Turkey",
            "Turkmenistan",
            "United Arab Emirates",
            "Uzbekistan",
            "Vietnam",
            "Yemen"
        )

        "North America" -> country in listOf(
            "United States", "Canada", "Mexico"
        )

        "South America" -> country in listOf(
            "Brazil", "Argentina", "Chile", "Peru", "Bolivia", "Colombia", "Ecuador", "Guyana",
            "Paraguay", "Suriname", "Uruguay", "Venezuela"
        )

        "Africa" -> country in listOf(
            "Nigeria",
            "South Africa",
            "Egypt",
            "Kenya",
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cameroon",
            "Cape Verde",
            "Central African Republic",
            "Chad",
            "Comoros",
            "Congo - Brazzaville",
            "Congo - Kinshasa",
            "Djibouti",
            "Equatorial Guinea",
            "Eritrea",
            "Eswatini",
            "Ethiopia",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Guinea-Bissau",
            "Ivory Coast",
            "Lesotho",
            "Liberia",
            "Libya",
            "Madagascar",
            "Malawi",
            "Mali",
            "Mauritania",
            "Mauritius",
            "Morocco",
            "Mozambique",
            "Namibia",
            "Niger",
            "Rwanda",
            "Sao Tome & Principe",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Sudan",
            "Sudan",
            "Tanzania",
            "Togo",
            "Tunisia",
            "Uganda",
            "Zambia",
            "Zimbabwe"
        )

        "Oceania" -> country in listOf(
            "Australia",
            "New Zealand",
            "Fiji",
            "Papua New Guinea",
            "Solomon Islands",
            "Vanuatu",
            "Samoa",
            "Tonga",
            "Tuvalu",
            "Kiribati",
            "Marshall Islands",
            "Micronesia",
            "Nauru",
            "Palau"
        )

        else -> false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartDatePicker(
    viewModel: AddTripViewModel
) {
    val uiState = viewModel.state
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            viewModel.onStartDateChange(date.toString())
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    Button(
        onClick = { calendarState.show() },
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = uiState.startDate,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EndDatePicker(
    viewModel: AddTripViewModel
) {
    val uiState = viewModel.state
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            viewModel.onEndDateChange(date.toString())
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            disabledTimeline = CalendarTimeline.PAST
        )
    )
    Button(
        onClick = { calendarState.show() },
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = uiState.endDate,
            color = Color.Black
        )
    }
}
