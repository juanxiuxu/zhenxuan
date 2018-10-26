#/bin/sh

source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:$TOKEN" -XPOST -d'{"login_uid":"jIWQp/Z/AABbwKmPAAAAAQE="}' "http://127.0.0.1:9091/passport/user/is_fwh_user"
