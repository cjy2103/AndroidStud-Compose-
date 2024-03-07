package com.example.stt.vm

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Locale

class SpeechToTextViewModel(application: Application) : ViewModel() {
    private val speechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(application.applicationContext)
    }

    private val _sttText = mutableStateOf("")

    val sttText : String get() = _sttText.value



    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                Log.v("준비완료","테스트 준비")
            }

            override fun onBeginningOfSpeech() {
                // 사용자가 말하기 시작할 때 호출됩니다.
            }

            override fun onRmsChanged(rmsdB: Float) {
                // 소리의 강도가 변경될 때 호출됩니다.
            }

            override fun onBufferReceived(buffer: ByteArray?) {
            }

            override fun onEndOfSpeech() {
                // 사용자가 말하기를 멈출 때 호출됩니다.
            }

            override fun onError(error: Int) {
                Log.v("오류발생",error.toString())
            }

            override fun onResults(results: Bundle?) {
                // 인식 결과를 처리합니다.
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val resultText = matches[0]
                    Log.v("결과",resultText)
                    _sttText.value = resultText
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
            }

        })
    }

    fun startSpeechToText(){

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        viewModelScope.launch {
            speechRecognizer.startListening(intent)
        }
    }


}