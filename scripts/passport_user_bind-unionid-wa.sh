#!/bin/sh

source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:"$TOKEN -XPOST -d '{"login_uid":"jIWQp/Z/AABbxTr3AAAAAgE=", "iv":"'$BIND_UNIONID_IV'", "encryptedData":"'$BIND_UNIONID_ENCRYPTEDDATA'"}' "http://127.0.0.1:9091/passport/user/bind-unionid-wa"

