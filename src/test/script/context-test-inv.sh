#!/bin/sh
for i in src/test/deca/context/invalid/*.deca
    do
        echo "$i"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done
