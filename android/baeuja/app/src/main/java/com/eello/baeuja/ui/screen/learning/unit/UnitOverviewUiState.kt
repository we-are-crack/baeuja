package com.eello.baeuja.ui.screen.learning.unit

data class UnitOverviewUiState(
    val isLoading: Boolean = false,
    val isLoadCompleted: Boolean = false,
    val isFailure: Boolean = false,
    val title: String = "",
    val units: List<ContentUnitUiModel> = emptyList()
)