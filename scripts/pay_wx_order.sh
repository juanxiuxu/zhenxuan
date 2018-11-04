#!/bin/sh

source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:"$TOKEN -XPOST -d '{"oid":"myoid4", "use_balance":false}' $HOST"/pay/wx/order"

