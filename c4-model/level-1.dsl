workspace {

    model {
        
        user = person "User" {
            description "User or system initiating batch processing jobs"
        }

        batchProcessingSystem = softwareSystem "High-Throughput system.domain.Batch Processing System" {
            description "Asynchronous batch data processing system for high-speed ETL, data migration, and bulk processing."
        }
        
        sourceSystem = softwareSystem "Source System" {
            description "System providing large datasets for processing."
        }
        
        targetSystem = softwareSystem "Target System" {
            description "System where processed data is saved."
        }

        user -> batchProcessingSystem "Initiates batch processing jobs"
        batchProcessingSystem -> sourceSystem "Loads data batches asynchronously"
        batchProcessingSystem -> targetSystem "Writes processed data batches asynchronously"
    }

    views {
        systemContext batchProcessingSystem {
            include *
            autolayout lr
        }

        theme default
    }
}