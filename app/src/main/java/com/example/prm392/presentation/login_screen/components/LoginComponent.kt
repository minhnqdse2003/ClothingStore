import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.presentation.login_screen.LoginViewModel
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.ui.theme.icons.LockIcon
import com.example.prm392.ui.theme.icons.UserIcon
import com.example.prm392.utils.Result

@Composable
fun LoginComponent(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val loginServiceResponse = viewModel.loginService.collectAsState().value

    // Focus requesters for fields
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                usernameFocusRequester.requestFocus()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Use insets to adjust padding
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.systemBars.asPaddingValues()) // Applies padding for system bars
            .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) // Adjust for keyboard insets
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username",
                    style = MaterialTheme.typography.labelLarge
                )
            },
            leadingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = UserIcon,
                    tint = ButtonDefaults.buttonColors().containerColor,
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }  // Move to password field on "Next"
            ),
            modifier = Modifier
                .focusRequester(usernameFocusRequester)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelLarge
                )
            },
            leadingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = LockIcon,
                    tint = ButtonDefaults.buttonColors().containerColor,
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() } // Dismiss keyboard on "Done"
            ),
            modifier = Modifier
                .focusRequester(passwordFocusRequester)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                focusManager.clearFocus()  // Clear focus to hide keyboard
                viewModel.onClickButtonLogin(
                    LoginRequestModel(
                        username = username,
                        password = password,
                        expiresInMins = 30
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(),
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Login",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(
                        horizontal = 0.dp,
                        vertical = 4.dp
                    )
            )
        }

        when (loginServiceResponse) {
            is Result.Loading -> LoadingScreen()

            is Result.Success -> {
                navHostController.navigate(Screen.HomeScreen.route)
                Log.d("LoginComps", "Success" )
            }

            is Result.Error -> {
                Text(
                    loginServiceResponse.error.localizedMessage ?: "An error occurred."
                )
                LaunchedEffect(Unit) {
                    // Refocus on username field if login fails
                    usernameFocusRequester.requestFocus()
                }
            }

            else -> Unit
        }
    }
}
