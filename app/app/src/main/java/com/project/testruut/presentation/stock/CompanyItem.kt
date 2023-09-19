package com.project.testruut.presentation.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.testruut.domain.model.CompanyListing

@Composable
fun CompanyItem(company: CompanyListing,
                modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${company.symbol} (${company.series})",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$${company.low}",
                    fontSize = 16.sp,
                    fontStyle= FontStyle.Italic,
                    color = Color.Red,
                    modifier=Modifier.padding(start = 8.dp,top=4.dp)
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${company.high}",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Green
            )
        }

    }

}