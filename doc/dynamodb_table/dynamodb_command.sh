#!/bin/sh

#aws dynamodb list-tables --endpoint-url http://127.0.0.1:8000
#aws dynamodb list-tables --endpoint-url https://dynamodb.cn-northwest-1.amazonaws.com.cn
#
#aws dynamodb get-item --table-name zx_p --key '{"gid":{"S":"spuid1"}, "sid":{"S": "0"} }' --endpoint-url http://127.0.0.1:8000
#aws dynamodb get-item --table-name zx_p --key '{"gid":{"S":"9a34d393f279f4ba"}, "sid":{"S": "0"} }'



#aws dynamodb put-item --table-name zx_p --item file://zx_p_data.json --return-consumed-capacity TOTAL   --endpoint-url http://127.0.0.1:8000
aws dynamodb batch-write-item --request-items file://zx_p_data.json  --endpoint-url http://127.0.0.1:8000
