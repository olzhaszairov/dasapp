package kz.das.dasaccounting.data.source.local

import android.content.Context
import androidx.room.*
import kz.das.dasaccounting.data.entities.driver.AcceptedTransportEntity
import kz.das.dasaccounting.data.entities.driver.FligelProductEntity
import kz.das.dasaccounting.data.entities.driver.SentTransportEntity
import kz.das.dasaccounting.data.entities.driver.TransportInventoryEntity
import kz.das.dasaccounting.data.entities.office.OfficeInventoryAcceptedEntity
import kz.das.dasaccounting.data.entities.office.OfficeInventoryEntity
import kz.das.dasaccounting.data.entities.office.OfficeInventorySentEntity
import kz.das.dasaccounting.data.entities.warehouse.WarehouseInventoryEntity
import kz.das.dasaccounting.data.source.local.daos.*
import kz.das.dasaccounting.data.source.local.typeconvertors.DriverInventoryTypeConvertor
import kz.das.dasaccounting.data.source.local.typeconvertors.OfficeInventoryEntityTypeConvertor

@Database(version = 5, exportSchema = false, entities = [OfficeInventoryEntity::class,
    OfficeInventoryAcceptedEntity::class,
    OfficeInventorySentEntity::class,
    WarehouseInventoryEntity::class,
    TransportInventoryEntity::class,
    SentTransportEntity::class,
    AcceptedTransportEntity::class,
    FligelProductEntity::class])
@TypeConverters(OfficeInventoryEntityTypeConvertor::class, DriverInventoryTypeConvertor::class)
abstract class DasAppDatabase: RoomDatabase() {

    abstract fun officeInventoryDao(): OfficeInventoryDao

    abstract fun officeInventoryAcceptedDao(): OfficeInventoryAcceptedDao

    abstract fun officeInventorySentDao(): OfficeInventorySentDao

    abstract fun driverInventoryDao(): DriverInventoryDao

    abstract fun driverSentInventoryDao(): DriverInventorySentDao

    abstract fun driverAcceptedInventoryDao(): DriverInventoryAcceptedDao

    abstract fun driverFligelDataDao(): FligelDataDao

    companion object {
        private val LOCK = Any()
        @Volatile
        private var sInstance: DasAppDatabase? = null
        private const val DATABASE_NAME = "das_app_db"

        fun getInstance(context: Context): DasAppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context, DasAppDatabase::class.java, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return sInstance
        }

        fun resetDb() { sInstance = null }

    }

}