package com.example.id_generation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.id_generation.R
import com.example.id_generation.data.entity.Admin

@Composable
fun AdminList(
    admins: List<Admin>,
    onAdminClick: (Admin) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        var searchText by remember { mutableStateOf("") }
        
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(stringResource(R.string.search)) },
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        if (admins.isEmpty()) {
            Text(
                text = stringResource(R.string.no_admins),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn {
                val filteredAdmins = if (searchText.isBlank()) {
                    admins
                } else {
                    admins.filter { 
                        it.id.toString().contains(searchText, ignoreCase = true) || 
                        it.nom.contains(searchText, ignoreCase = true)
                    }
                }
                
                items(filteredAdmins) { admin ->
                    AdminListItem(admin = admin, onClick = { onAdminClick(admin) })
                }
            }
        }
    }
}

@Composable
fun AdminListItem(admin: Admin, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(R.string.id_label)}: ${admin.id}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${stringResource(R.string.name_label)}: ${admin.nom}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}