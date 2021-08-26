package br.com.dio.coinconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getExchangeValueUseCase: GetExchangeValueUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getExchangeValue(coins: String) {
        viewModelScope.launch {
            getExchangeValueUseCase(coins)
                .flowOn(Dispatchers.Main)
                .onStart {
                    // usar para mostrar a nossa dialog de progresso
                    _state.value = State.Loading
                }
                .catch {
                    // usar para mostrar um erro
                    _state.value = State.Error(it)
                }
                .collect {
                    // usar para mostrar o resultado
                    _state.value = State.Success(it)
                }
        }
    }

    sealed class State {
        object Loading: State()

        data class Success(internal val exchange: ExchangeResponseValue): State()
        data class Error(val error: Throwable): State()
    }
}