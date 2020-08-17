package com.vclinic.ui.constant

object ApplicationConstant {

    private const val REQUEST_BASE_URL = "https://AmitMulay.github.io/Json/"
    const val PET_INFO = REQUEST_BASE_URL+"petinfo.json"
    const val SETTINGS = REQUEST_BASE_URL+"config.json"
    const val API_TAG_SETTINGS = 1
    const val API_TAG_PETINFO = 2

    const val TAG_PETS = "pets"
    const val TAG_IMAGE_URL = "image_url"
    const val TAG_TITLE = "title"
    const val TAG_CONTENT_URL = "content_url"
    const val TAG_DATE_ADDED = "date_added"
    const val TAG_SETTINGS = "settings"
    const val TAG_IS_CHAT_ENABLED = "isChatEnabled"
    const val TAG_IS_CALL_ENABLED = "isCallEnabled"
    const val TAG_WORK_HOURS = "workHours"
    const val LOGGING = true
    const val ARGUMENT_CONTENT_URL = "CONTENT_URL"
    const val ARGUMENT_TITLE = "TITLE"
}