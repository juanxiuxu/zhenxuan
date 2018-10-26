#! /bin/bash

# author yupeng.qin
# 本地会打出开发包以及QA包, 线上包依赖于独立配置
# 上线改两处（有需要的话）

JAVA_HOME=/home/scmtools/buildkit/java/jdk-1.8-8u20
PATH=${JAVA_HOME}/bin:/usr/maven/apache-maven-3.0.4/bin:$PATH
export JAVA_HOME
export PATH

function parse_fe_release()
{
    curl -s 'http://agile.neptune.com/api/agile/getReleaseInfoOutputUrl?module=baidu/asset/upms-fe' | \
    sed -e 's/[{}]/''/g' | \
    sed -e 's/", "/'\",\"'/g' | \
    sed -e 's/" ,"/'\",\"'/g' | \
    sed -e 's/" , "/'\",\"'/g' | \
    sed -e 's/","/'\"---SEPERATOR---\"'/g' | \
    awk -F=':' -v RS='---SEPERATOR---' "\$1~/\"$1\"/ {print}" | \
    sed -e "s/\"$1\"://" | \
    tr -d "\n\t" | \
    sed -e 's/\\"/"/g' | \
    sed -e 's/\\\\/\\/g' | \
    sed -e 's/^[ \t]*//g' | \
    sed -e 's/^"//'  -e 's/"$//'
}

echo "Get with scm fe page..."
rm -rf temp
mkdir -p temp/fe
mkdir -p temp/war
cd temp/fe
data=$(parse_fe_release prodPathCmd)
echo "============"
echo  "start:="${data}
$data
echo "========================"
cd output
tar  -zxvf static-adapter-fe.tar.gz
cd ../../../

export LANG='en_US.UTF-8'
echo "dev compile begin"
DEFAULT_ENVIRONMENT="dev"
if ! [ "$1" = "" ]
then
    DEFAULT_ENVIRONMENT="$1"
fi

rm -rf output
mkdir output

mvn -U clean package -Dmaven.test.skip=true -P dev
mv target/output/adapter.war temp
unzip ./temp/adapter.war -d ./temp/war/
mv  ./temp/fe/output/adapter-fe/* ./temp/war/
rm ./temp/adapter.war
cd temp/war/
zip -r ../adapter.war *
cd ../../
mv temp/adapter.war output/adapter_dev.war


rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/qa/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_qa.war *
cd ../../
mv temp/adapter_qa.war output/adapter_qa.war



rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/online/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_online.war *
cd ../../
mv temp/adapter_online.war output/adapter_online.war



rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/liantiaoA/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_liantiaoA.war *
cd ../../
mv temp/adapter_liantiaoA.war output/adapter_liantiaoA.war


rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/liantiaoB/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_liantiaoB.war *
cd ../../
mv temp/adapter_liantiaoB.war output/adapter_liantiaoB.war

rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/liantiaoC/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_liantiaoC.war *
cd ../../
mv temp/adapter_liantiaoC.war output/adapter_liantiaoC.war

rm -rf ./temp/war/WEB-INF/classes/config-db.properties
cp ./temp/war/WEB-INF/classes/_profile/yufei/config-db.properties ./temp/war/WEB-INF/classes
cd temp/war/
zip -r ../adapter_yufei.war *
cd ../../
mv temp/adapter_yufei.war output/adapter_yufei.war