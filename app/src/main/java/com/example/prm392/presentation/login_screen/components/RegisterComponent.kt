import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.prm392.domain.model.User.Request.RegisterRequestModel
import com.example.prm392.ui.theme.icons.AddressIcon
import com.example.prm392.ui.theme.icons.LockIcon
import com.example.prm392.ui.theme.icons.MailIcon
import com.example.prm392.ui.theme.icons.PhoneIcon
import com.example.prm392.ui.theme.icons.UserIcon

@Composable
fun RegisterComponent() {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()) // Adds padding for system bars
            .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) // Adjusts padding for keyboard
            .verticalScroll(rememberScrollState()), // Enable scrolling when content is larger than screen
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username", style = MaterialTheme.typography.labelLarge) },
                leadingIcon = {
                    Icon(
                        contentDescription = null,
                        imageVector = UserIcon,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", style = MaterialTheme.typography.labelLarge) },
                leadingIcon = {
                    Icon(
                        contentDescription = null,
                        imageVector = LockIcon,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", style = MaterialTheme.typography.labelLarge) },
                leadingIcon = {
                    Icon(
                        contentDescription = null,
                        imageVector = MailIcon,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = "Phone Number", style = MaterialTheme.typography.labelLarge) },
                leadingIcon = {
                    Icon(
                        contentDescription = null,
                        imageVector = PhoneIcon,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(text = "Address", style = MaterialTheme.typography.labelLarge) },
                leadingIcon = {
                    Icon(
                        contentDescription = null,
                        imageVector = AddressIcon,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = false, // Allow multi-line for address
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val registerRequest = RegisterRequestModel(
                    username = username.text,
                    password = password.text,
                    email = email.text,
                    phoneNumber = phoneNumber.text,
                    address = address.text
                )
                // Call the registration function here
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 4.dp)
            )
        }
    }
}
