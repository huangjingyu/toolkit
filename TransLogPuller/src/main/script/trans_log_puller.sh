#!/bin/sh

DIR=$(cd $(dirname "$0"); pwd)
java -jar $DIR/TransLogPuller.jar $@