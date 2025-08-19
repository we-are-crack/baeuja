package com.eello.baeuja.ui.screen.learning.unit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eello.baeuja.ui.screen.learning.unit.ContentUnitUiModel

@Composable
fun ContentUnitMetaRow(
    sequence: Int = 1,
    contentUnit: ContentUnitUiModel
) {
    when (contentUnit) {
        is ContentUnitUiModel.PopUnitUiModel -> {
            PopContentUnitMetaRow(sequence = sequence, contentUnit = contentUnit)
        }

        is ContentUnitUiModel.VisualUnitUiModel -> {
            VisualContentUniMetaRow(contentUnit = contentUnit)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewContentUnitMetaRow() {

}