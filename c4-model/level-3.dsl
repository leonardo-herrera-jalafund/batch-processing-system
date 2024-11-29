workspace {

    model {
        system = softwareSystem "High-Throughput Batch Processing System" {
            batchProcessor = container "Batch Processor" "Processes each batch of data asynchronously with parallelism." "Async Service"

            concurrencyController = container "Concurrency Controller" "Manages the concurrent processing of multiple data batches." "Concurrency Management"
    
            batchProcessorCore = container "Processor Core" "Core component responsible for processing individual data items within each batch." "Processing Logic"
    
            batchProcessor -> concurrencyController "Controls parallel processing"
            concurrencyController -> batchProcessorCore "Distributes tasks across cores"
        }
    }

    views {
        container system {
            include *
            autolayout lr
        }

        theme default
    }
}