#!/bin/sh
for i in ~/gl16/src/test/deca/context/invalid/invalid_sans_objet/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done
