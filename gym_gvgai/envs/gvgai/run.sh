#!/bin/bash
src_folder='./src'
build_folder='builtclient'
rm -rf ${build_folder}
mkdir -p ${build_folder}
find ${src_folder} -name "*.java" > sources.txt
javac -d ${build_folder} @sources.txt
#run built client
java -classpath ${build_folder} tracks.singlePlayer.Test

