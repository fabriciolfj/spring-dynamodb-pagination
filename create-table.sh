#!/bin/bash

aws dynamodb create-table --endpoint-url http://localhost:4566  \
--table-name account \
--attribute-definitions \
  AttributeName=ID,AttributeType=S \
	AttributeName=customer,AttributeType=S \
	AttributeName=date_creation,AttributeType=S \
--key-schema \
	AttributeName=ID,KeyType=HASH \
--provisioned-throughput \
ReadCapacityUnits=5,WriteCapacityUnits=5 \
--global-secondary-indexes \
'[
    {
      "IndexName": "CustomerDateIdIndex",
      "KeySchema": [
        {
           "AttributeName": "customer",
           "KeyType": "HASH"
        },
        {
            "AttributeName": "date_creation",
            "KeyType": "RANGE"
        }
      ],
      "Projection": {
        "ProjectionType": "ALL"
      },
      "ProvisionedThroughput": {
        "ReadCapacityUnits": 10,
        "WriteCapacityUnits": 5
      }
    }
]'