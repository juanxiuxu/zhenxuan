#!/bin/sh
source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:$TOKEN" -XPOST -d'{"sku":"9a34d393f279f4ba-1","address": "info2 name1", "contact":"62674498", "phone":"15210833658", "province":"shanxi", "city":"xian", "county":"china", "num":1, "coupon_code":"12"}' "http://127.0.0.1:9091/order/create-sku-direct"

