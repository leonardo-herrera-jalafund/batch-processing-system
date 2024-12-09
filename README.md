# Batch Processing System

## Authors
Santiago Caballero Manzaneda  
Leonardo Alberto Herrera Rosales

### Description


### How to Run
1. Start the server
2. To start the processing execute this command:
   > `curl -X POST -H "Content-Type: text/plain" -d "/directory/path" http://localhost:8080/api/process-csv`


### **GET `/api/status`**
Retrieves the status of the ongoing or completed batch processing.

#### **Response**
Returns a JSON object with the following structure:
```json
{
  "processId": "Unique identifier for the process",
  "status": true,
  "totalBatches": 10,
  "completedBatches": 5,
  "totalSales": 1250.50,
  "totalItemsSold": 250,
  "totalTax": 62.50,
  "averageRating": 4.8,
  "errorMessages": [
    "File not found",
    "Invalid format in file: data.csv"
  ]
}