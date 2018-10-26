#!/bin/sh
aws dynamodb create-table --table-name zx_shop --attribute-definitions AttributeName=uid,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH   --provisioned-throughput ReadCapacityUnits=45,WriteCapacityUnits=30 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_p --attribute-definitions AttributeName=gid,AttributeType=S AttributeName=sid,AttributeType=S --key-schema AttributeName=gid,KeyType=HASH  AttributeName=sid,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_p_g --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH   --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=100 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_address --attribute-definitions AttributeName=uid,AttributeType=S AttributeName=address_id,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH  AttributeName=address_id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_cart --attribute-definitions AttributeName=uid,AttributeType=S AttributeName=object_id,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH  AttributeName=object_id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_collect --attribute-definitions AttributeName=uid,AttributeType=S AttributeName=collect_id,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH  AttributeName=collect_id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_common --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH   --provisioned-throughput ReadCapacityUnits=45,WriteCapacityUnits=30 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_eva --attribute-definitions AttributeName=gid,AttributeType=S AttributeName=comment_id,AttributeType=S --key-schema AttributeName=gid,KeyType=HASH  AttributeName=comment_id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_error --attribute-definitions AttributeName=service,AttributeType=S AttributeName=id,AttributeType=S --key-schema AttributeName=service,KeyType=HASH  AttributeName=id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_user_act --attribute-definitions AttributeName=uid,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_user_bg --attribute-definitions AttributeName=email,AttributeType=S --key-schema AttributeName=email,KeyType=HASH --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zx_user_qr --attribute-definitions AttributeName=uid,AttributeType=S AttributeName=key,AttributeType=S --key-schema AttributeName=uid,KeyType=HASH  AttributeName=key,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000

aws dynamodb create-table --table-name zc_paycb_log --attribute-definitions AttributeName=gid,AttributeType=S AttributeName=comment_id,AttributeType=S --key-schema AttributeName=gid,KeyType=HASH  AttributeName=comment_id,KeyType=RANGE --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000


aws dynamodb create-table --table-name zz_bg_vendor --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=100,WriteCapacityUnits=144 --endpoint-url http://localhost:8000
