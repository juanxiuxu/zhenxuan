#!/bin/sh
source config.sh
#curl -v -H"Content-Type:application/json" -XPOST -d '{"code":"001FfLcL0gKFD52zKIfL00jHcL0FfLcM", "appType":"zx"}' "http://127.0.0.1:9091/passport/login/js-wa"
curl -v -H"Content-Type:application/json" -XPOST -d '{"code":"'$CODE'", "app_type":"zx"}' "http://127.0.0.1:9091/passport/login/js-wa"
