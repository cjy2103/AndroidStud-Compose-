package com.example.volumncontrol.system

import android.content.Context
import android.media.AudioManager
import androidx.core.content.ContextCompat.getSystemService

fun systemSoundUp(audioManager: AudioManager) {
    // 현재 음량 가져오기
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)

    // 최대 음량 가져오기
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)

    // 새로운 음량 설정 (현재 음량 + 1)
    val newVolume = currentVolume + 1

    // 최대 음량을 넘지 않도록 설정
    val adjustedVolume = if (newVolume > maxVolume) maxVolume else newVolume

    // 시스템 음량 설정
    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, adjustedVolume, 0)
}

fun mediaSoundUp(audioManager: AudioManager){
    // 현재 미디어 음량 가져오기
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    // 최대 미디어 음량 가져오기
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    // 새로운 미디어 음량 설정 (현재 음량 + 1)
    val newVolume = currentVolume + 1

    // 최대 미디어 음량을 넘지 않도록 설정
    val adjustedVolume = if (newVolume > maxVolume) maxVolume else newVolume

    // 미디어 음량 설정
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, adjustedVolume, 0)
}

fun notiSoundUp(audioManager: AudioManager){
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)

    // 최대 알림 음량 가져오기
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)

    // 새로운 알림 음량 설정 (현재 음량 + 1)
    val newVolume = currentVolume + 1

    // 최대 알림 음량을 넘지 않도록 설정
    val adjustedVolume = if (newVolume > maxVolume) maxVolume else newVolume

    // 알림 음량 설정
    audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, adjustedVolume, 0)
}

fun ringSoundUp(audioManager: AudioManager){
    // 현재 벨소리 음량 가져오기
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING)

    // 최대 벨소리 음량 가져오기
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

    // 새로운 벨소리 음량 설정 (현재 음량 + 1)
    val newVolume = currentVolume + 1

    // 최대 벨소리 음량을 넘지 않도록 설정
    val adjustedVolume = if (newVolume > maxVolume) maxVolume else newVolume

    // 벨소리 음량 설정
    audioManager.setStreamVolume(AudioManager.STREAM_RING, adjustedVolume, 0)
}