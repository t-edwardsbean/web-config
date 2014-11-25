#!/bin/bash

APP_HOME=$(dirname "$0")
APP_HOME=$(cd "$APP_HOME"; pwd)
DEST_HOME=$(cd $2; pwd)
{
flock -w 10 200
if [ $# != 2 ]; then
    echo "参数不对"
    exit 1
fi
SOURCE=$1
cp -r $SOURCE $APP_HOME/thrift-package/src/main/java/
cd $APP_HOME/thrift-package
mvn package -q -Dmaven.test.skip=true
mv $APP_HOME/thrift-package/target/thrift-package-1.0-SNAPSHOT.jar $DEST_HOME
rm -rf $APP_HOME/thrift-package/src/main/java/*
rm -rf $APP_HOME/thrift-package/target
} 200<>$APP_HOME/.lock