package com.may.vknews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardPost() {
    Card(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column {
            HeadContent()
            MainContent()
            BottomContent()
        }
    }

}

@Composable
fun HeadContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.bigel),
            contentDescription = stringResource(R.string.avatar)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.group_name),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.time),
                color = Color.LightGray
            )
        }
        IconButton(
            onClick = { },
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.icon_button_more_vert),
            )
        }
    }
}

@Composable
fun MainContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.main_content)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.akita_inu),
            contentDescription = stringResource(R.string.image_akita_inu),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BottomContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            TextAndIcon(
                text = "206",
                icon = Icons.Filled.Person
            )
        }
        
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextAndIcon(
                text = "206",
                icon = Icons.Outlined.Share
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextAndIcon(
                text = "11",
                icon = Icons.Outlined.Create
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextAndIcon(
                text = "491",
                icon = Icons.Outlined.Favorite
            )
        }

    }


}

@Composable
fun TextAndIcon(text: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text)
        Spacer(modifier = Modifier.width(2.dp))
        Icon(imageVector = icon, contentDescription = stringResource(R.string.image))
    }
}

@Preview
@Composable
fun PreviewPostCard() {
    CardPost()
}

