#/bin/sh

source config.sh

curl -v -H"Content-Type:application/json" -H"x-access-token:$TOKEN" -XPOST -d'{"auth_uid":"jIWQp/Z/AABbwKmPAAAAAQE="}' "http://127.0.0.1:9091/passport/user/info"
#curl -v -H"Content-Type:application/json" -H"x-access-token:$TOKEN" -XPOST -d'{}' "http://127.0.0.1:9091/passport/user/info"
