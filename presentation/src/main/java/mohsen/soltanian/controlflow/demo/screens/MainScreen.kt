package mohsen.soltanian.controlflow.demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import mohsen.soltanian.controlflow.demo.component.ErrorView
import mohsen.soltanian.controlflow.demo.component.ProgressDialog
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResHotelData
import mohsen.soltanian.controlflow.demo.core.framework.base.ViewState
import mohsen.soltanian.controlflow.demo.core.framework.extensions.cast
import mohsen.soltanian.demo.controlflow.data.extensions.empty
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainScreen (viewModel: MainViewModel = hiltViewModel()){
    val uiState by viewModel.uiState.collectAsState()

    when(uiState) {
        is ViewState.Loading -> {
            ProgressDialog {}
        }
        is ViewState.Data<*> -> {
           val state = uiState.cast<ViewState.Data<MainContract.State.HotelsData>>().value
            HotelList(hotels = state.hotels)
        }
        is ViewState.Error -> {
          val state = uiState.cast<ViewState.Error>().throwable
          ErrorView(errMessage = state?.message ?: String.empty()){
              viewModel.onTriggerEvent(eventType = MainContract.Event.DoAuthorizationAndGetHotels)
          }
        }

        else -> {}
    }

    LaunchedEffect(key1 = viewModel, block = {
        viewModel.onTriggerEvent(eventType = MainContract.Event.DoAuthorizationAndGetHotels)
    })

}
@Composable
fun HotelList(hotels: List<ResHotelData>? = listOf()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(hotels?.size ?: -1) { index ->
            if(index != -1) HotelCard(hotel = hotels?.get(index))
        }
    }
}

@Composable
fun HotelCard(hotel: ResHotelData?) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = hotel?.name ?: String.empty(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            HotelInfoRow(label = "Chain Code:", value = hotel?.chainCode ?: String.empty())
            HotelInfoRow(label = "IATA Code:", value = hotel?.iataCode ?: String.empty())
            HotelInfoRow(label = "Hotel ID:", value = hotel?.hotelId ?: String.empty())
            HotelInfoRow(label = "Country Code:", value = hotel?.chainCode ?: String.empty())
            HotelInfoRow(
                label = "Location:",
                value = "Lat: ${hotel?.geoCode?.latitude}, Lon: ${hotel?.geoCode?.longitude}"
            )
            HotelInfoRow(label = "Last Update:", value = formatDate(hotel?.lastUpdate ?: String.empty()))
        }
    }
}

// Composable function for a single row of hotel information
@Composable
fun HotelInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier.weight(2f)
        )
    }
}

// Function to convert ISO date string to a formatted date string
fun formatDate(isoDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = inputFormat.parse(isoDate)
    return outputFormat.format(date!!)
}



