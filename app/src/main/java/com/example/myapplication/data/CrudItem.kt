package com.example.myapplication.data

import java.util.UUID

data class CrudItem(val id: String = UUID.randomUUID().toString(), val text: String)