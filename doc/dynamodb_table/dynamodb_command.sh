#!/bin/sh

aws dynamodb list-tables --endpoint-url http://127.0.0.1:8000
aws dynamodb list-tables --endpoint-url https://dynamodb.cn-northwest-1.amazonaws.com.cn
