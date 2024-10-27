package com.example.prm392.presentation.components.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

class Animations {
    companion object {
        fun horizontalSlideIn(duration: Int = 300): EnterTransition {
            return slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutLinearInEasing
                )
            )
        }

        fun horizontalSlideOut(duration: Int = 300): ExitTransition {
            return slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(
                    durationMillis = duration,
                    easing = FastOutLinearInEasing
                )
            )
        }
    }
}