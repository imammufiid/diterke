package com.mufiid.core.stateevent

import kotlinx.coroutines.flow.Flow

typealias FlowState<T> = Flow<StateEvent<T>>