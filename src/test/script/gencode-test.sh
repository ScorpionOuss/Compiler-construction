#!/bin/sh

for i in ./src/test/deca/codegen/valid/*
do
    for j in $i/*.deca
    do
        str=$j
        prefix=${str%.deca}
        assembly=$prefix.ass
        result=$prefix.res
        echo $str
        #cat "$j"
        echo "\n"
        decac "$j"
        ima $assembly > $result
    echo "------------------------------------------------------------------------------------"
    done
done
