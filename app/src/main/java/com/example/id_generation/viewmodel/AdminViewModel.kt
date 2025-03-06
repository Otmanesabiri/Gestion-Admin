package com.example.id_generation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.id_generation.R
import com.example.id_generation.data.database.PersonnesDatabase
import com.example.id_generation.data.entity.Admin
import com.example.id_generation.data.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AdminRepository
    private val _currentId = MutableStateFlow("")
    val currentId = _currentId.asStateFlow()

    private val _currentNom = MutableStateFlow("")
    val currentNom = _currentNom.asStateFlow()

    private val _admins = MutableStateFlow<List<Admin>>(emptyList())
    val admins: StateFlow<List<Admin>> = _admins.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
    
    private val context = application.applicationContext

    init {
        val adminDao = PersonnesDatabase.getDatabase(application).adminDao()
        repository = AdminRepository(adminDao)
        viewModelScope.launch {
            repository.allAdmins.collect {
                _admins.value = it
            }
        }
    }

    fun updateId(id: String) {
        _currentId.value = id
    }

    fun updateNom(nom: String) {
        _currentNom.value = nom
    }

    fun saveAdmin() {
        if (_currentNom.value.isBlank()) {
            _message.value = context.getString(R.string.name_required)
            return
        }
        
        viewModelScope.launch {
            try {
                val admin = Admin(
                    nom = _currentNom.value
                )
                repository.insert(admin)
                clearInputs()
                _message.value = context.getString(R.string.save_success)
            } catch (e: Exception) {
                _message.value = context.getString(R.string.error_prefix) + e.message
            }
        }
    }

    fun updateAdmin() {
        if (_currentNom.value.isBlank()) {
            _message.value = context.getString(R.string.name_required)
            return
        }
        
        viewModelScope.launch {
            try {
                val id = _currentId.value.toIntOrNull()
                if (id != null) {
                    val admin = Admin(
                        id = id,
                        nom = _currentNom.value
                    )
                    repository.update(admin)
                    clearInputs()
                    _message.value = context.getString(R.string.update_success)
                } else {
                    _message.value = context.getString(R.string.invalid_id)
                }
            } catch (e: Exception) {
                _message.value = context.getString(R.string.error_prefix) + e.message
            }
        }
    }

    fun deleteAdmin() {
        viewModelScope.launch {
            try {
                val id = _currentId.value.toIntOrNull()
                if (id != null) {
                    val admin = repository.getAdminById(id)
                    if (admin != null) {
                        repository.delete(admin)
                        clearInputs()
                        _message.value = context.getString(R.string.delete_success)
                    } else {
                        _message.value = context.getString(R.string.admin_not_found)
                    }
                } else {
                    _message.value = context.getString(R.string.invalid_id)
                }
            } catch (e: Exception) {
                _message.value = context.getString(R.string.error_prefix) + e.message
            }
        }
    }

    private fun clearInputs() {
        _currentId.value = ""
        _currentNom.value = ""
    }
}
