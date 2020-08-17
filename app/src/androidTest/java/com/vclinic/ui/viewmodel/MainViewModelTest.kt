package com.vclinic.ui.viewmodel

import com.vclinic.ui.model.Pet
import com.vclinic.ui.model.Settings
import com.vclinic.ui.parser.PetInfoParser
import com.vclinic.ui.parser.SettingsParser
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest{

    @Test
    fun petsInfo() {
        val petList: ArrayList<Pet>? = ArrayList()
        petList?.add(Pet(
            "Cat",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Cat_poster_1.jpg/1200px-Cat_poster_1.jpg",
            "https://en.wikipedia.org/wiki/Cat",
            "2018-06-02T03:27:38.027Z"))
        petList?.add(Pet("Rabbit",
            "https://upload.wikimedia.org/wikipedia/commons/3/30/RabbitMilwaukee.jpg",
            "https://en.wikipedia.org/wiki/Rabbit",
            "2018-06-06T08:36:16.027Z"))
        val parsedPetList=PetInfoParser.parseSucessResponse("{\n" +
                "\t\"pets\": [{\n" +
                "\t\t\t\"image_url\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Cat_poster_1.jpg/1200px-Cat_poster_1.jpg\",\n" +
                "\t\t\t\"title\": \"Cat\",\n" +
                "\t\t\t\"content_url\": \"https://en.wikipedia.org/wiki/Cat\",\n" +
                "\t\t\t\"date_added\": \"2018-06-02T03:27:38.027Z\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"image_url\": \"https://upload.wikimedia.org/wikipedia/commons/3/30/RabbitMilwaukee.jpg\",\n" +
                "\t\t\t\"title\": \"Rabbit\",\n" +
                "\t\t\t\"content_url\": \"https://en.wikipedia.org/wiki/Rabbit\",\n" +
                "\t\t\t\"date_added\": \"2018-06-06T08:36:16.027Z\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}")
        if (parsedPetList != null) {
            assertEquals(parsedPetList, petList)
        }
    }

    @Test
    fun bothSettingsEnable() {
        val actual = Settings(
            isCallEnabled=true,
            isChatEnabled=true,
            workHours="M-F 9:00 - 18:00",
            officeHours = "Office Hours: M-F 9:00 - 18:00"
        )
        val expected= SettingsParser.parseSucessResponse("{\"settings\": {\n" +
                "\t\"isChatEnabled\" : true,\n" +
                "\t\"isCallEnabled\" : true,\n" +
                "\t\"workHours\" : \"M-F 9:00 - 18:00\"\n" +
                "}\n" +
                "}")
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun callEnableChatDisable() {
        val actual = Settings(
            isCallEnabled=true,
            isChatEnabled=false,
            workHours="M-F 9:00 - 18:00",
            officeHours = "Office Hours: M-F 9:00 - 18:00"
        )
        val expected= SettingsParser.parseSucessResponse("{\"settings\": {\n" +
                "\t\"isChatEnabled\" : false,\n" +
                "\t\"isCallEnabled\" : true,\n" +
                "\t\"workHours\" : \"M-F 9:00 - 18:00\"\n" +
                "}\n" +
                "}")
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun callDisableChatEnable() {
        val actual = Settings(
            isCallEnabled=false,
            isChatEnabled=true,
            workHours="M-F 9:00 - 18:00",
            officeHours = "Office Hours: M-F 9:00 - 18:00"
        )
        val expected= SettingsParser.parseSucessResponse("{\"settings\": {\n" +
                "\t\"isChatEnabled\" : true,\n" +
                "\t\"isCallEnabled\" : false,\n" +
                "\t\"workHours\" : \"M-F 9:00 - 18:00\"\n" +
                "}\n" +
                "}")
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun bothSettingsDisable() {
        val actual = Settings(
            isCallEnabled=false,
            isChatEnabled=false,
            workHours="M-F 9:00 - 18:00",
            officeHours = "Office Hours: M-F 9:00 - 18:00"
        )
        val expected= SettingsParser.parseSucessResponse("{\"settings\": {\n" +
                "\t\"isChatEnabled\" : false,\n" +
                "\t\"isCallEnabled\" : false,\n" +
                "\t\"workHours\" : \"M-F 9:00 - 18:00\"\n" +
                "}\n" +
                "}")
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun invalidJsonOfSetting() {
        val invalidJson=SettingsParser.parseSucessResponse("{\": {\n" +
                "\t\"isChatEnabled\" : false,\n" +
                "\t\"isCallEnabled\" : false,\n" +
                "\t\"workHours\" : \"M-F 9:00 - 18:00\"\n" +
                "}\n" +
                "}")
        assertEquals(invalidJson, null)
    }

}

