import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.clientadmin.service.impl.ClubApiServiceImpl
import kotlinx.coroutines.launch

class ClubViewModel : ViewModel() {
    private val _list = MutableStateFlow<List<String>>(emptyList())
    val clubsNames: Flow<List<String>> = _list.asStateFlow()

    private val clubApiServiceImpl = ClubApiServiceImpl()

    init {
        fetchClubsNames()
    }

    private fun fetchClubsNames() {
        viewModelScope.launch {
            _list.value = clubApiServiceImpl.getClubSNames()
        }
    }

    fun addClub(name: String, pic: Bitmap, league: String) {
        viewModelScope.launch {
            clubApiServiceImpl.createClub(name, pic)
        }
    }

    fun updateClub(name: String, image: Bitmap) {
        //TODO: Implementa l'aggiornamento di un club
    }
}
