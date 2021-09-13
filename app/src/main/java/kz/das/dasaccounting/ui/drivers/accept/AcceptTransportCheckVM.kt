package kz.das.dasaccounting.ui.drivers.accept

import androidx.lifecycle.LiveData
import kz.das.dasaccounting.core.ui.utils.SingleLiveEvent
import kz.das.dasaccounting.core.ui.view_model.BaseVM
import kz.das.dasaccounting.domain.DriverInventoryRepository
import kz.das.dasaccounting.domain.UserRepository
import kz.das.dasaccounting.domain.data.drivers.TransportInventory
import org.koin.core.inject

class AcceptTransportCheckVM: BaseVM() {

    private val userRepository: UserRepository by inject()

    private var transportInventory: TransportInventory? = null

    private val transportInventoryLV = SingleLiveEvent<TransportInventory?>()
    fun getTransportInventory(): LiveData<TransportInventory?> = transportInventoryLV

    private val isOnAwaitLV = SingleLiveEvent<Boolean>()
    fun isOnAwait(): LiveData<Boolean> = isOnAwaitLV

    fun getUserRole() = userRepository.getUserRole()

    fun getUser() = userRepository.getUser()

    fun setLocalInventory(transportInventory: TransportInventory) {
        this.transportInventory = transportInventory
    }

    fun getLocalInventory() = transportInventory

    fun setTransportInventory(transportInventory: TransportInventory?) {
        this.transportInventory = transportInventory
        transportInventory?.receiverUUID = userRepository.getUser()?.userId
        transportInventory?.receiverName = userRepository.getUser()?.lastName + " "
        if (userRepository.getUser()?.firstName?.length ?: 0 > 0) {
            userRepository.getUser()?.firstName?.toCharArray()?.let { it[0] }?.plus(".")
        } else {
            ""
        }  +
                if (userRepository.getUser()?.middleName?.length ?: 0 > 0) {
                    userRepository.getUser()?.middleName?.toCharArray()?.let { it[0] }?.plus(".")
                } else {
                    ""
                }
        transportInventoryLV.postValue(transportInventory)
    }

    private val isTransportInventorySentLV = SingleLiveEvent<Boolean>()

    fun isTransportInventorySent(): LiveData<Boolean> = isTransportInventorySentLV

}