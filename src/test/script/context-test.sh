#!/bin/sh
for i in ~/gl16/src/test/deca/context/valid/valid_sans_objet/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done

for i in ~/gl16/src/test/deca/context/valid/valid_hello_world/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done
