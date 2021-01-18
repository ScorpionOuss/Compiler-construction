#!/bin/sh

for i in ./src/test/deca/codegen/valid/*
do
    for j in $i/*.ass
    do
        rm "$j"
    done
done

for i in ./src/test/deca/codegen/valid/*
do
    for j in $i/*.res
    do
        rm "$j"
    done
done


