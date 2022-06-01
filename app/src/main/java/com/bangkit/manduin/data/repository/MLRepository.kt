package com.bangkit.manduin.data.repository

import android.content.Context
import android.util.Log
import com.bangkit.manduin.model.LabelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class MLRepository @Inject constructor(
    private val context: Context
) {
    fun getModelLabelsFromFile() = flow {
        val reader = BufferedReader(InputStreamReader(context.assets.open("labels.txt")))
        val labelList = mutableListOf<LabelModel>()
        var label: List<String>

        reader.lineSequence().forEach { result ->
            label = result.split("|")
            if (label.isNotEmpty()) {
                labelList.add(LabelModel(label[0], label[1], label[2].toInt()))
            }
        }
        reader.close()
        emit(labelList)
        Log.d("TEST", labelList.toString())
    }.flowOn(Dispatchers.IO)
}