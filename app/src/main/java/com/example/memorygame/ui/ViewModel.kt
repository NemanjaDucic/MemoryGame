package com.example.memorygame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemmoryGameViewModel : ViewModel() {
    private val cards: MutableLiveData<MutableList<CardsModel>> by lazy {
        MutableLiveData<MutableList<CardsModel>>()
    }

    fun getCards(): LiveData<MutableList<CardsModel>> {
        return cards
    }

    fun randomizeCards() {
        cards.value=mutableListOf(
            CardsModel("1"),
            CardsModel("2"),
            CardsModel("3"),
            CardsModel("4"),
            CardsModel("5"),
            CardsModel("6"),
            CardsModel("1"),
            CardsModel("2"),
            CardsModel("3"),
            CardsModel("4"),
            CardsModel("5"),
            CardsModel("6"),

        ).apply { shuffle() }
    }

    fun updateShowVisibleCard(id: String) {
        val selects: List<CardsModel>?=cards.value?.filter { it -> it.isSelect }
        val selectCount: Int=selects?.size ?: 0
        var charFind: String="";
        if (selectCount >= 2) {
            val hasSameChar: Boolean=selects!!.get(0).char == selects.get(1).char
            if (hasSameChar) {
                charFind=selects[0].char
            }
        }

        val list: MutableList<CardsModel>?=cards.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect=false
            }

            if (it.char == charFind) {
                it.isVisible=false
            }

            if (it.id == id) {
                it.isSelect=true
            }

            it
        } as MutableList<CardsModel>?

        val visibleCount: Int=list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            randomizeCards()
            return
        }

        cards.value?.removeAll { true }
        cards.value=list
    }
}