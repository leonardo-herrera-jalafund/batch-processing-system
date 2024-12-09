workspace {

  model {

    sourceSystem = softwareSystem "Source System" {
      description "System providing large datasets for processing."
    }

    targetSystem = softwareSystem "Target System" {
      description "System where processed data is saved."
    }

    batchProcessingSystem = softwareSystem "High-Throughput system.domain.Batch Processing System"  {
      batchLoader = container "system.domain.Batch Loader" "Asynchronously loads data in batches from the source system." "Async Service"

      batchProcessor = container "system.domain.Batch Processor" "Processes each batch of data asynchronously with parallelism." "Async Service"

      batchWriter = container "system.domain.Batch Writer"  "Writes processed data back to the target system asynchronously." "Async Service"

      flowController = container "Flow Controller" "Manages backpressure and flow control, ensuring efficient processing." "Control Module"

      errorHandler = container "Error Handler" "Handles asynchronous error management and retries failed batches." "Error Handling Module"
    }

    batchLoader -> sourceSystem "Loads data batches asynchronously"
    batchLoader -> batchProcessor "Sends batches for processing"
    batchProcessor -> batchWriter "Sends processed data batches"
    batchWriter -> targetSystem "Writes data asynchronously"
    batchProcessor -> flowController "Coordinates processing rate and backpressure"
    batchWriter -> flowController "Coordinates write rate and backpressure"
    errorHandler -> batchProcessor "Handles batch failures and retries"
  }

  views {
    container batchProcessingSystem {
      include *
      autolayout lr
    }

    theme default
  }
}