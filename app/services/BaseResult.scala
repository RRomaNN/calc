package services

//Base response class for all services with code and error message
abstract class BaseResult(val errorCode: Int, val errorMessage: String) 