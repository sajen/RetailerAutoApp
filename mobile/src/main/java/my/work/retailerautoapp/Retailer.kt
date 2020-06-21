package my.work.retailerautoapp

class Retailer {
    private var retailerName: String? = null
    private  var retailerAddress:kotlin.String? = null
    private var latitude = 0.0
    private  var longitude:kotlin.Double = 0.0

    fun getRetailerName(): String? {
        return retailerName
    }

    fun setRetailerName(retailerName: String?) {
        this.retailerName = retailerName
    }

    fun getRetailerAddress(): String? {
        return retailerAddress
    }

    fun setRetailerAddress(retailerAddress: String) {
        this.retailerAddress = retailerAddress
    }

    fun getLatitude(): Double {
        return latitude
    }

    fun setLatitude(latitude: Double) {
        this.latitude = latitude
    }

    fun getLongitude(): Double {
        return longitude
    }

    fun setLongitude(longitude: Double) {
        this.longitude = longitude
    }
}