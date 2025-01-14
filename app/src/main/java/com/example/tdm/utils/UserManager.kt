import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tdm.data.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

object UserManager {
    private val USER_KEY = stringPreferencesKey("user_data")
    private val gson = Gson()

    // Sauvegarder un utilisateur dans DataStore
    suspend fun saveUser(context: Context, user: User) {
        val userJson = gson.toJson(user) // Convertir l'utilisateur en JSON
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = userJson
        }
    }

    // Récupérer l'utilisateur depuis DataStore
    suspend fun getUser(context: Context): User? {
        val preferences = context.dataStore.data.first()
        val userJson = preferences[USER_KEY] // Obtenir le JSON
        return if (userJson != null) gson.fromJson(userJson, User::class.java) else null // Désérialiser en User
    }

    // Supprimer l'utilisateur de DataStore
    suspend fun clearUser(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }
}
