package bogusz.com.service.model

data class AppSettings(
    var darkMode: Boolean,
    var sendingSms: Boolean,
    var sendingLocation: Boolean,
    var sensitivity: Sensitivity
)