#!/bin/sh
source config.sh
#curl -v -H"Content-Type:application/json" -XGET "http://127.0.0.1:9091/debug/hello"
#curl -v -H"Content-Type:application/json" -XGET "http://java-t.cn-northwest-1.eb.amazonaws.com.cn/debug/hello"

curl -v -H"Content-Type:application/json" -XGET $HOST"/debug/hello"
