#!/bin/sh

source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:"$TOKEN -XPOST -d '{"oid":"myoid3", "use_balance":false}' "http://127.0.0.1:9091/pay/wx/order"

