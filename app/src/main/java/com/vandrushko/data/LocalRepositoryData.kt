package com.vandrushko.data

import com.vandrushko.data.model.Contact


class LocalRepositoryData {
    fun getLocalContactsList(): List<Contact> = listOf(
        Contact("ddsfsdf@dfgh.f", "Aa123@#zc").apply {
            name = "1"
            career = "asd"
            image = "https://upload.wikimedia.org/wikipedia/commons/4/4d/Cat_November_2010-1a.jpg"
        },
        Contact("ddsfsdf@dfgh.f", "Aa123@#zc").apply {
            name = "2"
            career = "asd"
            image = "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg"
        },
        Contact("ddsfsdf@dfgh.f", "Aa123@#zc").apply {
            name = "3"
            career = "asd"
            image = "https://cdn.theatlantic.com/thumbor/W544GIT4l3z8SG-FMUoaKpFLaxE=/0x131:2555x1568/1600x900/media/img/mt/2017/06/shutterstock_319985324/original.jpg"
        },
        Contact("ddsfsdf@dfgh.f", "Aa123@#zc").apply {
            name = "4"
            career = "asd"
            image = "https://upload.wikimedia.org/wikipedia/commons/4/4d/Cat_November_2010-1a.jpg"
        },
    )
}