package bogusz.com.service.connectivity

interface ISmsService{
    fun sendMessage(number: Int, message: String)
}