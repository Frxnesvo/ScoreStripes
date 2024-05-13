import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import kotlinx.coroutines.launch

class ClubViewModel : ViewModel() {
    private val _clubsNames = MutableStateFlow<List<String>>(emptyList())
    val clubsNames: Flow<List<String>> = _clubsNames.asStateFlow()

    private val clubApiServiceImpl = ClubApiServiceImpl()

    init {
        fetchClubsNames()
    }

    private fun fetchClubsNames() {
        viewModelScope.launch {
            _clubsNames.value = clubApiServiceImpl.getClubSNames()
        }
    }

    fun addClub(name: String, image: Bitmap?, league: String) {
        //TODO: Implementa l'aggiunta di un club
    }

    fun updateClub(name: String, image: Bitmap) {
        //TODO: Implementa l'aggiornamento di un club
    }
}
