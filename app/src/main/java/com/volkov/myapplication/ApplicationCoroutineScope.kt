package com.volkov.myapplication

import kotlinx.coroutines.*

/**
 * Scope корутин, которые при запуске будут существовать,
 * пока приложение не закроется/не завершится корутина.
 * Стоит использовать только тогда, когда нужно обеспечить завершенность операции,
 * независимо от переключения экранов (например, запись в БД)
 *
 * @warning: не создавайте тут [MainScope] или scope с [Dispatchers.Main]
 * при их использовании в контексте appScope, есть риски утечки памяти (context, view и т.д.)
 */

// application scope IO-корутины
val appScopeIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)

// application scope Default-корутины
val appScopeDefault = CoroutineScope(SupervisorJob() + Dispatchers.Default)